<template>
  <form @submit.prevent="submitForm">
    <label for="Start">Start</label>
    <input type="text" name="Start" id="Start" v-model="form.start" />
    <br />

    <label for="End">End</label>
    <input type="text" name="End" id="End" v-model="form.end" />
    <br />

    <label for="Destination">Destination</label>
    <input type="text" name="Destination" id="Destination" v-model="form.destination" />
    <br />

    <label for="Reason">Reason</label>
    <input type="text" name="Reason" id="Reason" v-model="form.reason" />
    <br />

    <button>Submit</button>
  </form>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Emit } from "vue-property-decorator";
import axios from "axios";
export default class EntryForm extends Vue {
  form = {
    start: "",
    end: "",
    destination: "",
    reason: "",
  };
  @Emit("entry-submitted")
  public submitForm() {
    return axios.post("/submit", this.form);
  }
}
</script>
