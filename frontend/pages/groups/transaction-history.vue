<script setup>
import AccountInformation from "~/components/group/AccountInformation.vue";

definePageMeta({
  layout: "groups",
});

// Define a method to dynamically require images
const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// TODO : 일단 API 안나와서 패스
//
</script>

<template>
  <div class="bg-white h-44 rounded-b-[14px]">
    <div>
      <!-- 상세, 납부 완료, 접기/펴기 아이콘 -->
      <div class="flex flex-row justify-between">
        <NuxtLink to="/groups/detail">
          <div class="flex items-center justify-center w-10 h-6 ml-4">
            <div></div>
          </div>
        </NuxtLink>

        <div class="items-center">
          <p class="text-positive-color text-[13px]">납부 완료</p>
        </div>
        <div class="w-8 h-6 mr-4"></div>
      </div>

      <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
      <AccountInformation></AccountInformation>
      <!-- 실선, 거래 내역, 예산 -->
      <div class="flex justify-center">
        <div class="mt-4 border-t-[1px] border-t-light-gray-color w-80"></div>
      </div>
      <div class="flex justify-center">
        <div class="flex mt-3 w-80">
          <nuxt-link to="/groups/transaction-history">
            <div
              class="font-semibold text-[17px] w-40 text-center border-r-[1px] text-main-color"
            >
              거래내역
            </div>
          </nuxt-link>

          <nuxt-link to="/groups/budget">
            <div
              class="font-semibold text-[17px] w-40 text-center text-main-color"
            >
              예산
            </div>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>

  <div class="mt-2 bg-white rounded-xl">
    <div class="px-6 py-6">
      <div>2월 29일</div>
    </div>
  </div>
</template>
