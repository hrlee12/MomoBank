<script setup>
import BankAccount from "@/components/bank/BankAccount.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { useRouter } from "vue-router";
import { useBankApi } from "@/api/bank";

const { getMyAccountList } = useBankApi();

import { useGroupApi } from "~/api/groups";

const { getMyGroups } = useGroupApi();

const myGroups = ref([]);

// remitStore 사용
const remitStore = useRemitStore();

const memberId = remitStore.memberId;

const fetchMyGroups = async () => {
  try {
    const response = await getMyGroups(memberId);
    return response.data;
  } catch (error) {
    console.error("나의 모임 목록을 불러오는 데 실패했습니다.", error);
  }
};

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
const myAccountList = ref([]);
const isLastSlide = ref(false);

// 각각의 그룹 페이지로 이동
const goToGroup = (groupId) => {
  router.push(`/groups/` + groupId);
};

// 전체 계좌 리스트 받는 함수
onMounted(async () => {
  try {
    const memberId = remitStore.memberId; // 예시 ID
    const response = await getMyAccountList(memberId);
    myAccountList.value = response.data.data.myAccountList;
  } catch (error) {
    console.error(error);
  }

  fetchMyGroups(memberId).then((response) => {
    myGroups.value = response.data.groupList.slice(0, 3);
    console.log(myGroups.value);
  });
});
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
          <h1>{{ remitStore.memberName }}</h1></NuxtLink
        >

        <NuxtLink to="/bank/account-list" class="list-link">전체계좌</NuxtLink>
      </div>
      <NuxtLink to="/">
        <img :src="getImageUrl ? getImageUrl('logout-icon.png', 0) : ''" alt=""
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
      <SwiperSlide v-for="(account, index) in myAccountList" :key="index">
        <BankAccount :accountInfo="account" />
      </SwiperSlide>

      <SwiperSlide>
        <BankAccount />
      </SwiperSlide>
    </Swiper>

    <!-- 메인 모임 리스트 -->
    <div class="club-container content">
      <div
        v-for="(group, index) in myGroups"
        :key="index"
        class="club-content"
        @click="goToGroup(group.groupId)"
      >
        <div class="club-item">
          <h2>{{ group.name }}</h2>

          <h3 v-if="group.monthlyFee !== null">
            {{ group.monthlyFee.toLocaleString("ko-KR") }}원
          </h3>
          <h3 v-else>{{ group.monthlyFee }}원</h3>
        </div>
        <div class="club-item">
          <p>{{ group.description }}</p>
          <div class="icon-item">
            <img :src="getImageUrl('user-icon-1.png', 0)" alt="" />
            <p>{{ group.joinMembers }}명</p>
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

.logout {
  min-width: 50px !important;
}

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
