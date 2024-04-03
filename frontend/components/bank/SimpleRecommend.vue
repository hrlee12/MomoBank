<script setup>
defineProps({
  id: "",
  name: "",
  benefits: Array,
  interest: Number,
  imgUrl: String,
  corpName: String,
  description: "",
});
</script>

<template>
  <div class="card-list-container">
    <img :src="`${imgUrl}`" alt="" />

    <div class="card-content">
      <div class="left">
        <h3>{{ name }}</h3>
        <p>{{ corpName }}</p>
        <p class="desc">{{ description }}</p>
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
        <h3 v-else class="emphasize">최고 연 {{ interest }}%</h3>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/card.scss";

.card-list-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  width: 100% !important;
  padding: 4% 0;
}

.card-content {
  width: 75%;
  display: flex;
  justify-content: space-between;
}

.card-item {
  display: flex;
  flex-direction: column;
  align-self: center;
  width: 100%;
}

h3 {
  font-size: 2vh;
}
.emphasize {
  font-size: 1.8vh;
  text-align: right;
}

p {
  font-size: 1.7vh;
}

.desc {
  font-size: 1.3vh;
}
</style>
