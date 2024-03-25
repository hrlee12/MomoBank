<script setup>
definePageMeta({
  layout: "groups",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="h-screen bg-white">
    <!-- 플러스 아이콘 -->
    <div class="flex justify-between">
      <div></div>
      <div class="w-8 h-8 mt-4 mr-4">
        <img :src="getImageUrl('add-icon2.png', 0)" alt="add-icon" />
      </div>
    </div>
    <div class="flex justify-center">
      <div
        class="flex flex-row items-center w-11/12 h-8 mt-4 font-bold rounded-3xl pl-7 bg-light-gray-color"
      >
        <div class="basis-1/4">이름</div>
        <div class="basis-2/3">신용점수</div>
        <div class="basis-1/4">총 6명</div>
      </div>
    </div>
    <div class="text-xs">
      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">이효리</div>
            <div class="w-4 h-4">
              <img :src="getImageUrl('red-crown.png', 0)" alt="red-crown" />
            </div>
          </div>
          <div class="pl-1 basis-4/12">100점</div>
        </div>
      </div>

      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">김성수</div>
            <div class="w-4 h-4">
              <img :src="getImageUrl('blue-crown.png', 0)" alt="red-crown" />
            </div>
          </div>
          <div class="pl-1 basis-4/12">105점</div>
          <div class="font-bold basis-3/12 text-main-color">납입금액수정</div>
          <div class="font-bold text-negative-color">추방</div>
        </div>
      </div>

      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">곽민우</div>
          </div>
          <div class="pl-1 basis-4/12">105점</div>
          <div class="font-bold basis-3/12 text-main-color">납입금액수정</div>
          <div class="font-bold text-negative-color">추방</div>
        </div>
      </div>

      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">명소이</div>
          </div>
          <div class="pl-1 basis-4/12">105점</div>
          <div class="font-bold basis-3/12 text-main-color">납입금액수정</div>
          <div class="font-bold text-negative-color">추방</div>
        </div>
      </div>

      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">손준성</div>
          </div>
          <div class="pl-1 basis-4/12">105점</div>
          <div class="font-bold basis-3/12 text-main-color">납입금액수정</div>
          <div class="font-bold text-negative-color">추방</div>
        </div>
      </div>

      <div class="flex justify-center">
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">엄세현</div>
          </div>
          <div class="pl-1 basis-4/12">105점</div>
          <div class="font-bold basis-3/12 text-main-color">납입금액수정</div>
          <div class="font-bold text-negative-color">추방</div>
        </div>
      </div>
    </div>
  </div>
</template>
