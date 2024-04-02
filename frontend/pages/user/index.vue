<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가
import { useUserApi } from "@/api/user";

const { loginRequest } = useUserApi();

const router = useRouter(); // useRouter 인스턴스 생성

definePageMeta({
  layout: "user",
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const loginInfo = ref({
  userId: "",
  userPassword: "",
});

// 로그인 요청시 재확인

const login = async () => {
  // 아이디 입력란 확인
  if (!loginInfo.value.userId) {
    alert("아이디를 입력해주세요.");
    return;
  }

  // 비밀번호 입력란 확인
  if (!loginInfo.value.userPassword) {
    alert("비밀번호를 입력해주세요.");
    return;
  }

  // axios로 사용자 유무 확인
  // 실패시 alert("입력된 정보에 해당하는 유저를 찾지 못하였습니다.");
  await loginRequest(
    //
    {
      id: loginInfo.value.userId,
      password: loginInfo.value.userPassword,
    },
    (data) => {
      console.log(data);
      // 모든 검증을 통과했을 때
      console.log(loginInfo.value);
      router.push(`/bank`);
    },
    // 로그인 실패시
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="login-container">
    <div class="login-content">
      <input
        class="login-item"
        type="text"
        placeholder="아이디"
        v-model="loginInfo.userId"
      />
      <input
        class="login-item"
        type="password"
        placeholder="비밀번호"
        v-model="loginInfo.userPassword"
      />
      <button class="prime-btn login-item" @click="login">로그인</button>
      <NuxtLink to="/user/find-password"
        ><p class="font-small">비밀번호를 잊으셨나요?</p></NuxtLink
      >
    </div>
    <div class="login-content center">
      <NuxtLink to="/user/authenicate">
        <button class="second-btn login-item">새 계정 만들기</button></NuxtLink
      >
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-container {
  height: 40vh;
  justify-content: space-between;
}

.login-content {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  text-align: center;

  p {
    font-size: 1.8vh;
    margin-top: 5%;
  }
}

.login-item {
  margin: 0.5vh 0;
  padding: 0 2vw;
}

.icon-item {
  justify-content: center;

  .login-item + .login-item {
    border-left: solid 1px $light-gray-color;
  }
}

.center {
  justify-content: center;
}
</style>
