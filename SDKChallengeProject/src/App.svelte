<script>
  import { onMount } from "svelte";
  import { Replayer } from "rrweb";
  import { RtcTransporter } from "./transport";
  import { BUFFER_MS, MirrorBuffer } from "./buffer";

  const transporter = new RtcTransporter("syncit-app");
  let login = transporter.login();

  let sourceReady = false;
  let playerDom;
  let replayer;
  let started = false;
  const buffer = new MirrorBuffer({
    onRecord({ chunk }) {
      replayer.addEvent(chunk);
    },
  });

  function connect() {
    transporter.sendMirrorReady();
    replayer = new Replayer([], {
      root: playerDom,
      loadTimeout: 100,
      liveMode: true,
      insertStyleRules: [".syncit-embed { display: none }"],
      showWarning: true,
      showDebug: true,
    });
  }

  onMount(() => {
    transporter.on("sourceReady", () => {
      sourceReady = true;
    });
    transporter.on("record", (data) => {
      const { id, chunk } = data.payload;
      if (!started) {
        replayer.startLive(chunk.timestamp - BUFFER_MS);
        started = true;
      }
      buffer.add({ id, chunk });
      transporter.ackRecord(id);
    });
  });
</script>

{#await login}
<p>...login...</p>
{:catch error}
<p style="color: red;">{error.message}</p>
{/await}
<!---->
{#if sourceReady}
<button on:click="{connect}">connect</button>
{/if}
<div bind:this="{playerDom}"></div>

<style>
  :global(.replayer-wrapper) {
    position: relative;
  }
  :global(.replayer-mouse) {
    position: absolute;
    width: 20px;
    height: 20px;
    transition: 0.05s linear;
    background-size: contain;
    background-position: center center;
    background-repeat: no-repeat;
    background-image: url("data:image/svg+xml;base64,PHN2ZyBoZWlnaHQ9JzMwMHB4JyB3aWR0aD0nMzAwcHgnICBmaWxsPSIjMDAwMDAwIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGRhdGEtbmFtZT0iTGF5ZXIgMSIgdmlld0JveD0iMCAwIDUwIDUwIiB4PSIwcHgiIHk9IjBweCI+PHRpdGxlPkRlc2lnbl90bnA8L3RpdGxlPjxwYXRoIGQ9Ik00OC43MSw0Mi45MUwzNC4wOCwyOC4yOSw0NC4zMywxOEExLDEsMCwwLDAsNDQsMTYuMzlMMi4zNSwxLjA2QTEsMSwwLDAsMCwxLjA2LDIuMzVMMTYuMzksNDRhMSwxLDAsMCwwLDEuNjUuMzZMMjguMjksMzQuMDgsNDIuOTEsNDguNzFhMSwxLDAsMCwwLDEuNDEsMGw0LjM4LTQuMzhBMSwxLDAsMCwwLDQ4LjcxLDQyLjkxWm0tNS4wOSwzLjY3TDI5LDMyYTEsMSwwLDAsMC0xLjQxLDBsLTkuODUsOS44NUwzLjY5LDMuNjlsMzguMTIsMTRMMzIsMjcuNThBMSwxLDAsMCwwLDMyLDI5TDQ2LjU5LDQzLjYyWiI+PC9wYXRoPjwvc3ZnPg==");
  }
  :global(.replayer-mouse::after) {
    content: "";
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 10px;
    background: rgb(73, 80, 246);
    transform: translate(-10px, -10px);
    opacity: 0.3;
  }
  :global(.replayer-mouse.active::after) {
    animation: click 0.2s ease-in-out 1;
  }

  @keyframes click {
    0% {
      opacity: 0.3;
      width: 20px;
      height: 20px;
      border-radius: 10px;
      transform: translate(-10px, -10px);
    }
    50% {
      opacity: 0.5;
      width: 10px;
      height: 10px;
      border-radius: 5px;
      transform: translate(-5px, -5px);
    }
  }
</style>
