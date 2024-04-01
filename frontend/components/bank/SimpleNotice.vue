<script setup>
defineProps({
  title: String,
  content: String,
  date: String,
  status: Number,
});
</script>

<template>
  <div class="content" :class="{ 'gray-bg': status === 1 }">
    <div class="item">
      <h2>{{ title }}</h2>
      <p>{{ date }}</p>
    </div>
    <h3>{{ content }}</h3>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";

.content {
  padding: 3%;
  border: none;
  box-shadow: none;
  border-radius: 0;
}

.gray-bg {
  background-color: $light-gray-color;
  color: $gray-color;
}
</style>
