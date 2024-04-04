<script setup>
definePageMeta({
  layout: "action",
});
</script>

<template>
  <div class="input-container">
    <BankRemitAccountInfo />
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.input-container {
  height: 90%;
  width: 95%;
}
</style>
