<script setup>
import SelectAnnouncementType from "~/components/announcement-write/SelectAnnouncementType.vue";
import CreateVoteType from "~/components/announcement-write/CreateVoteType.vue";
import CreateNormalAnnouncement from "~/components/announcement-write/CreateNormalAnnouncement.vue";
import CompleteModal from "~/components/layout/CompleteModal.vue";

definePageMeta({
  layout: "groups",
});

const currentStep = ref("SelectAnnouncementType");
const selectedVote = ref(null);
const completeWrite = ref(false);
const alongButton = ref(true);

const changeStep = (step) => {
  currentStep.value = step;
};

function selectVote(vote) {
  selectedVote.value = vote;
}

function completeWriteAnnounce(complete) {
  completeWrite.value = complete;
}

function okButton(changeComplte) {
  completeWrite.value = changeComplte;
}
</script>

<template>
  <div class="h-screen bg-white">
    <SelectAnnouncementType
      v-if="currentStep === 'SelectAnnouncementType'"
      :selectedVote="selectedVote"
      @select-vote="selectVote"
      @change-step="changeStep"
    ></SelectAnnouncementType>
    <CreateVoteType
      v-else-if="currentStep === 'CreateVoteType'"
      @change-step="changeStep"
      @complete-write="completeWriteAnnounce"
    ></CreateVoteType>
    <CreateNormalAnnouncement
      v-else-if="currentStep === 'CreateNormalAnnouncement'"
      @change-step="changeStep"
      @complete-write="completeWriteAnnounce"
    >
    </CreateNormalAnnouncement>
    <CompleteModal
      :alongButton="alongButton"
      v-if="completeWrite === true"
      @ok-button="okButton"
    ></CompleteModal>
  </div>
</template>
