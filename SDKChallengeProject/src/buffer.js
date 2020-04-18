export class SourceBuffer {
  constructor() {
    this._buffer = {};
    this.cursor = 0;
  }

  add(chunk) {
    this.cursor++;
    this._buffer[this.cursor] = chunk;
  }
}
