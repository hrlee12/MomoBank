<script setup>
import CompleteModal from "~/components/layout/CompleteModal.vue";

// TODO : 완료 버튼 눌렀을 떄 수정 API 호출
definePageMeta({
  layout: "groups",
});

const completeWrite = ref(false);

const alongButton = ref(true);

const goBack = () => {
  window.history.back();
};

const announcement = {
  id: 1,
  title: "5반 5린이들 공지사항 ",
  vote: [{ value: "투표1" }, { value: "투표2" }, { value: "투표3" }],
};

const voteItems = ref(announcement.vote);

function addVoteItem() {
  voteItems.value.push({ value: "" });
}

function removeVoteItem(itemValue) {
  voteItems.value = voteItems.value.filter((item) => item.value !== itemValue);
}

function completeWriteEdit() {
  completeWrite.value = true;
}
function okButton(changeComplte) {
  completeWrite.value = changeComplte;
}
</script>
<template>
  <div class="h-screen px-6 py-6 bg-white">
    <div class="h-10 border-b-[1px] border-light-gray-color">
      <input
        v-model="announcement.title"
        type="text"
        name="title"
        id="title"
        placeholder="제목 입력"
        class="text-lg font-semibold text-gray-color"
      />
    </div>

    <div
      v-for="item in announcement.vote"
      :key="item.value"
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
        v-if="voteItems[voteItems.length - 1].value === item.value"
        @click="addVoteItem"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>+</div>
      </div>
      <div
        v-if="
          voteItems.length > 1 &&
          item.value !== voteItems[voteItems.length - 1].value
        "
        @click="removeVoteItem(item.value)"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>-</div>
      </div>
    </div>
    <div class="flex justify-between px-6 py-6 pb-16 bg-white">
      <div>
        <button
          @click="goBack"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-negative-color hover:bg-red-700"
        >
          취소
        </button>
      </div>
      <div>
        <button
          @click="completeWriteEdit"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
        >
          완료
        </button>
      </div>
      <CompleteModal
        v-if="completeWrite === true"
        :alongButton="alongButton"
        @ok-button="okButton"
      ></CompleteModal>
    </div>
  </div>
</template>
