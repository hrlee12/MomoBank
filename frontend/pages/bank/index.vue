<script setup>
import KebabMenuSample from "@/components/KebabMenuSample.vue";

// Define a method to dynamically require images
const getImageUrl = (imageName, idx) => {
  if (idx == 0)
    // Note: You might need to adjust the path depending on your project structure
    return "/icon/" + imageName;
  else return "/images/" + imageName;
};

definePageMeta({
  layout: "bank",
});
</script>

<template>
  <div class="bank-container">
    <!-- 메인 계좌 -->
    <div class="account-content">
      <div class="account-item">
        <div class="account-info">
          <h2>저축예금</h2>
          <div class="account-no">
            <p>입출금 xxx-xxxxx-xxxxx</p>
            <p>복사</p>
          </div>
        </div>
        <!-- <KebabMenu /> -->
        <KebabMenuSample />
      </div>
      <div class="money-content">
        <h1>1,000,000원</h1>

        <button><p>숨김</p></button>
      </div>

      <div class="link-content">
        <NuxtLink to="/momo/bank/history">거래내역</NuxtLink>
        <NuxtLink to="/momo/bank/remit">송금하기</NuxtLink>
      </div>
    </div>

    <div class="pagination">page</div>

    <!-- 메인 모임 리스트 -->
    <div class="club-container">
      <div class="club-content">
        <div class="club-item">
          <h2>5반 5린이들</h2>
          <h3>160,000원</h3>
        </div>
        <div class="club-item">
          <p>5반 5린이들과 함께하는 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png', 0)" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>
      <div class="club-content">
        <div class="club-item">
          <h2>달려라 자전거</h2>
          <h3>160,000원</h3>
        </div>
        <div class="club-item">
          <p>자전거 스프린터들의 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png', 0)" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>
      <div class="club-content">
        <div class="club-item">
          <h2>두근두근 여행모</h2>
          <h3>160,000원</h3>
        </div>
        <div class="club-item">
          <p>5반 5린이들과 함께하는 모임</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png', 0)" alt="" />
            <p>6명</p>
          </div>
        </div>
      </div>

      <div class="center club-item">
        <NuxtLink to="/momo/bank/club-list">모임 더보기 ></NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

img {
  height: 3vh;
}

.bank-container {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .pagination {
    text-align: center;
    margin: 2vh;
  }

  .account-content {
    width: 80%;
    min-height: 200px;
    padding: 2vh;
    position: relative;
    margin: 0 auto;
    overflow: hidden; /* 현재 슬라이드 오른쪽에 위치한 나머지 슬라이드 들이 보이지 않도록 가림 */
    text-align: center;
    border: 1px solid #a3a3a3;
    border-radius: 20px;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .account-item {
      display: flex;
      justify-content: space-between;
      width: 100%;
      padding: 0 5% 0 5%;
      text-align: left;

      .account-no {
        display: flex;
      }
    }

    .money-content {
      display: flex;
      justify-content: center;

      h1 {
        margin-right: 2%;
      }

      button {
        background-color: $light-gray-color;
        border-radius: 20px;
        min-width: 40px;
        color: $gray-color;
      }
    }

    .link-content {
      display: flex;
      justify-content: space-around;
      border-top: 1px solid $light-gray-color;
      width: 90%;
      align-self: center;
      padding-top: 2vh;

      a {
        color: $primary-color;
        font-weight: bold;
      }
    }
  }

  .club-container {
    width: 85%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    border: 1px solid $light-gray-color;
    border-radius: 20px;
    // padding: 2% 5% 1% 5%;
    padding: 1vh;
    background-color: white;
    align-self: center;

    .center {
      align-self: center;
      color: $gray-color;
    }
    .club-item {
      display: flex;
      justify-content: space-between;
      padding: 1.5vh;

      p {
        max-width: 60%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }

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
