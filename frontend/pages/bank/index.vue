<script setup>
// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

definePageMeta({
  layout: "bank",
});

// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";
function onSlideChange(swiper) {
  // 마지막 슬라이드에 도달했는지 확인
  isLastSlide.value = swiper.isEnd;
}

// 슬라이드 데이터
const accounts = ref([
  { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
  { accountName: "효리은행", accountNumber: "123-1234-12346", money: 2000000 },
  { accountName: "성수은행", accountNumber: "123-1234-12347", money: 1000 },
]);
const isLastSlide = ref(false);
</script>

<template>
  <div class="bank-container">
    <Swiper
      :modules="[SwiperPagination]"
      :spaceBetween="15"
      :slides-per-view="1"
      :slidesPerView="1.2"
      :centeredSlides="true"
      :pagination="{ clickable: true }"
      @slideChange="onSlideChange"
      class="mySwiper"
    >
      <SwiperSlide v-for="(account, index) in accounts" :key="index">
        <BankAccount
          :accountName="account.accountName"
          :accountNumber="account.accountNumber"
          :money="account.money"
        />
      </SwiperSlide>

      <SwiperSlide>
        <BankAccount />
      </SwiperSlide>
    </Swiper>

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

.mySwiper {
  position: relative;
  padding-bottom: 5vh;
}

img {
  height: 3vh;
}

.bank-container {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .club-container {
    width: 85%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    border: 1px solid $light-gray-color;
    border-radius: 20px;
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
