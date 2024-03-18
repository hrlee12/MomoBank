<script setup>
const getImageUrl = (imageName) => {
  return "/icon/" + imageName;
};
</script>

<template>
  <header class="flex flex-row mt-6 h-11 justify-between">
    <div class="basis-1/4">
      <img class="w-10 h-8 ml-4" :src="getImageUrl('arrow-icon.png')" alt="" />
    </div>
    <div class="items-center text-xl font-semibold">5반 5린이들</div>
    <div>
      <div class="flex">
        <img class="w-7 h-7 mr-4" :src="getImageUrl('bell-icon.png')" alt="" />
        <img class="w-7 h-7 mr-4" :src="getImageUrl('home-icon.png')" alt="" />
      </div>
    </div>
  </header>
</template>
