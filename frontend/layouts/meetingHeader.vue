<script setup>
const getImageUrl = (imageName) => {
  return "/icon/" + imageName;
};
</script>

<template>
  <div class="bg-white h-60 rounded-b-[14px]">
    <div class="pt-4">
      <header class="flex flex-row h-11 justify-between">
        <div>
          <img
            class="w-10 h-8 ml-4"
            :src="getImageUrl('arrow-icon.png')"
            alt="arrow-icon 화살표 아이콘"
          />
        </div>
        <div class="text-xl font-semibold">5반 5린이들</div>
        <div>
          <div class="flex">
            <img
              class="w-7 h-7 mr-4"
              :src="getImageUrl('bell-icon.png')"
              alt="bell-icon"
            />
            <img
              class="w-7 h-7 mr-4"
              :src="getImageUrl('home-icon.png')"
              alt="home-icon"
            />
          </div>
        </div>
      </header>
      <NuxtPage />
    </div>
  </div>
</template>
