<script setup>
definePageMeta({
  layout: "user",
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="login-container">
    <div class="login-content">
      <input class="login-item" type="text" placeholder="아이디" />
      <input class="login-item" type="text" placeholder="비밀번호" />
      <button class="prime-btn login-item">로그인</button>
      <NuxtLink to="/user/find-password"
        ><p class="font-small">비밀번호를 잊으셨나요?</p></NuxtLink
      >
    </div>
    <div class="login-content center">
      <NuxtLink to="/user/authenicate">
        <button class="second-btn login-item">새 계정 만들기</button></NuxtLink
      >
      <div class="icon-item">
        <p class="login-item">간편 로그인</p>
        <img
          class="login-item"
          :src="getImageUrl('kakako-icon.png', 0)"
          alt=""
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-container {
  width: 90%;
  height: 40vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.login-content {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  text-align: center;

  p {
    font-size: 1.8vh;
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
