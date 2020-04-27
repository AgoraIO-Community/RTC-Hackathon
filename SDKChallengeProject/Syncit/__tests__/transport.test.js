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
    transporter.on("remoteControl", (data) => {
      results.push(data);
    });
    transporter.on("stop", (data) => {
      results.push(data);
    });
    transporter.sendSourceReady();
    transporter.sendMirrorReady();
    transporter.sendRecord({ id: 1 });
    transporter.ackRecord(1);
    transporter.sendRemoteControl({ id: 1, action: "scroll" });
    transporter.sendStop();
    expect(results).toEqual([
      { e: 0, payload: undefined },
      { e: 1, payload: undefined },
      { e: 2, payload: { id: 1 } },
      { e: 3, payload: 1 },
      { e: 5, payload: { action: "scroll", id: 1 } },
      { e: 4, payload: undefined },
    ]);
  });
});
