<script setup>
import AccountInformation from "~/components/group/AccountInformation.vue";

definePageMeta({
  layout: "groups",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const groupDetails = [
  { title: "모임명", content: "5반 5린이들", icon: "arrow-icon.png" },
  { title: "목적", content: "5반 5린이들 친해지자!", icon: "arrow-icon.png" },
  { title: "가용금액", content: "1,234,567원" },
  { title: "총 납부금액", content: "780,000원" },
  { title: "납부일자", content: "매월 11일" },
  { title: "전체금액", content: "2,345,678원" },
  { title: "모임인원", content: "6명", icon: "arrow-icon.png" },
];
</script>

<template>
  <div class="bg-white h-44 rounded-b-[14px]">
    <div class="flex flex-row justify-between">
      <div class="flex items-center justify-center w-10 h-6 ml-4 rounded-xl">
        <div class="text-[13px]"></div>
      </div>
      <div class="items-center">
        <p class="text-positive-color text-[13px]">납부 완료</p>
      </div>
      <div class="w-8 h-6 mr-4">
        <img
          class="rotate-90"
          :src="getImageUrl('arrow-icon.png', 0)"
          alt="arrow-icon"
        />
      </div>
    </div>
    <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
    <AccountInformation></AccountInformation>

    <!-- 매월 예산 납부 금액, 입금현황 -->
    <div class="flex justify-center mt-4">
      <!-- TODO : 각 모임 별 입금 현황이라 id 값으로 받아야함 즉, :to로 동적 경로로 설정해야함. -->
      <NuxtLink
        to="/groups/deposit-status"
        class="flex items-center justify-center w-64 border border-gray-300 rounded-xl h-9"
      >
        <div
          class="h-5 pr-2 text-sm border-r-2 border-sub-color text-sub-color"
        >
          매월 11일, 320,000원씩
        </div>
        <div class="pl-2 text-sm text-sub-color">입금 현황 ></div>
      </NuxtLink>
    </div>
  </div>

  <!-- 상세 정보 -->

  <div class="h-full pb-16 mt-4 bg-white">
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">모임명</div>
          <div class="font-semibold">5반5린이들</div>
        </div>

        <div class="h-5 rotate-180 w-7">
          <img
            :src="getImageUrl ? getImageUrl('arrow-icon.png', 0) : ''"
            alt="arrow-icon"
          />
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">목적</div>
          <div class="font-semibold">5반 5린이들 친해지자!</div>
        </div>

        <div class="h-5 rotate-180 w-7">
          <img
            :src="getImageUrl ? getImageUrl('arrow-icon.png', 0) : ''"
            alt="arrow-icon"
          />
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">가용 금액</div>
          <div class="font-semibold">1,234,567원</div>
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">총 납부 금액</div>
          <div class="font-semibold">780,000원</div>
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">납부 일자</div>
          <div class="font-semibold">매월 11일</div>
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <div
        class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
      >
        <div>
          <div class="text-xl font-bold">전체 금액</div>
          <div class="font-semibold">2,345,678원</div>
        </div>
      </div>
    </div>
    <div class="flex-col ml-3 mr-3">
      <NuxtLink to="/groups/view-all-group-members">
        <div
          class="flex items-center justify-between w-full pt-2 pb-2 border-b-2 border-light-gray-color"
        >
          <div>
            <div class="text-xl font-bold">모임 인원</div>
            <div class="font-semibold">6명</div>
          </div>

          <div class="h-5 rotate-180 w-7">
            <img
              :src="getImageUrl ? getImageUrl('arrow-icon.png', 0) : ''"
              alt="arrow-icon"
            />
          </div>
        </div>
      </NuxtLink>
    </div>
  </div>
</template>
