<script setup>
// Define a method to dynamically require images
const getImageUrl = (imageName) => {
  // Note: You might need to adjust the path depending on your project structure
  return "/icon/" + imageName;
};

definePageMeta({
  layout: "bank",
});
</script>

<template>
  <div class="bank-container">
    <div class="account-content">은행 계좌 화면</div>
    <div class="club-container">
      <div class="club-content">
        <div class="club-item">
          <h1>5반 5린이들</h1>
          <h2>160,000원</h2>
        </div>
        <div class="club-item">
          <p>5반 5린이들과 함께하는 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png')" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>
      <div class="club-content">
        <div class="club-item">
          <h1>5반 5린이들</h1>
          <h2>160,000원</h2>
        </div>
        <div class="club-item">
          <p>5반 5린이들과 함께하는 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png')" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>
      <div class="club-content">
        <div class="club-item">
          <h1>5반 5린이들</h1>
          <h2>160,000원</h2>
        </div>
        <div class="club-item">
          <p>5반 5린이들과 함께하는 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png')" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>

      <div class="club-item">모임 더보기 ></div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
h1 {
  font-size: 15px;
  font-weight: bold;
}

h2 {
  font-size: 14px;
  font-weight: bold;
}

p {
  font-size: 12px;
}

img {
  height: 3vh;
}

.bank-container {
  width: 90%;
  height: 100%;

  .club-container {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    border: 1px solid $light-gray-color;
    border-radius: 20px;
    padding: 1% 4% 0 4%;
    max-width: 600px;

    .club-item {
      display: flex;
      justify-content: space-between;
      padding: 4%;
      .icon-item {
        display: flex;
        justify-content: space-around;
        min-width: 45px;
      }
    }

    .club-item + .club-item {
      border-bottom: solid 1px $light-gray-color;
    }
  }
}
</style>
