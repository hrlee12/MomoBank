<script setup>
import CommonHeader from "~/components/layout/CommonHeader.vue";
</script>

<template>
  <CommonHeader />
  <!-- output the page content -->
  <div class="layout-container">
    <NuxtPage />
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
* {
  background-color: white;
}

.layout-container {
  background-color: white;
  height: 100%;
  min-height: 92vh;
  padding: 0;
}
</style>
