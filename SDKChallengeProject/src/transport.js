import AgoraRTM from "agora-rtm-sdk";
import { EMBED_UID, APP_UID } from "./constant";

const EVENTS = {
  SOURCE_READY: 0,
  MIRROR_READY: 1,
  SEND_RECORD: 2,
  ACK_RECORD: 3,
  STOP: 4,
  REMOTE_CONTROL: 5,
  0: "SOURCE_READY",
  1: "MIRROR_READY",
  2: "SEND_RECORD",
  3: "ACK_RECORD",
  4: "STOP",
  5: "REMOTE_CONTROL",
};

// for testing
const STORAGE_KEY = "_transporter_message_";
export class LocalTransporter {
  constructor() {
    this.handlers = {
      SOURCE_READY: [],
      MIRROR_READY: [],
      SEND_RECORD: [],
      ACK_RECORD: [],
      STOP: [],
      REMOTE_CONTROL: [],
    };
    localStorage.removeItem(STORAGE_KEY);
    window.addEventListener("storage", (e) => {
      if (e.key === STORAGE_KEY && e.newValue) {
        const message = JSON.parse(e.newValue);
        this.handlers[EVENTS[message.event]].map((h) =>
          h({
            e: message.event,
            payload: message.payload,
          })
        );
      }
    });
  }

  setItem(message) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(message));
    // jest could not listen to storage event in JSDOM, not a big deal at here.
    if (process.env.NODE_ENV === "test") {
      this.handlers[EVENTS[message.event]].map((h) =>
        h({
          e: message.event,
          payload: message.payload,
        })
      );
    }
  }

  sendSourceReady() {
    this.setItem({
      event: EVENTS.SOURCE_READY,
    });
  }

  sendMirrorReady() {
    this.setItem({
      event: EVENTS.MIRROR_READY,
    });
  }

  sendRecord(record) {
    this.setItem({
      event: EVENTS.SEND_RECORD,
      payload: record,
    });
  }

  ackRecord(id) {
    this.setItem({
      event: EVENTS.ACK_RECORD,
      payload: id,
    });
  }

  sendStop() {
    this.setItem({
      event: EVENTS.STOP,
    });
  }

  sendRemoteControl(payload) {
    this.setItem({
      event: EVENTS.REMOTE_CONTROL,
      payload,
    });
  }

  on(event, handler) {
    switch (event) {
      case "sourceReady":
        this.handlers.SOURCE_READY.push(handler);
        break;
      case "mirrorReady":
        this.handlers.MIRROR_READY.push(handler);
        break;
      case "record":
        this.handlers.SEND_RECORD.push(handler);
        break;
      case "ack":
        this.handlers.ACK_RECORD.push(handler);
        break;
      case "stop":
        this.handlers.STOP.push(handler);
        break;
      case "remoteControl":
        this.handlers.REMOTE_CONTROL.push(handler);
        break;
      default:
        break;
    }
  }
}

const AGORA_APP_ID = "016149b89b524ce8b88cd11320bf4dd9";
export class RtcTransporter {
  constructor(uid) {
    this.handlers = {
      SOURCE_READY: [],
      MIRROR_READY: [],
      SEND_RECORD: [],
      ACK_RECORD: [],
      STOP: [],
      REMOTE_CONTROL: [],
    };
    this.client = AgoraRTM.createInstance(AGORA_APP_ID, {
      logFilter: AgoraRTM.LOG_FILTER_WARNING,
    });
    this.uid = uid;
    this.client.on("MessageFromPeer", (message, peerId) => {
      if (![EMBED_UID, APP_UID].includes(peerId)) {
        return;
      }
      const data = JSON.parse(message.text);
      this.handlers[EVENTS[data.event]].map((h) =>
        h({
          e: data.event,
          payload: data.payload,
        })
      );
    });
  }

  async login() {
    // return;
    let retry = 5;
    let loginResult;
    let loginError;
    while (retry > 0 || loginResult) {
      retry--;
      try {
        loginResult = await this.client.login({ uid: this.uid });
      } catch (error) {
        loginError = error;
      }
    }
    return loginResult || loginError;
  }

  sendSourceReady() {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.SOURCE_READY }),
      },
      APP_UID
    );
  }

  sendMirrorReady() {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.MIRROR_READY }),
      },
      EMBED_UID
    );
  }

  sendRecord(record) {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.SEND_RECORD, payload: record }),
      },
      APP_UID
    );
  }

  ackRecord(id) {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.ACK_RECORD, payload: id }),
      },
      EMBED_UID
    );
  }

  sendStop() {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.STOP }),
      },
      APP_UID
    );
  }

  sendRemoteControl(payload) {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.REMOTE_CONTROL, payload }),
      },
      EMBED_UID
    );
  }

  on(event, handler) {
    switch (event) {
      case "sourceReady":
        this.handlers.SOURCE_READY.push(handler);
        break;
      case "mirrorReady":
        this.handlers.MIRROR_READY.push(handler);
        break;
      case "record":
        this.handlers.SEND_RECORD.push(handler);
        break;
      case "ack":
        this.handlers.ACK_RECORD.push(handler);
        break;
      case "stop":
        this.handlers.STOP.push(handler);
        break;
      case "remoteControl":
        this.handlers.REMOTE_CONTROL.push(handler);
        break;
      default:
        break;
    }
  }
}

export const transporter = new LocalTransporter();
