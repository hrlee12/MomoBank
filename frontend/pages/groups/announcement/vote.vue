<script setup>
definePageMeta({
  layout: "groups",
});

const voteList = [
  { name: "우리 이제 일본 여행 간다!" },
  { name: "우리 이제 대만 여행 간다!" },
  { name: "우리 이제 중국 여행 간다!" },
  { name: "버스커 버스커" },
];
</script>
<template>
  <div class="h-screen bg-white">
    <div class="px-4">
      <div
        class="flex pt-10 text-2xl font-bold border-b-[1px] pb-3 mb-4 border-light-gray-color"
      >
        <div>
          [중요] 중요!! 피드 게시글에 욕설을 자제 부탁드립니다!!!!!!! 유유유
        </div>
      </div>
      <div class="flex justify-between mb-8">
        <div>작성자:김성수</div>
        <div>작성일:2024.3.12 화요일</div>
      </div>
      <div class="h-64 px-6 py-6 mb-4 overflow-y-auto border-2">
        <div class="py-2 text-lg" v-for="item in voteList" :key="item.id">
          <input
            class="border-2 border-main-color"
            type="radio"
            :value="item.name"
            :id="item.name"
            name="vote"
          />
          <label class="pl-2" :for="item.name">{{ item.name }}</label>
        </div>
      </div>
    </div>
    <div class="flex justify-end px-4 mt-4 space-x-4">
      <button
        class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
      >
        선택
      </button>
    </div>
  </div>
</template>
