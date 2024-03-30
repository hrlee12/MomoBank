<script setup>
// TODO: onMounted로 해당 모임 공지사항 리스트 불러오기

const props = defineProps({
  cancelButton: Boolean,
});

const emit = defineEmits(["ok-button", "visible-modal"]);

function completeWrite() {
  emit("ok-button", false);
}

function isVisibleModal() {
  emit("visible-modal", false);
}

const moveLink = ref("");

const route = useRoute();

// TODO : 생각해보니 글을 수정하거나 삭제하고 나서 이동할 떄 해당 id가 필요해서 그걸 이 컴포넌트에 props 해줘야 할 것 같음.

const modalTitle = computed(() => {
  if (route.name === "groups-announcement-write") {
    moveLink.value = "/groups/announcement";
    return "공지사항이 작성되었습니다";
  } else if (
    route.name === "groups-announcement-vote-edit" ||
    route.name === "groups-announcement-normal-edit"
  ) {
    moveLink.value = "/groups/announcement";
    return "공지사항이 수정되었습니다";
  } else if (route.name === "groups-feed-detail") {
    moveLink.value = "/groups/feed/my";
    return "정말로 삭제하시겠습니까?";
  } else if (route.name === "groups-budget-detail") {
    moveLink.value = "/groups/budget";
    return "정말로 삭제하시겠습니까?";
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
      <div v-if="!props.cancelButton">
        <nuxt-link :to="moveLink">
          <button @click="completeWrite" class="px-4 mt-4 text-main-color">
            확인
          </button>
        </nuxt-link>
      </div>
      <div v-if="props.cancelButton" class="flex justify-center w-full">
        <nuxt-link :to="moveLink" class="w-1/2">
          <div @click="completeWrite" class="text-center text-main-color">
            확인
          </div>
        </nuxt-link>

        <div
          @click="isVisibleModal"
          class="w-1/2 text-center text-negative-color"
        >
          취소
        </div>
      </div>
    </div>
  </div>
</template>
