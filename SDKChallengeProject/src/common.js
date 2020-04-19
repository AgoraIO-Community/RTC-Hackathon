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
