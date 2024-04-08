<script setup>
const emit = defineEmits(["change-step", "complete-write"]);

function completeWrite() {
  emit("complete-write", true);
}

function goBeforeStep() {
  emit("change-step", "SelectAnnouncementType");
}

const voteItems = ref([{ id: 1, value: "" }]);
const voteTitle = ref("");

let nextItemId = 2;

function addVoteItem() {
  voteItems.value.push({ id: nextItemId++, value: "" });
}

function removeVoteItem(itemId) {
  voteItems.value = voteItems.value.filter((item) => item.id !== itemId);
}
</script>
<template>
  <div class="px-6 py-6 bg-white">
    <div class="h-10 border-b-[1px] border-light-gray-color">
      <input
        v-model="voteTitle"
        type="text"
        name="title"
        id="title"
        placeholder="제목 입력"
        class="text-lg font-semibold text-gray-color"
      />
    </div>

    <div
      v-for="item in voteItems"
      :key="item.id"
      class="flex justify-between items-center h-10 border-b-[1px] border-light-gray-color mt-2"
    >
      <input
        type="text"
        v-model="item.value"
        name="voteItem"
        :id="'voteItem' + item.id"
        placeholder="투표 항목"
        class="text-lg font-semibold text-gray-color"
      />
      <div
        v-if="voteItems[voteItems.length - 1].id === item.id"
        @click="addVoteItem"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>+</div>
      </div>
      <div
        v-if="
          voteItems.length > 1 && item.id !== voteItems[voteItems.length - 1].id
        "
        @click="removeVoteItem(item.id)"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>-</div>
      </div>
    </div>
  </div>

  <div class="flex justify-between px-6 py-6 pb-16 bg-white">
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
</template>
