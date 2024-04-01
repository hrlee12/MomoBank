<script setup>
import BankFooter from "~/components/layout/BankFooter.vue";
import CommonHeader from "~/components/layout/CommonHeader.vue";
</script>

<template>
  <CommonHeader />
  <!-- output the page content -->
  <div class="layout-container">
    <NuxtPage />
  </div>

  <BankFooter />
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
</style>
