<script>
  import { record } from "rrweb";
  import { createMachine, interpret } from "@xstate/fsm";
  import { onMount, onDestroy } from "svelte";
  import { quintOut } from "svelte/easing";
  import { scale } from "svelte/transition";
  import { RtcTransporter } from "./transport";
  import { SourceBuffer } from "./buffer";
  import { EMBED_UID } from "./constant";
  import Panel from "./components/Panel.svelte";
  import Tag from "./components/Tag.svelte";

  const transporter = new RtcTransporter(EMBED_UID);
  let login = transporter.login();

  let ref;
  $: ref && document.body.appendChild(ref);

  let open = false;
  $: icon = open ? "./icons/close.svg" : "./icons/team.svg";

  let stopRecordFn;
  const buffer = new SourceBuffer({
    onTimeout(record) {
      transporter.sendRecord(record);
    },
  });

  const embedMachine = createMachine(
    {
      initial: "idle",
      states: {
        idle: {
          on: {
            START: {
              target: "ready",
              actions: ["start"],
            },
          },
        },
        ready: {
          on: {
            CONNECT: {
              target: "connected",
              actions: ["connect"],
            },
          },
        },
        connected: {
          on: {
            STOP: {
              target: "idle",
              actions: ["stop"],
            },
          },
        },
      },
    },
    {
      actions: {
        start() {
          transporter.sendSourceReady();
        },
        connect() {
          stopRecordFn = record({
            emit(event) {
              const id = buffer.add(event);
              transporter.sendRecord(buffer.buffer[id]);
            },
          });
        },
        stop() {
          stopRecordFn();
        },
      },
    }
  );

  let selecting = false;
  let mask;
  $: mask && document.body.appendChild(mask);
  let blockElSet = new Set();
  $: blockEls = Array.from(blockElSet);

  const highlight = (target) => {
    const { x, y, width, height } = target.getBoundingClientRect();
    Object.assign(mask.style, {
      left: `${x}px`,
      top: `${y}px`,
      width: `${width}px`,
      height: `${height}px`,
    });
  };
  const removeHighlight = () => {
    Object.assign(mask.style, {
      left: 0,
      top: 0,
      width: 0,
      height: 0,
    });
  };

  const over = (event) => {
    if (
      event.target &&
      ref !== event.target &&
      !ref.contains(event.target) &&
      event.target !== document.body
    ) {
      highlight(event.target);
    }
  };
  const click = (event) => {
    if (!selecting || ref.contains(event.target)) {
      return;
    }
    event.target.classList.add("rr-block");
    blockElSet = blockElSet.add(event.target);
    cancelSelect();
  };
  const cancelSelect = () => {
    selecting = false;
    removeHighlight();
    window.removeEventListener("mousemove", over, { capture: true });
    window.removeEventListener("click", click, { capture: true });
  };
  const removeBlockEl = (el) => {
    blockElSet.delete(el);
    blockElSet = blockElSet;
    removeHighlight();
  };
  function handleSelectBlock() {
    if (selecting) {
      cancelSelect();
    } else {
      selecting = true;
      window.addEventListener("mousemove", over, { capture: true });
      window.addEventListener("click", click, { capture: true });
    }
  }

  let current = embedMachine.initialState;
  const service = interpret(embedMachine);
  onMount(() => {
    service.start();
    service.subscribe((state) => {
      current = state;
    });
    transporter.on("mirrorReady", () => {
      service.send("CONNECT");
    });
    transporter.on("ack", ({ payload }) => {
      buffer.delete(payload);
    });
  });
  onDestroy(() => {
    service.stop();
  });
</script>

<div class="syncit-embed" bind:this="{ref}">
  {#if open}
  <div
    transition:scale="{{duration: 500, opacity: 0.5, easing: quintOut}}"
    style="transform-origin: right bottom;"
  >
    <Panel>
      {#await login}
      <div class="syncit-center syncit-load-text">初始化中...</div>
      {:then}
      <!-- -->
      {#if current.matches('idle')}
      <div class="syncit-center">
        <div class="syncit-panel-control">
          <button class="syncit-btn ordinary" on:click="{handleSelectBlock}">
            {selecting ? '取消' : '选择屏蔽区域'}
          </button>
          <div class="syncit-block-els">
            {#each blockEls as el, idx}
            <Tag
              on:mouseover="{() => highlight(el)}"
              on:mouseout="{removeHighlight}"
              on:click="{() => removeBlockEl(el)}"
              >区域-{idx + 1}
            </Tag>
            {/each}
          </div>
        </div>
        <button class="syncit-btn" on:click="{() => service.send('START')}">
          启用 syncit 分享
        </button>
      </div>
      {:else if current.matches('ready')}
      <div class="syncit-center syncit-load-text">已启用，等待连接中</div>
      {:else if current.matches('connected')}
      <button class="syncit-btn" on:click="{() => service.send('STOP')}">
        停止分享
      </button>
      {/if}
      <!---->
      {:catch error}
      <div class="syncit-center syncit-error">{error.message}</div>
      {/await}
    </Panel>
  </div>
  {/if}
  <!---->
  <button class="syncit-toggle syncit-btn" on:click="{() => open = !open}">
    <img alt="icon" src="{icon}" />
  </button>
  <!---->
  <div bind:this="{mask}" class="syncit-mask"></div>
</div>

<style>
  :global(button) {
    outline: none;
  }

  .syncit-embed {
    position: absolute;
    right: 1em;
    bottom: 1em;
    display: flex;
    flex-direction: column;
  }

  .syncit-btn:hover {
    background: #3399ff;
  }

  .syncit-btn,
  .syncit-btn:active {
    cursor: pointer;
    background: #0078f0;
    border: 1px solid rgba(62, 70, 82, 0.18);
    box-shadow: 0px 1px 2px rgba(184, 192, 204, 0.6);
    color: #fff;
    padding: 8px 16px;
    border-radius: 4px;
    font-size: 14px;
    line-height: 22px;
    margin-bottom: 0.5em;
  }

  .syncit-btn.ordinary {
    background: #fff;
    color: #3e4652;
    border: 1px solid rgba(129, 138, 153, 0.6);
  }
  .syncit-btn.ordinary:hover {
    background: #f5f7fa;
  }
  .syncit-btn.ordinary:active {
    background: #dfe4eb;
  }

  .syncit-toggle,
  .syncit-toggle:active {
    width: 40px;
    height: 40px;
    line-height: 40px;
    border-radius: 20px;
    padding: 0;
    align-self: flex-end;
  }

  .syncit-center {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }

  .syncit-load-text {
    font-size: 14px;
    line-height: 22px;
    color: #3e4652;
  }

  .syncit-error {
    color: #e75a3a;
  }

  .syncit-mask {
    position: absolute;
    left: 0;
    top: 0;
    pointer-events: none;
    z-index: 999999;
    background: rgba(136, 194, 232, 0.75);
    border: 1px solid rgba(5, 150, 45, 0.5);
  }

  .syncit-panel-control {
    flex: 1;
    width: 100%;
    align-items: flex-start;
    border-bottom: 1px solid rgba(235, 239, 245, 0.6);
    margin-bottom: 8px;
  }

  .syncit-block-els {
    display: flex;
    overflow: auto;
    word-break: keep-all;
  }

  .syncit-block-els > :global(span) {
    cursor: pointer;
    margin-right: 4px;
  }
</style>
