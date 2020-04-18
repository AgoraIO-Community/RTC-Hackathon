import { SourceBuffer } from "../src/buffer";

describe("SourceBuffer", () => {
  it("can add to buffer", () => {
    const buffer = new SourceBuffer();
    buffer.add("test");
    expect(buffer.cursor).toEqual(1);
  });
});
