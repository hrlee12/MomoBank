<script setup>
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
</script>
<template>
  <div class="h-screen bg-white">
    <div class="flex justify-between">
      <div></div>
      <div class="w-8 h-8 mt-4 mr-4">
        <img :src="getImageUrl('add-icon2.png', 0)" alt="add-icon" />
      </div>
    </div>

    <nuxt-link to="/groups/announcement-detail">
      <div class="flex justify-center">
        <div
          class="flex flex-col justify-center-center w-11/12 h-11 mt-8 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center pb-2 text-sm font-bold h-1/6">
            <div class="w-9/12 overflow-hidden whitespace-nowrap text-ellipsis">
              [투표] 드디어 일본 여행 Let's go!!!
            </div>
          </div>
          <div class="mt-4 text-xs">
            <div class="flex items-center">
              <div>2024.3.12</div>
              <div class="ml-2">김성수</div>
              <div class="w-3 h-3 ml-1">
                <img :src="getImageUrl('blue-crown.png', 0)" alt="파란 왕관" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </nuxt-link>

    <div class="flex justify-center">
      <div
        class="flex flex-col justify-center-center w-11/12 h-11 mt-8 pl-5 border-b-[1px] border-light-gray-color"
      >
        <div class="flex items-center pb-2 text-sm font-bold h-1/6">
          <div class="w-9/12 overflow-hidden whitespace-nowrap text-ellipsis">
            [투표] 다음 모임 장소 투표
          </div>
        </div>
        <div class="mt-4 text-xs">
          <div class="flex items-center">
            <div>2024.3.12</div>
            <div class="ml-2">김성수</div>
            <div class="w-3 h-3 ml-1">
              <img :src="getImageUrl('blue-crown.png', 0)" alt="파란 왕관" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex justify-center">
      <div
        class="flex flex-col justify-center-center w-11/12 h-11 mt-8 pl-5 border-b-[1px] border-light-gray-color"
      >
        <div class="flex items-center pb-2 text-sm font-bold h-1/6">
          <div class="w-11/12 overflow-hidden whitespace-nowrap text-ellipsis">
            [중요] 피드에 욕설하시면안됩니다. 자제
            부탁드립니다.!!!!!!!!!!!!!!!!!!
          </div>
        </div>
        <div class="mt-4 text-xs">
          <div class="flex items-center">
            <div>2024.3.12</div>
            <div class="ml-2">김성수</div>
            <div class="w-3 h-3 ml-1">
              <img :src="getImageUrl('blue-crown.png', 0)" alt="파란 왕관" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex justify-center">
      <div
        class="flex flex-col justify-center-center w-11/12 h-11 mt-8 pl-5 border-b-[1px] border-light-gray-color"
      >
        <div class="flex items-center pb-2 text-sm font-bold h-1/6">
          <div class="pr-1">[수요조사]&nbsp;</div>
          <div class="w-9/12 overflow-hidden whitespace-nowrap text-ellipsis">
            [수요조사] 다음 모임 참석자 수요조사
          </div>
        </div>
        <div class="mt-4 text-xs">
          <div class="flex items-center">
            <div>2024.3.12</div>
            <div class="ml-2">김성수</div>
            <div class="w-3 h-3 ml-1">
              <img :src="getImageUrl('blue-crown.png', 0)" alt="파란 왕관" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
