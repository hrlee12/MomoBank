<script setup></script>

<template>
  <!-- output the page content -->
  <div class="layout-container">
    <slot />
  </div>

  <BankFooter />
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
</style>
