<script setup>
import { useRoute } from "#vue-router";

const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const goBack = () => {
  window.history.back();
};

const route = useRoute();

const groupsName = "5반5린이들";

// TODO : pinia로 관리할지, Header에서 직접 관리할지.
const pageTitle = computed(() => {
  if (route.name === "groups") {
    return groupsName;
  } else if (route.name === "groups-detail") {
    return "모임 상세";
  } else if (route.name === "groups-deposit-status") {
    return "입금 현황";
  } else if (route.name === "groups-my-feed") {
    return "나의 모임";
  } else if (route.name === "groups-report") {
    return "리포트";
  } else if (route.name === "groups-view-all-group-members") {
    return "모임 인원";
  }
});
</script>

<template>
  <header class="flex flex-row justify-between pt-3 bg-white h-14">
    <div @click="goBack">
      <img
        class="w-10 h-8 ml-4"
        :src="getImageUrl('arrow-icon.png', 0)"
        alt="arrow-icon 화살표 아이콘"
      />
    </div>
    <div class="ml-8 text-xl font-semibold">{{ pageTitle }}</div>
    <div>
      <div class="flex">
        <img
          class="mr-4 w-7 h-7"
          :src="getImageUrl('bell-icon.png', 0)"
          alt="bell-icon"
        />
        <img
          class="mr-4 w-7 h-7"
          :src="getImageUrl('home-icon.png', 0)"
          alt="home-icon"
        />
      </div>
    </div>
  </header>
</template>
