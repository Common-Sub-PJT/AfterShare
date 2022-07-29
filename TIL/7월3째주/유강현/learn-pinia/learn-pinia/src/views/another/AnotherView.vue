<template>
  <main>
    <h2>This is test page</h2>
    <div>
      <h3>conter.counter2 is under this line</h3>
      <h4>Counter {{ counter2.counter2 }}</h4>
      <h4>Doubled {{ counter2.doubleCount2 }}</h4>
      <button type="button" @click="counter2.increment">increment</button>
    </div>
  </main>
</template>

<script>
import { defineComponent } from "vue";
import { useCounter2Store } from "@/views/another/stores/counter2";

export default defineComponent({
  name: "App",
  setup() {
    const counter2 = useCounter2Store();

    return {
      counter2,
    };
  },
});
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

