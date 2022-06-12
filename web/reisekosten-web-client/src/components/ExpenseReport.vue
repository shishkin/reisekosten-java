<template>
  <div>
    <div v-if="report.travels && report.travels.length > 0">
      <table>
        <thead>
          <th>Start</th>
          <th>Reason</th>
          <th>Destination</th>
          <th>Allowance</th>
        </thead>
        <tr v-for="travel in report.travels" :key="travel">
          <td>{{ travel.start }}</td>
          <td>{{ travel.reason }}</td>
          <td>{{ travel.destination }}</td>
          <td>{{ travel.allowance }} EUR</td>
        </tr>
      </table>

      <strong>Total sum: {{ report.totalSum }} EUR</strong>
    </div>
    <div v-else>No travels submitted yet.</div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import axios from "axios";
import { Report } from "../model";
export default class ExpenseReport extends Vue {
  report: Report = {} as Report;
  created() {
    console.log("loading");
    axios.get("/report").then((response) => {
      this.report = response.data as Report;
    });
  }
}
</script>
