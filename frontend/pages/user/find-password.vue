<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가

const router = useRouter(); // useRouter 인스턴스 생성

definePageMeta({
  layout: "user",
});

const userInfo = ref({
  userName: "",
  phoneNumber: "",
});

// 비밀번호 찾기 요청시 재확인
const findPasswordReqeust = () => {
  // 이름 입력란 확인
  if (!userInfo.value.userName) {
    alert("이름을 입력해주세요.");
    return;
  }

  // 전화번호 입력란 확인
  if (!userInfo.value.phoneNumber) {
    alert("전화번호를 입력해주세요.");
    return;
  }

  // axios로 사용자 유무 확인
  // 실패시 alert("입력된 정보에 해당하는 유저를 찾지 못하였습니다.");

  // 모든 검증을 통과했을 때
  console.log(userInfo.value);
  alert("해당휴대폰 번호로 임시 번호를 전송하였습니다.");
  router.push(`/user`);
};
</script>

<template>
  <div class="login-container">
    <div class="login-content">
      <input
        class="login-item"
        type="text"
        placeholder="이름"
        v-model="userInfo.userName"
      />
      <input
        class="login-item"
        type="number"
        placeholder="전화번호"
        v-model="userInfo.phoneNumber"
      />
      <button class="second-btn login-item" @click="findPasswordReqeust">
        비밀번호 찾기
      </button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-container {
  justify-content: flex-start;
}
</style>
