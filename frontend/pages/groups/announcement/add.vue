<script setup>
import { useGroupStore } from "@/stores/group";

import { useGroupApi } from "~/api/groups";

const { postGroupNotice } = useGroupApi();
const groupStore = useGroupStore();

const groupStoreMemberId = computed(() => groupStore.groupMemberId);

const notice = ref({
  groupMemberId: groupStoreMemberId.value,
  title: "",
  content: "",
});
const goBack = () => {
  window.history.back();
};
const postNotice = async () => {
  console.log(groupStoreMemberId.value);
  try {
    const response = await postGroupNotice(notice.value);
    if (response.status === 200) {
      goBack();
    }
  } catch (error) {
    console.error(error); // 오류 처리
  }
};

definePageMeta({
  layout: "groups",
});
</script>

<template>
  <div class="h-[90vh] bg-white">
    <div class="px-6 py-6">
      <div class="mb-5 border-b-[1px] border-gray-color h-8">
        <input
          class="w-full"
          v-model="notice.title"
          type="text"
          placeholder="제목 입력"
        />
      </div>
      <div class="mb-5 border-2 rounded-xl h-72">
        <textarea
          class="w-full h-full overflow-y-auto whitespace-normal"
          v-model="notice.content"
        ></textarea>
      </div>

      <div class="flex justify-between">
        <div>
          <button
            @click="goBack()"
            class="px-4 py-2 font-bold text-white w- rounded-xl bg-negative-color hover:bg-red-700"
          >
            취소
          </button>
        </div>
        <div>
          <button
            @click="postNotice"
            class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
          >
            완료
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
