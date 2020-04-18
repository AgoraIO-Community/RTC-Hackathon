import { LocalTransporter } from "../src/transport";

describe("LocalTransporter", () => {
  it("integration test", () => {
    const transporter = new LocalTransporter();
    const results = [];
    transporter.on("sourceReady", (data) => {
      results.push(data);
    });
    transporter.on("mirrorReady", (data) => {
      results.push(data);
    });
    transporter.on("record", (data) => {
      results.push(data);
    });
    transporter.on("ack", (data) => {
      results.push(data);
    });
    transporter.sendSourceReady();
    transporter.sendMirrorReady();
    transporter.sendRecord({ id: 1 });
    transporter.ackRecord(1);
    expect(results).toEqual([
      { e: 0 },
      { e: 1 },
      { e: 2, payload: { id: 1 } },
      { e: 3, payload: 1 },
    ]);
  });
});
