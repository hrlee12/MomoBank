<script setup>
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// TODO : 입금현황 사용자별로 보려면 ROUTER ID가 서로 다를테니까 ID 별로해서 해당 USER의 정보를 뿌려주면 될 것 같음.
const memberList = [
  { name: "손준성", image: "" },
  { name: "명소이", image: "" },
  { name: "김성수", image: "" },
  { name: "곽민우", image: "" },
  { name: "이효리", image: "" },
  { name: "엄세현", image: "" },
];

const deposit = [
  { month: "3월", pay: "5,000원" },
  { month: "4월", pay: "0원" },
  { month: "5월", pay: "25,000원" },
];

// TODO : 년도별로 입금 현황 보기
// 예시 코드(notion/TODAY/2024=03-21)
definePageMeta({
  layout: "groups",
});
</script>

<template>
  <div class="h-screen bg-white">
    <!-- 모임 멤버 프로필 리스트 -->
    <div class="flex justify-around pt-9 pb-11">
      <div
        class="flex flex-col items-center"
        v-for="item in memberList"
        :key="item.id"
      >
        <div>
          <img
            class="rounded-full w-11 h-11"
            :src="
              getImageUrl === ''
                ? getImageUrl(item.image)
                : getImageUrl('image-1.png', 1)
            "
            alt="profile-image"
          />
        </div>
        <div class="pt-1 text-xs">{{ item.name }}</div>
      </div>
    </div>
    <!-- 년도, 총 금액 bar -->
    <div
      class="flex items-center justify-between w-11/12 h-8 mx-auto bg-light-gray-color rounded-xl"
    >
      <div class="pl-3 font-bold">2024년</div>
      <div class="pr-3 font-bold">총 45,000원</div>
    </div>
    <!-- 해당 년도 납부 정보 리스트 -->
    <div
      class="border-b-[2px] w-11/12 mx-auto"
      v-for="item in deposit"
      :key="item.id"
    >
      <div class="flex justify-between pb-3 pl-3 pr-3 mt-2">
        <div>{{ item.month }}</div>
        <div>{{ item.pay }}</div>
      </div>
    </div>

    <!-- 입금 내역 년도별 더보기 버튼 -->
    <div
      class="flex items-center justify-center w-5/12 mx-auto mt-8 rounded-lg h-11 bg-light-gray-color"
    >
      <div class="mx-auto font-bold">2023년 더보기</div>
    </div>
  </div>
</template>
