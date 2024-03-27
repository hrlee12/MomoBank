<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가

const router = useRouter(); // useRouter 인스턴스 생성

definePageMeta({
  layout: "user",
});

const newPhoneNumber = ref("");

const editPhoneNumber = () => {
  // axios로 전화번호 변경 요청
  // 완료시 페이지 리라우팅
  console.log(newPhoneNumber.value);
  router.push(`/bank/profile`);
};
</script>

<template>
  <div class="login-container">
    <input
      type="text"
      placeholder="전화번호 ex) 01012341234"
      v-model="newPhoneNumber"
      @keyup.enter="editPhoneNumber"
    />

    <button class="second-btn" @click="editPhoneNumber">전화번호 수정</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";
</style>
