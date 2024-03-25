<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가

const router = useRouter(); // useRouter 인스턴스 생성

definePageMeta({
  layout: "user",
});

const newPassword = ref("");
const newPasswordConfirm = ref("");

const editPassword = () => {
  // 비밀번호가 일치하는지 확인
  if (newPassword.value !== newPasswordConfirm.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }
  // axios로 비밀번호ㅗ 변경 요청
  // 완료시 페이지 리라우팅
  console.log(newPassword.value);
  router.push(`/bank/profile`);
};
</script>

<template>
  <div class="login-container">
    <input
      type="password"
      placeholder="비밀번호 (6자 ~ 18자, 영문 숫자 혼합)"
      v-model="newPassword"
      @keyup.enter="editPassword"
    />
    <input
      type="password"
      placeholder="비밀번호 재입력"
      v-model="newPasswordConfirm"
      @keyup.enter="editPassword"
    />

    <button class="second-btn" @click="editPassword">전화번호 수정</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";
</style>
