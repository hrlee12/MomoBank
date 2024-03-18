<script setup>
definePageMeta({
  layout: "meeting-header",
});

const getImageUrl = (imageName) => {
  return "/icon/" + imageName;
};
</script>

<template>
  <div>
    <!-- 상세, 납부 완료, 접기/펴기 아이콘 -->
    <div class="flex flex-row justify-between">
      <div
        class="bg-light-gray-color ml-4 rounded-xl w-10 h-6 flex justify-center items-center"
      >
        <div class="text-gray-color text-[13px]">상세</div>
      </div>
      <div class="items-center">
        <p class="text-positive-color text-[13px]">납부 완료</p>
      </div>
      <div class="w-8 h-6 mr-4">
        <img
          class="rotate-90"
          :src="getImageUrl('arrow-icon.png')"
          alt="arrow-icon"
        />
      </div>
    </div>

    <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
    <div></div>
  </div>
</template>
