<script>
  import { record } from "rrweb";
  import { createMachine, interpret } from "@xstate/fsm";
  import { onMount, onDestroy } from "svelte";
  import { RtcTransporter } from "./transport";
  import { SourceBuffer } from "./buffer";

  const transporter = new RtcTransporter("syncit-embed");
  let login = transporter.login();

  let ref;
  $: ref && document.body.appendChild(ref);

  let open = false;
  let stopRecordFn;
  const buffer = new SourceBuffer({
    onTimeout(record) {
      transporter.sendRecord(record);
    },
  });

  const embedMachine = createMachine(
    {
      initial: "idle",
      states: {
        idle: {
          on: {
            START: {
              target: "ready",
              actions: ["start"],
            },
          },
        },
        ready: {
          on: {
            CONNECT: {
              target: "connected",
              actions: ["connect"],
            },
          },
        },
        connected: {
          on: {
            STOP: {
              target: "idle",
              actions: ["stop"],
            },
          },
        },
      },
    },
    {
      actions: {
        start() {
          transporter.sendSourceReady();
        },
        connect() {
          stopRecordFn = record({
            emit(event) {
              const id = buffer.add(event);
              transporter.sendRecord(buffer.buffer[id]);
            },
          });
        },
        stop() {
          stopRecordFn();
        },
      },
    }
  );
  let current = embedMachine.initialState;
  const service = interpret(embedMachine);
  onMount(() => {
    service.start();
    service.subscribe((state) => {
      current = state;
    });
    transporter.on("mirrorReady", () => {
      service.send("CONNECT");
    });
    transporter.on("ack", ({ payload }) => {
      buffer.delete(payload);
    });
  });
  onDestroy(() => {
    service.stop();
  });
</script>

<div class="syncit-embed" bind:this="{ref}">
  {#await login}
  <p>...login...</p>
  {:catch error}
  <p style="color: red;">{error.message}</p>
  {/await}
  <!---->
  <button on:click="{() => open = !open}">{open ? 'close' : 'open'}</button>
  {#if open}
  <div class="syncit-embed-panel">
    {#if current.matches('idle')}
    <button on:click="{() => service.send('START')}">start</button>
    {:else if current.matches('ready')}
    <div>等待连接中</div>
    {:else if current.matches('connected')}
    <button on:click="{() => service.send('STOP')}">stop</button>
    {/if}
  </div>
  {/if}
</div>

<style>
  .syncit-embed {
    position: absolute;
    right: 1em;
    bottom: 1em;
  }
</style>
