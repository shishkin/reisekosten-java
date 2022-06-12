<template>
  <div>
    <h1>Travel Expense Entry</h1>

    <EntryForm @entry-submitted="forceRerender" />

    <h2>Expense Report</h2>
    <ExpenseReport :key="expenseReportKey" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import HelloWorld from "./components/HelloWorld.vue";
import EntryForm from "./components/EntryForm.vue";
import ExpenseReport from "./components/ExpenseReport.vue";

@Options({
  components: {
    HelloWorld,
    EntryForm,
    ExpenseReport,
  },
})
export default class App extends Vue {
  expenseReportKey = 0;
  forceRerender() {
    this.expenseReportKey += 1;
  }
}
</script>

<style>
#app {
  font-family: Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  margin: auto 3em;
  color: #2c3e50;
}
</style>
