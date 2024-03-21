<script setup>
const getImageUrl = (imageName) => {
  return "/icon/" + imageName;
};

const goBack = () => {
  window.history.back();
};
</script>

<template>
  <div class="pt-4">
    <header class="flex flex-row justify-between h-11">
      <div @click="goBack">
        <img
          class="w-10 h-8 ml-4"
          :src="getImageUrl('arrow-icon.png')"
          alt="arrow-icon 화살표 아이콘"
        />
      </div>
      <div class="ml-8 text-xl font-semibold">5반 5린이들</div>
      <div>
        <div class="flex">
          <img
            class="mr-4 w-7 h-7"
            :src="getImageUrl('bell-icon.png')"
            alt="bell-icon"
          />
          <img
            class="mr-4 w-7 h-7"
            :src="getImageUrl('home-icon.png')"
            alt="home-icon"
          />
        </div>
      </div>
    </header>
  </div>
</template>
