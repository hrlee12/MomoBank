<script setup>
defineProps({
  accountName: String,
  accountNumber: String,
  money: Number,
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
      <button>송금</button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";

.content {
  width: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  border-radius: 15px;
  border: 1.5px solid $light-gray-color;
  padding: 0% 8%;
  box-shadow: 3px 3px 10px -3px $light-gray-color;

  .item {
    display: flex;
    justify-content: space-between;
    margin: 6% 0;

    p {
      align-self: center;
    }

    button {
      border: 1px solid $primary-color;
      border-radius: 20px;
      padding: 1% 4% 1% 4%;
      color: $primary-color;
      font-weight: bold;
    }
  }
}
</style>
