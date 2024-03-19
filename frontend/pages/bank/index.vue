<script setup>
import KebabMenu from "@/components/KebabMenu.vue";
import BankAccount from "@/components/BankAccount.vue";

const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

definePageMeta({
  layout: "bank",
});
</script>

<template>
  <div class="bank-container">
    <div class="account-container">
      <!-- 메인 계좌 -->
      <BankAccount />
      <BankAccount />
      <BankAccount />
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
        <NuxtLink to="/bank/club-list">모임 더보기 ></NuxtLink>
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

  .account-container {
    overflow-x: auto; /* 수평 스크롤 활성화 */
    scroll-snap-type: x mandatory; /* X축으로 스냅 활성화 */
    display: flex; /* 슬라이드 항목들을 가로로 배치 */
    -webkit-overflow-scrolling: touch; /* iOS에서 부드러운 스크롤 */
    gap: 20px; /* 슬라이드 사이 간격 추가 */
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
