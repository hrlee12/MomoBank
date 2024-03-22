<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가

const router = useRouter(); // useRouter 인스턴스 생성

// This file path is /products/:id
const { number } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일
const phoneNumber = ref(number);

// 전화번호 마스킹 함수
const maskPhoneNumber = (phoneNumber) => {
  const phoneString = String(phoneNumber);
  return phoneString.replace(/(\d{3})(\d{4})(\d{4})/, "$1****$3");
};

const authNumber = ref();
const expirationTime = ref(5);
const intervalId = ref();
// 타이머를 시작하고, 1초마다 expirationTime을 감소시키는 함수
const startTimer = () => {
  intervalId.value = setInterval(() => {
    if (expirationTime.value > 0) {
      expirationTime.value -= 1;
    } else {
      clearInterval(intervalId.value); // 타이머 종료 조건이 만족되면, 인터벌을 정지시킵니다.
    }
  }, 1000);
};

const formattedTime = computed(() => {
  const minutes = Math.floor(expirationTime.value / 60)
    .toString()
    .padStart(2, "0");
  const seconds = (expirationTime.value % 60).toString().padStart(2, "0");
  return `${minutes}:${seconds}`;
});

// 인증번호 재발송 함수
const resetTime = () => {
  expirationTime.value = 10;
  startTimer();
  // Axios로 인증요청을 다시 보낸다.
};

// 입력값이 숫자인지 확인하는 함수
const validateNumberInput = (event) => {
  const value = event.target.value;
  // 숫자와 맨 앞 0을 허용하는 정규식
  if (!value.match(/^[0-9]*$/)) {
    // 숫자가 아닌 문자가 입력되면 마지막 문자 제거
    authNumber.value = value.slice(0, -1);
  } else {
    // 숫자만 입력된 경우, inputNumber 업데이트
    authNumber.value = value;
  }
};

const handleEnter = () => {
  // Axios로 입력받은 인증번호가 맞는 인증번호인지 확인한다.
  // 틀리면 틀렸다고, 맞으면 맞았다고 표시
  var answer = 1;

  if (authNumber.value == answer) router.push(`/user/signup`);
  // 입력값을 경로의 일부로 사용하여 페이지 이동
  else alert("인증번호가 틀렸습니다.");
};

definePageMeta({
  layout: "user",
});

onMounted(() => {
  phoneNumber.value = maskPhoneNumber(phoneNumber.value);
  startTimer();
});

// 컴포넌트가 언마운트될 때 인터벌을 정지시키기 위한 처리
onUnmounted(() => {
  clearInterval(intervalId.value);
});
</script>

<template>
  <div class="login-container">
    <h1>{{ phoneNumber }}</h1>

    <h3>인증번호가 발송되었습니다.</h3>

    <div class="login-content">
      <input
        type="text"
        placeholder="인증번호입력"
        v-model="authNumber"
        @keyup.enter="handleEnter"
        @input="validateNumberInput"
      />

      <p class="timer" v-if="expirationTime != 0">{{ formattedTime }}</p>
      <p class="timer warning" v-else>시간초과</p>
      <button
        class="login-item second-btn"
        @click="resetTime"
        :disabled="expirationTime > 120"
      >
        재발송
      </button>
    </div>

    <!-- 시간이 초과되어있으면 비활성화 -->
    <button
      class="second-btn"
      @click="handleEnter"
      :disabled="expirationTime == 0"
    >
      인증하기
    </button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-content {
  display: flex;
  input {
    width: 100%;
  }
  .login-item {
    position: fixed;
    right: 5.5%;
    width: 20%;
    border-radius: 10px;
  }
  .timer {
    position: fixed;
    right: 27%;
    margin-top: 7px;
  }
}

.warning {
  color: $negative-color;
  border-color: $negative-color;
}
</style>
