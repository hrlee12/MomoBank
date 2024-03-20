<script setup>
definePageMeta({
  layout: "bank",
});
</script>

<template>
  <Swiper :modules="[SwiperPagination]" :slides-per-view="1" :loop="true">
    <SwiperSlide v-for="slide in 10" :key="slide">
      <BankAccount />
    </SwiperSlide>
  </Swiper>
</template>

<style lang="scss" scoped></style>
