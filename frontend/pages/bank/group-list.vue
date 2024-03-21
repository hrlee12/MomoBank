<script setup>
import { useMenuStore } from "~/stores/menu-store";

const menuStore = useMenuStore();

menuStore.updateMenuTitle("전체모임");

definePageMeta({
  layout: "bank",
});
</script>

<template>
  <div></div>
</template>

<style lang="scss" scoped></style>
