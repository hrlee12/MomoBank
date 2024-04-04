<script setup>
import CommonHeader from "~/components/layout/CommonHeader.vue";
import BankFooter from "~/components/layout/BankFooter.vue";
import { useRoute } from "#vue-router";
import { ref } from "vue";

const router = useRoute();

const footerActive = ref(false);
const id = router.params.id;
if (id && router.name.startsWith("bank-")) {
  footerActive.value = true;
}
</script>

<template>
  <CommonHeader />
  <!-- output the page content -->
  <div class="layout-container">
    <NuxtPage />
  </div>
  <BankFooter v-if="footerActive" />
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
* {
  width: 100%;
  background-color: white;
}

.layout-container {
  background-color: white;
  min-height: 92vh;
  padding: 0;
  display: flex;
  justify-content: center;
}
</style>
