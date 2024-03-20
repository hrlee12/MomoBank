<script setup>
import BankFooter from "~/components/layout/BankFooter.vue";
</script>

<template>
  <!-- output the page content -->
  <div class="layout-container">
    <NuxtPage />
  </div>

  <BankFooter />
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
</style>
