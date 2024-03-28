<script setup>
defineProps({
  id: String,
  name: String,
  benefits: Array,
  imgUrl: String,
  corpName: String,
});
</script>

<template>
  <div class="container">
    <img :src="`${imgUrl}`" alt="" />

    <div class="card-content">
      <div class="left">
        <h3>{{ name }}</h3>
        <p>{{ corpName }}</p>
      </div>
      <div class="right">
        <p class="emphasize">{{ benefits[0] }}</p>
        <p class="emphasize">{{ benefits[1] }}</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/card.scss";

.container {
  display: flex;
  justify-content: space-evenly;
  width: 100%;
  padding: 4% 0;
}

.card-content {
  width: 75%;
  display: flex;
  justify-content: space-between;
}
</style>
