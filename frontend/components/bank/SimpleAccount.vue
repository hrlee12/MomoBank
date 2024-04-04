<script setup>
defineProps({
  accountName: String,
  accountNumber: String,
  money: Number,
  status: Boolean,
});
</script>

<template>
  <div class="content">
    <div class="item">
      <h2>{{ accountName }}</h2>
      <h2>{{ money.toLocaleString("ko-KR") }}원</h2>
    </div>
    <div class="item">
      <p>입출금 {{ accountNumber }}</p>
      <button v-if="status == true">송금</button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";
.content {
  padding: 0% 8%;
}
.item {
  margin: 4% 0;
}

button {
  border: 1px solid $primary-color;
  border-radius: 20px;
  padding: 0 3%;
  color: $primary-color;
  font-weight: bold;
}
</style>
