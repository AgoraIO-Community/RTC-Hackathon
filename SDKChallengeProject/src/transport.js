import AgoraRTM from "agora-rtm-sdk";

const EVENTS = {
  SOURCE_READY: 0,
  MIRROR_READY: 1,
  SEND_RECORD: 2,
  ACK_RECORD: 3,
  0: "SOURCE_READY",
  1: "MIRROR_READY",
  2: "SEND_RECORD",
  3: "ACK_RECORD",
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
    };
    this.client = AgoraRTM.createInstance(AGORA_APP_ID, {
      logFilter: AgoraRTM.LOG_FILTER_WARNING,
    });
    this.uid = uid;
    this.client.on("MessageFromPeer", (message, peerId) => {
      if (!["syncit-embed", "syncit-app"].includes(peerId)) {
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

  login() {
    return this.client.login({ uid: this.uid });
  }

  sendSourceReady() {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.SOURCE_READY }),
      },
      "syncit-app"
    );
  }

  sendMirrorReady() {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.MIRROR_READY }),
      },
      "syncit-embed"
    );
  }

  sendRecord(record) {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.SEND_RECORD, payload: record }),
      },
      "syncit-app"
    );
  }

  ackRecord(id) {
    return this.client.sendMessageToPeer(
      {
        text: JSON.stringify({ event: EVENTS.ACK_RECORD, payload: id }),
      },
      "syncit-embed"
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
      default:
        break;
    }
  }
}

export const transporter = new LocalTransporter();
