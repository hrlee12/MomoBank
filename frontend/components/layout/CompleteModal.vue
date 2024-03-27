<script setup>
const emit = defineEmits(["ok-button"]);

function completeWrite() {
  emit("ok-button", false);
}

const moveLink = ref("");

const route = useRoute();

const modalTitle = computed(() => {
  if (route.name === "groups-announcement-write") {
    moveLink.value = "/groups/announcement";
    return "공지사항이 작성되었습니다";
  }
});
</script>

<template>
  <div
    class="fixed inset-0 flex items-center justify-center h-full bg-black bg-opacity-30"
  >
    <div
      class="flex flex-col items-center justify-center w-10/12 p-6 bg-white shadow-xl rounded-xl"
    >
      <div
        class="w-full pt-4 mb-3 text-base font-bold text-center border-b h-14 border-light-gray-color"
      >
        {{ modalTitle }}
      </div>
      <nuxt-link :to="moveLink">
        <button
          @click="completeWrite"
          class="px-4 py-2 mt-4 rounded text-main-color"
        >
          확인
        </button>
      </nuxt-link>
    </div>
  </div>
</template>
