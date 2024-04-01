<script setup>
const props = defineProps({
  selectedVote: String,
});

const goBack = () => {
  window.history.back();
};

const emit = defineEmits(["change-step", "select-vote"]);

function selectVote(vote) {
  emit("select-vote", vote);
}

function goToNextStep() {
  if (props.selectedVote === "vote") {
    emit("change-step", "CreateVoteType");
  } else if (props.selectedVote === "normal") {
    emit("change-step", "CreateNormalAnnouncement");
  }
}
</script>
<template>
  <div class="px-6 py-6 text-xl font-bold">어떤 공지사항을 작성할까요?</div>

  <div v-if="props.selectedVote !== null">
    <select
      v-model="props.selectedVote"
      @change="selectVote($event.target.value)"
      class="w-7/12 max-w-full px-6 py-6"
      name="announcementType"
      id="announcementType"
    >
      <option class="w-10/12" value="normal">일반</option>
      <option class="w-10/12" value="vote">투표</option>
    </select>
  </div>

  <div v-if="props.selectedVote === null">
    <select
      @change="selectVote($event.target.value)"
      class="w-7/12 max-w-full px-6 py-6"
      name="announcementType"
      id="announcementType"
    >
      <option disabled value="" :selected="props.selectedVote === null" hidden>
        공지사항 종류
      </option>
      <option class="w-10/12" value="normal">일반</option>
      <option class="w-10/12" value="vote">투표</option>
    </select>
  </div>

  <div class="flex justify-between px-6 py-6">
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
        @click="goToNextStep"
        class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
      >
        다음
      </button>
    </div>
  </div>
</template>
