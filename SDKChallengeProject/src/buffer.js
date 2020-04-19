export const BUFFER_MS = 1000;

export class BaseBuffer {
  constructor() {
    this.buffer = {};
    this.cursor = 0;
  }

  add(chunk) {
    this.cursor++;
    this.buffer[this.cursor] = {
      id: this.cursor,
      t: Date.now(),
      chunk,
    };
    return this.cursor;
  }

  delete(id) {
    delete this.buffer[id];
  }
}

export class SourceBuffer extends BaseBuffer {
  constructor({ onTimeout } = {}) {
    super();
    this.timeToRetry = BUFFER_MS / 4;
    this.onTimeout = onTimeout;

    if (this.onTimeout) {
      this.checkTimeToRetry();
    }
  }

  checkTimeToRetry() {
    requestAnimationFrame(() => {
      for (const key in this.buffer) {
        const record = this.buffer[key];
        const now = Date.now();
        if (now - record.t > this.timeToRetry) {
          this.onTimeout(record);
          // reset timestamp
          record.t = now;
        }
      }
      this.checkTimeToRetry();
    });
  }
}

export class MirrorBuffer extends BaseBuffer {
  constructor({ onRecord } = {}) {
    super();
    this.onRecord = onRecord;
  }

  add(record) {
    if (!record.id) {
      throw new Error("MirrorBuffer only accept record with id");
    }
    if (record.id <= this.cursor) {
      // TODO: explain this
      return;
    }
    this.buffer[record.id] = record;
    if (this.onRecord) {
      this.tryToEmit();
    }
    return record.id;
  }

  tryToEmit() {
    const records = Object.values(this.buffer).sort((a, b) => a.id - b.id);
    let idx = 0;
    while (idx < records.length && this.cursor === records[idx].id - 1) {
      const record = records[idx];
      this.onRecord(record);
      idx++;
      this.cursor++;
      this.delete(record.id);
    }
    // const keys = Object.keys(this.buffer);
    // if (keys.length !== 0) {
    //   console.log(
    //     "in area",
    //     keys.length,
    //     this.cursor,
    //     records.map((r) => r.id)
    //   );
    // }
  }
}
