<script setup>
import { ref } from "vue";
import SimpleNotice from "~/components/bank/SimpleNotice.vue";

definePageMeta({
  layout: "bank",
});

const noticeList = ref([
  // status 0은 안읽은 것, 1은 읽은 것
  {
    title: "회비요청",
    content: "알림 내용 1",
    date: "24-02-12",
    status: 0,
  },
  {
    title: "회비요청",
    content: "알림 내용 2",
    date: "24-02-13",
    status: 0,
  },
  {
    title: "회비요청",
    content: "알림 내용 3",
    date: "24-02-14",
    status: 1,
  },
  {
    title: "회비요청",
    content: "알림 내용 4",
    date: "24-02-15",
    status: 1,
  },
]);
</script>

<template>
  <div class="notice-container">
    <div v-for="(notice, index) in noticeList" :key="index">
      <SimpleNotice
        :title="notice.title"
        :date="notice.date"
        :content="notice.content"
        :status="notice.status"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";

.notice-container {
  height: 95vh;
  padding-top: 8vh;
  top: 0;
  left: 0;
  width: 100%;
  position: absolute;
  background-color: white;
}
</style>
