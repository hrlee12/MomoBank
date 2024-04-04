<script setup>
defineProps({
  history: Object,
});
</script>

<template>
  <div class="history-content">
    <h3 class="item">{{ history.date }}</h3>
    <div
      v-for="(list, index) in history.transferlist"
      :key="index"
      class="history-item"
    >
      <div class="item">
        <h2>{{ list.name }}</h2>
        <h2 v-if="list.transferType" class="emphasize">
          {{ list.amount.toLocaleString("ko-KR") }}원
        </h2>
        <h2 v-else>-{{ list.amount.toLocaleString("ko-KR") }}원</h2>
      </div>
      <div class="item">
        <p>{{ list.date.slice(11, 16) }}</p>
        <p>{{ list.balance.toLocaleString("ko-KR") }}원</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";

.history-content {
  display: flex;
  flex-direction: column;
  background-color: white;
  border-radius: 15px;
  padding-bottom: 2vh;
}

.history-item {
  padding: 2% 0;
}

p {
  color: black;
}
</style>
