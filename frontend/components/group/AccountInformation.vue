<script setup>
import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();

const accountNumber = computed(() => groupStore.accountNumber);

const copyTextToClipboard = async (text) => {
  try {
    await navigator.clipboard.writeText(text);
    alert(`클립보드에 복사되었습니다! text-${text}`);
  } catch (err) {
    console.error("클립보드 복사 실패:", err);
  }
};
</script>

<template>
  <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
  <div class="flex justify-center mt-4">
    <div
      class="text-gray-color border-b-2 border-b-gray-color-500 text-[15px]"
      @click="copyTextToClipboard(accountNumber)"
    >
      {{ accountNumber }}
    </div>
  </div>
  <div class="flex justify-center">
    <div v-if="groupStore.groupBalance !== null" class="font-bold text-[28px]">
      {{ groupStore.groupBalance.toLocaleString("ko-KR") }}
    </div>
    <div v-else class="font-bold text-[28px]">0</div>
    <div class="text-[15px] pt-4 pl-1 pr-2">원</div>
    <div
      class="flex items-center justify-center w-8 mt-3 bg-light-gray-color rounded-xl h-7"
    >
      <div class="text-gray-color text-[13px]">숨김</div>
    </div>
  </div>
</template>
