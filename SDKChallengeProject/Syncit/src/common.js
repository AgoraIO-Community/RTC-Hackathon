import { EventType, IncrementalSource } from "rrweb";
import { on } from "rrweb/es/rrweb/src/utils";
import { REMOTE_CONTROL_ACTIONS } from "./constant";

export function formatBytes(bytes, decimals = 0) {
  if (!bytes) {
    return {
      value: 0,
      unit: "B/s",
    };
  }
  const k = 1024;
  const units = ["B/s", "KiB/s", "MiB/s"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return {
    value: parseFloat((bytes / Math.pow(k, i)).toFixed(decimals)),
    unit: units[i],
  };
}

const INPUT_TAGS = ["INPUT", "TEXTAREA", "SELECT"];
const lastInputValueMap = new WeakMap();
export function onMirror(iframe, cb) {
  const iframeWindow = iframe.contentWindow;
  const iframeDoc = iframeWindow.document;
  const handlers = [];

  const clickCb = (evt) => {
    if (!evt.target.__sn) {
      return;
    }
    cb({
      action: REMOTE_CONTROL_ACTIONS.CLICK,
      id: evt.target.__sn.id,
    });
  };
  handlers.push(on("click", clickCb, iframeWindow));
  const scrollCb = (evt) => {
    if (!evt.target.__sn) {
      return;
    }
    const { id } = evt.target.__sn;
    if (evt.target === iframeDoc) {
      const scrollEl = iframeDoc.scrollingElement || iframeDoc.documentElement;
      cb({
        action: REMOTE_CONTROL_ACTIONS.SCROLL,
        id,
        x: scrollEl.scrollLeft,
        y: scrollEl.scrollTop,
      });
    } else {
      cb({
        action: REMOTE_CONTROL_ACTIONS.SCROLL,
        id,
        x: evt.target.scrollLeft,
        y: evt.target.scrollTop,
      });
    }
  };
  handlers.push(on("scroll", scrollCb, iframeWindow));

  // copied and modified from rrweb
  function hookSetter(target, key, d, isRevoked) {
    const original = iframeWindow.Object.getOwnPropertyDescriptor(target, key);
    iframeWindow.Object.defineProperty(
      target,
      key,
      isRevoked
        ? d
        : {
            set(value) {
              // put hooked setter into event loop to avoid of set latency
              setTimeout(() => {
                d.set.call(this, value);
              }, 0);
              if (original && original.set) {
                original.set.call(this, value);
              }
            },
          }
    );
    return () => hookSetter(target, key, original || {}, true);
  }

  function eventHandler(event) {
    const { target } = event;
    if (!target || !target.tagName || INPUT_TAGS.indexOf(target.tagName) < 0) {
      return;
    }
    const type = target.type;
    if (type === "password") {
      return;
    }
    let text = target.value;
    let isChecked = false;
    if (type === "radio" || type === "checkbox") {
      isChecked = target.checked;
    }
    cbWithDedup(target, { text, isChecked });
    const name = target.name;
    if (type === "radio" && name && isChecked) {
      document
        .querySelectorAll(`input[type="radio"][name="${name}"]`)
        .forEach((el) => {
          if (el !== target) {
            cbWithDedup(el, {
              text: el.value,
              isChecked: !isChecked,
            });
          }
        });
    }
  }
  function cbWithDedup(target, v) {
    const lastInputValue = lastInputValueMap.get(target);
    if (
      !lastInputValue ||
      lastInputValue.text !== v.text ||
      lastInputValue.isChecked !== v.isChecked
    ) {
      lastInputValueMap.set(target, v);
      const id = target.__sn.id;
      cb({
        action: REMOTE_CONTROL_ACTIONS.INPUT,
        id,
        ...v,
      });
    }
  }
  ["input", "change"].forEach((eventName) =>
    handlers.push(on(eventName, eventHandler, iframeDoc))
  );
  const propertyDescriptor = Object.getOwnPropertyDescriptor(
    HTMLInputElement.prototype,
    "value"
  );
  const hookProperties = [
    [HTMLInputElement.prototype, "value"],
    [HTMLInputElement.prototype, "checked"],
    [HTMLSelectElement.prototype, "value"],
    [HTMLTextAreaElement.prototype, "value"],
  ];
  if (propertyDescriptor && propertyDescriptor.set) {
    handlers.push(
      ...hookProperties.map((p) =>
        hookSetter(p[0], p[1], {
          set() {
            // mock to a normal event
            eventHandler({ target: this });
          },
        })
      )
    );
  }
  return () => {
    handlers.forEach((h) => h());
  };
}

export function isIgnoredOnRmoteControl(chunk) {
  switch (true) {
    case chunk.type === EventType.IncrementalSnapshot &&
      chunk.data.source === IncrementalSource.MouseInteraction:
    case chunk.type === EventType.IncrementalSnapshot &&
      chunk.data.source === IncrementalSource.Scroll:
    case chunk.type === EventType.IncrementalSnapshot &&
      chunk.data.source === IncrementalSource.Input:
      return true;
    default:
      return false;
  }
}

export function applyMirrorAction(mirror, payload) {
  const { action, id, x, y, isChecked, text } = payload;
  switch (action) {
    case REMOTE_CONTROL_ACTIONS.CLICK: {
      const node = mirror.getNode(id);
      if (!node) {
        return;
      }
      node.click();
      break;
    }
    case REMOTE_CONTROL_ACTIONS.SCROLL: {
      const node = mirror.getNode(id);
      if (!node) {
        return;
      }
      if (node === document) {
        window.scrollTo({
          top: y,
          left: x,
          behavior: "smooth",
        });
      } else {
        node.scrollTop = y;
        node.scrollLeft = x;
      }
      break;
    }
    case REMOTE_CONTROL_ACTIONS.INPUT: {
      const node = mirror.getNode(id);
      if (!node) {
        return;
      }
      node.checked = isChecked;
      node.value = text;
      break;
    }
    default:
      break;
  }
}
