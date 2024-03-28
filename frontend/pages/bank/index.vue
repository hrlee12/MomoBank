<script setup>
import BankAccount from "@/components/bank/BankAccount.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { useRouter } from "vue-router";

const router = useRouter();

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

definePageMeta({
  layout: "bank-home",
});

function onSlideChange(swiper) {
  // 마지막 슬라이드에 도달했는지 확인
  isLastSlide.value = swiper.isEnd;
}

// 슬라이드 데이터
const accounts = ref([
  {
    accountId: 0,
    accountType: "입출금",
    accountName: "저축은행",
    accountNumber: "123-1234-12345",
    balance: 1000000,
  },
  {
    accountId: 1,
    accountType: "입출금",
    accountName: "효리은행",
    accountNumber: "123-1234-12345",
    balance: 2000000,
  },
  {
    accountId: 2,
    accountType: "입출금",
    accountName: "소이은행",
    accountNumber: "123-1234-12345",
    balance: 3000000,
  },
]);
const isLastSlide = ref(false);

// 각각의 그룹 페이지로 이동
const goToGroup = (param) => {
  router.push(`/groups`);
};
</script>

<template>
  <header>
    <nav>
      <div class="menu">
        <!-- 나중에 /bank/profile로 바꿀 것 -->
        <NuxtLink to="/bank/profile" class="font-bold">
          <!-- getImageUrl함수가 존재할 때만 동작 -->
          <img
            :src="getImageUrl ? getImageUrl('user-icon.png', 0) : ''"
            alt=""
          />
          <h1>엄세현</h1></NuxtLink
        >

        <NuxtLink to="/bank/account-list" class="list-link">전체계좌</NuxtLink>
      </div>
      <NuxtLink to="/bank/notice">
        <img :src="getImageUrl ? getImageUrl('bell-icon.png', 0) : ''" alt=""
      /></NuxtLink>
    </nav>
  </header>

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
        <BankAccount :accountInfo="account" />
      </SwiperSlide>

      <SwiperSlide>
        <BankAccount />
      </SwiperSlide>
    </Swiper>

    <!-- 메인 모임 리스트 -->
    <div class="club-container content">
      <div class="club-content" @click="goToGroup('5반 5린이들')">
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
        <NuxtLink to="/bank/group-list">모임 더보기 ></NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
@import "@/assets/css/content.scss";

.account-content {
  border: 1px solid #a3a3a3;
}

h1 {
  padding-top: 0.5vh;
  font-size: 2vw 5vh;
  border-bottom: 1px solid black;
}

.mySwiper {
  position: relative;
  padding-bottom: 5vh;
}

header {
  width: 100%;
  padding: 2vh;

  nav {
    display: flex;
    justify-content: center;

    img {
      height: 4.5vh;
    }

    .menu {
      display: flex;
      min-width: 80%;

      a {
        display: flex;
        margin-right: 4%;
        justify-content: center;
      }

      .list-link {
        border-radius: 30px;
        border: 1px solid $gray-color;
        font-size: 1vw 2vh;
        color: $gray-color;
        margin-top: 0.5vh;
        min-width: 100px;
        width: 14vw;
        padding-top: 0.5vh;
        font-size: 2vh;
        height: 4.5vh;
        text-align: center;
      }
    }
  }
}

.bank-container {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .club-container {
    width: 85%;
    padding: 1vh;
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
    }

    .club-item + .club-item {
      border-bottom: solid 1px $light-gray-color;
    }
  }
}
</style>
