<script setup>
import GroupsBottomSheetModal from "~/components/layout/GroupsBottomSheetModal.vue";
import CompleteModal from "~/components/layout/CompleteModal.vue";

import { useGroupApi } from "~/api/groups";

const { patchGroupBudget, deleteGroupBudget } = useGroupApi();

import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();

const remitStore = useRemitStore();

const { budgetId } = useRoute().params;

const budget = ref({
  memberId: 2,
  monthlyDueDate: 11,
  name: "우오우오우우!!",
  finalFee: 32000000,
  finalDueDate: "2024-04-25",
});

const memberId = remitStore.memberId;

const groupId = groupStore.groupId;

const goBack = () => {
  window.history.back();
};

const patchBudget = async () => {
  console.log(budget.value);
  try {
    const response = await patchGroupBudget(groupId, budgetId, budget.value);
    if (response.status === 200) {
      goBack();
    }
  } catch (error) {
    console.error(error); // 오류 처리
  }
};

definePageMeta({
  layout: "no-footer-bank",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const budgetGoal = ref("우가우가우가");

// 삭제 확인,취소를 위한 변수
const cancelButton = true;
const visibleModal = ref(false);

const isVisibleModal = (value) => {
  visibleModal.value = value;
};

// 이체주기 선택
const visibleFrequency = ref(false);

const visibleBottomModal = ref(false);

const editMode = ref(false);

const visibleBottomModalClick = () => {
  visibleBottomModal.value = true;
  visibleFrequency.value = true;
};

const handleUpdate = (event) => {
  visibleBottomModal.value = event.isVisible;
  visibleFrequency.value = event.budgetAddVisible;
  if (event.frequencyDay.value !== null) {
    budget.value.monthlyDueDate = event.frequencyDay.value;
  }
};

const editModeOn = () => {
  editMode.value = true;
};

const editModeOff = () => {
  editMode.value = false;
};

const deleteModal = async () => {
  visibleModal.value = true;
  try {
    const response = await deleteGroupBudget(groupId, budgetId);
    if (response.status === 200) {
      goBack();
    }
  } catch (error) {
    console.error(error); // 오류 처리
  }
};
</script>

<template>
  <div class="h-screen bg-white">
    <div class="px-4 py-2">
      <div v-if="!editMode">
        <div class="py-3">
          <div class="py-3 text-xl font-bold">예산 이름</div>
          <div class="border-b-2 border-main-color">
            <input
              type="text"
              disabled
              v-model="budget.name"
              placeholder="예산 이름 입력"
              class="w-full text-lg"
            />
          </div>
        </div>
        <div class="py-3">
          <div class="py-3 text-xl font-bold">예산 목적</div>
          <div class="border-b-2 border-main-color">
            <input
              type="text"
              disabled
              v-model="budgetGoal"
              placeholder="예산 목적 입력"
              class="w-full text-lg"
            />
          </div>
        </div>
        <div class="py-3">
          <div class="py-3 text-xl font-bold">목표 금액</div>
          <div class="border-b-2 border-main-color">
            <input
              type="text"
              disabled
              v-model="budget.finalFee"
              placeholder="목표 금액 입력"
              class="w-full text-lg"
            />
          </div>
        </div>
        <div class="py-3 text-xl font-bold">목표 날짜</div>
        <div>{{ budget.finalDueDate }}</div>
        <div class="py-3 text-xl font-bold">납입 날짜</div>
        <div class="flex items-center">
          <div>매월 {{ budget.monthlyDueDate }}일</div>
        </div>
        <div class="flex justify-end px-4 mt-4 space-x-4">
          <button
            @click="editModeOn()"
            class="px-4 py-2 font-bold text-white rounded-xl bg-main-color hover:bg-blue-700"
          >
            수정
          </button>
          <button
            @click="deleteModal"
            class="px-4 py-2 font-bold text-white rounded-xl bg-negative-color hover:bg-red-700"
          >
            삭제
          </button>
        </div>
      </div>

      <!-- editMode On -->
      <div v-if="editMode">
        <div class="py-3">
          <div class="py-3 text-xl font-bold">예산 목적</div>
          <div class="border-b-2 border-main-color">
            <input
              type="text"
              v-model="budget.name"
              placeholder="예산 목적 입력"
              class="w-full text-lg"
            />
          </div>
        </div>
        <div class="py-3">
          <div class="py-3 text-xl font-bold">목표 금액</div>
          <div class="border-b-2 border-main-color">
            <input
              type="text"
              v-model="budget.finalFee"
              placeholder="목표 금액 입력"
              class="w-full text-lg"
            />
          </div>
        </div>
        <div class="py-3 text-xl font-bold">목표 날짜</div>
        <div>
          <input
            type="date"
            class="border-none"
            id="goalInput"
            name="goalInput"
            v-model="budget.finalDueDate"
          />
        </div>
        <div class="py-3 text-xl font-bold">납입 날짜</div>
        <div class="flex items-center">
          <div @click="visibleBottomModalClick">
            매월 {{ budget.monthlyDueDate }}일
          </div>
          <div class="w-5 h-5 rotate-180">
            <img :src="getImageUrl('arrow-icon.png', 0)" alt="arrow-icon" />
          </div>
        </div>

        <GroupsBottomSheetModal
          :isFrequency="visibleFrequency"
          :isVisible="visibleBottomModal"
          @budget-add-update="handleUpdate"
        ></GroupsBottomSheetModal>
        <div v-if="visibleBottomModal" class="modal-bg"></div>
        <div class="flex justify-end px-4 mt-4 space-x-4">
          <button
            @click="editModeOff()"
            class="px-4 py-2 font-bold text-white rounded-xl bg-main-color hover:bg-blue-700"
          >
            취소
          </button>
          <button
            @click="patchBudget"
            class="px-4 py-2 font-bold text-white rounded-xl bg-negative-color hover:bg-red-700"
          >
            완료
          </button>
        </div>
      </div>
    </div>
  </div>
  <CompleteModal
    v-if="visibleModal"
    :cancelButton="cancelButton"
    @visible-modal="isVisibleModal"
  ></CompleteModal>
</template>
<style lang="scss" scoped>
.modal-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 999;
}
</style>
