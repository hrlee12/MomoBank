<script setup>
defineProps({
  id: "",
  name: "",
  benefits: Array,
  interest: Number,
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
      <div class="card-item">
        <p
          v-if="benefits !== undefined"
          v-for="(benefit, index) in benefits"
          :key="index"
          class="emphasize"
        >
          {{ benefit }}
        </p>
        <h3 v-else class="right emphasize">최고 연 {{ interest }}%</h3>
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

.card-item {
  display: flex;
  justify-content: end;
  width: 100%;
}
</style>
