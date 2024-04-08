<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가
import { useUserApi } from "~/api/user"; // api 추가

const router = useRouter(); // useRouter 인스턴스 생성
const { userJoinRequest } = useUserApi();
const userStore = useUserStore();

const signUpInfo = ref({
  userName: "",
  userId: "",
  password: "",
  passwordConfirm: "",
  userBirth: "",
});

const isPasswordValid = ref(true);
const checkPasswordMatch = () => {
  isPasswordValid.value =
    signUpInfo.value.password === signUpInfo.value.passwordConfirm
      ? true
      : false;
};

// 회원가입 요청시 재확인
const signUpRequest = () => {
  // 이름 입력란 확인
  if (!signUpInfo.value.userName) {
    alert("이름을 입력해주세요.");
    return;
  }

  // 아이디 입력란 확인
  if (!signUpInfo.value.userId) {
    alert("아이디를 입력해주세요.");
    return;
  }

  // 비밀번호 확인
  if (!signUpInfo.value.password) {
    alert("비밀번호를 입력해주세요.");
    return;
  }

  // 비밀번호와 비밀번호 확인이 일치하는지 확인
  checkPasswordMatch();
  if (!isPasswordValid.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  // 생일 입력란 확인
  if (!signUpInfo.value.userBirth) {
    alert("생년월일을 입력해주세요.");
    return;
  }

  // 모든 검증을 통과했을 때
  console.log(signUpInfo.value);
  // 회원가입 axios
  userJoinRequest(
    {
      name: signUpInfo.value.userName,
      id: signUpInfo.value.userId,
      password: signUpInfo.value.password,
      birthdate: signUpInfo.value.userBirth,
      phoneNumber: userStore.userPhoneNumber,
      authToken: userStore.messageVerifyAuthToken,
    },
    (data) => {
      alert("모모뱅크에 오신것을 환영합니다.");
      router.push(`/user`);
    },
    (error) => {
      alert("회원가입 실패!");
    }
  );
};

definePageMeta({
  layout: "user",
});
</script>

<template>
  <div class="login-container">
    <input type="text" placeholder="이름" v-model="signUpInfo.userName" />
    <input
      type="text"
      placeholder="아이디 (영어, 숫자 포함 6글자 이상)"
      v-model="signUpInfo.userId"
    />
    <input
      type="password"
      placeholder="비밀번호 (6자 ~ 18자, 영문 숫자 혼합)"
      v-model="signUpInfo.password"
    />
    <div>
      <input
        type="password"
        placeholder="비밀번호 확인"
        v-model="signUpInfo.passwordConfirm"
        @blur="checkPasswordMatch"
        :class="{ 'warning-border ': !isPasswordValid }"
      />
      <p class="warning" v-if="!isPasswordValid">
        비밀번호가 일치하지 않습니다.
      </p>
    </div>

    <input
      type="date"
      placeholder="생년월일 ex) 20000214"
      v-model="signUpInfo.userBirth"
    />
    <button
      class="second-btn"
      @click="signUpRequest"
      @keyup.enter="signupRequest"
    >
      새 계정 만들기
    </button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-container {
  height: 45vh;
}

.warning-border {
  border-color: $negative-color;

  &:focus {
    outline: 1px solid $negative-color;
  }
}
</style>
