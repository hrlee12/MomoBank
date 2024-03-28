<script setup>
// TODO: 완료 버튼을 클릭했을 떄 작성 API를 호출해야함. CreateVoteType.vue 페이지에서도 마찬가지
const title = ref("");

const emit = defineEmits(["change-step", "complete-write"]);

function completeWrite() {
  emit("complete-write", true);
}

function goBeforeStep() {
  emit("change-step", "SelectAnnouncementType");
}
</script>
<template>
  <div class="px-6 py-6">
    <div class="mb-5 border-b-[1px] border-gray-color h-8">
      <input
        class="w-full"
        v-model="title"
        type="text"
        placeholder="제목 입력"
      />
    </div>
    <div class="mb-5 border-2 rounded-xl h-72">
      <textarea
        class="w-full h-full overflow-y-auto whitespace-normal"
      ></textarea>
    </div>
    <div class="mb-5 border-b-[1px] h-9 border-gray-color">
      <input
        type="file"
        accept="image/png, image/jpeg, image/gif"
        placeholder="이미지 추가"
      />
    </div>
    <div class="flex justify-between">
      <div>
        <button
          @click="goBeforeStep"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-negative-color hover:bg-red-700"
        >
          이전
        </button>
      </div>
      <div>
        <button
          @click="completeWrite"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
        >
          완료
        </button>
      </div>
    </div>
  </div>
</template>
