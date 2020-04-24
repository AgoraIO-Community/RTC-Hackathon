const Utils = require("./util.js")

class Perf {
  constructor() {
    this.init();
  }

  init() {
    this.perf = [];
    this.ts = new Date().getTime();
  }

  profile(event) {
    const ts = new Date().getTime();
    this.perf.push(`${event}: ${ts - this.ts}ms`);
  }

  dump() {
    Utils.log(`${JSON.stringify(this.perf)}`);
  }
}


module.exports = new Perf();