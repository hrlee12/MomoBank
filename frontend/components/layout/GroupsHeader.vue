<script setup>
import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();

const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const noSideMenu = ref(false);
const noArrowMenu = ref(false);

const goBack = () => {
  window.history.back();
};

const route = useRoute();

const groupsName = computed(() => groupStore.groupHeaderName);

// const groupId = computed(() => route.params.groupId);

// console.log(route.name);

// TODO : pinia로 관리할지, Header에서 직접 관리할지.
const pageTitle = computed(() => {
  noSideMenu.value = false;
  noArrowMenu.value = false;
  if (
    route.name === "groups-groupId" ||
    route.name === "groups-budget" ||
    route.name === "groups-transaction-history"
  ) {
    return groupsName.value;
  } else if (route.name === "groups-detail-groupId") {
    return "모임 상세";
  } else if (route.name === "groups-deposit-status") {
    return "입금 현황";
  } else if (route.name === "groups-feed-my") {
    return "나의 모임";
  } else if (route.name === "groups-report") {
    return "리포트";
  } else if (route.name === "groups-members") {
    return "모임 인원";
  } else if (
    route.name === "groups-announcement-groupId" ||
    route.name === "groups-announcement-detail" ||
    route.name === "groups-announcement-vote"
  ) {
    return "공지사항";
  } else if (route.name === "groups-announcement-add") {
    noSideMenu.value = true;
    return "공지사항";
  } else if (
    route.name == "groups-announcement-vote-edit" ||
    route.name == "groups-announcement-normal-edit"
  ) {
    noSideMenu.value = true;
    return "공지사항 수정";
  } else if (route.name === "groups-feed-write") {
    noSideMenu.value = true;
    return "새 게시물";
  } else if (route.name === "groups-feed-detail") {
    noSideMenu.value = true;
    return "게시물";
  } else if (route.name === "groups-budget-add") {
    noSideMenu.value = true;
    return "예산 추가";
  } else if (route.name === "groups-budget-budgetId") {
    noSideMenu.value = true;
    return "예산 상세";
  } else if (route.name === "groups-menu") {
    noSideMenu.value = true;
    return "추가 메뉴";
  }
});
</script>

<template>
  <header class="flex items-center justify-between pt-1 bg-white h-14">
    <div v-if="noArrowMenu === false">
      <div @click="goBack">
        <img
          class="w-10 h-8 ml-4"
          :src="getImageUrl('arrow-icon.png', 0)"
          alt="arrow-icon 화살표 아이콘"
        />
      </div>
    </div>
    <div v-if="noArrowMenu === true">
      <div>
        <div class="w-10 h-8 ml-6" alt="arrow-icon 화살표 아이콘"></div>
      </div>
    </div>

    <div class="ml-8 text-xl font-semibold">{{ pageTitle }}</div>
    <div v-if="noSideMenu === false">
      <div class="flex">
        <img
          class="mr-4 w-7 h-7"
          :src="getImageUrl('bell-icon.png', 0)"
          alt="bell-icon"
        />
        <nuxt-link to="/bank">
          <img
            class="mr-4 w-7 h-7"
            :src="getImageUrl('home-icon.png', 0)"
            alt="home-icon"
          />
        </nuxt-link>
      </div>
    </div>
    <div class="flex" v-if="noSideMenu === true">
      <div class="mr-4 w-7 h-7"></div>
      <div class="mr-4 w-7 h-7"></div>
    </div>
  </header>
</template>
