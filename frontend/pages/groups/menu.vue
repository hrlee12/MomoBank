<script setup>
import CompleteModal from "~/components/layout/CompleteModal.vue";

import { useGroupApi } from "~/api/groups";

const { postSplitBalance } = useGroupApi();

const groupStore = useGroupStore();
const remitStore = useRemitStore();

definePageMeta({
  layout: "no-footer-bank",
});

//모임 탈퇴하기
const leaveGroup = ref(false);

// 돈 분배
const distributeMoney = ref(false);

// 모임 해제하기
const dismissingGroup = ref(false);

const visibleModal = ref(false);

const isVisibleModal = (value) => {
  visibleModal.value = value;
  leaveGroup.value = false;
  distributeMoney.value = false;
  dismissingGroup.value = false;
};

const leaveGroupTrue = () => {
  leaveGroup.value = true;
  visibleModal.value = true;
};

const distributeMoneyTrue = () => {
  distributeMoney.value = true;
  visibleModal.value = true;
}

const dismissingGroupTrue = () => {
  dismissingGroup.value = true;
  visibleModal.value = true;
}
</script>

<template>
  <div class="h-screen bg-white">
    <div class="px-8 pt-6">
      <div class="py-5 text-xl font-bold">모임상세</div>
      <div class="py-5 text-xl font-bold">공지사항</div>
      <div class="py-5 text-xl font-bold">거래내역</div>
      <div class="py-5 text-xl font-bold">예산</div>
      <div class="py-5 text-xl font-bold">리포트</div>
      <div
        @click="leaveGroupTrue"
        class="py-5 text-xl font-bold border-b-2 border-light-gray-color"
      >
        모임 탈퇴하기
      </div>
      <div class="py-5 text-xl font-bold" @click="distributeMoneyTrue">돈 분배하기</div>
      <div class="py-5 text-xl font-bold" @click="dismissingGroupTrue">모임 해제하기</div>
    </div>
  </div>
  <CompleteModal
    v-if="visibleModal"
    :leaveGroup="leaveGroup"
    :distributeMoney="distributeMoney"
    :dismissingGroup="dismissingGroup"
    @visible-modal="isVisibleModal"
  ></CompleteModal>
</template>
