<script setup>
import { useRouter } from "vue-router";
import { useBankApi } from "~/api/bank";

const router = useRouter();
const { confirmAccountPassword } = useBankApi();

definePageMeta({
  layout: "action",
});

const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const name = ref("");

// 비밀번호 숫자를 저장할 배열
const keypadNumbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, "", 0, ""];

// 입력 패스워드
const inputPasswordArray = ref(["", "", "", ""]);

// 비밀번호 체크
const checkPassword = ref(["", "", "", ""]);
const checkPassBoolean = ref(false);
const failPass = ref(false);

// 입력한 패스워드와 체크 비밀번호가 같은지 체크
const passValidation = ref(false);

onMounted(() => {
  checkPassBoolean.value = false;
});

// 배열 구조 분해를 통해 반응형 데이터를 만듭니다.

const enterNumber = (number) => {
  // 첫 번째 빈 입력을 찾습니다.
  const nextIndex = inputPasswordArray.value.findIndex((value) => value === "");

  // 빈 입력이 있으면 숫자를 채웁니다.
  if (nextIndex !== -1) {
    inputPasswordArray.value[nextIndex] = number.toString();
  }

  if (inputPasswordArray.value[3] !== "") {
    console.log(inputPasswordArray.value[3]);
    checkPassBoolean.value = true;
  }
};

const checkEnterNumber = (number) => {
  failPass.value = false;
  // 첫 번째 빈 입력을 찾습니다.
  const nextIndex = checkPassword.value.findIndex((value) => value === "");

  // 빈 입력이 있으면 숫자를 채웁니다.
  if (nextIndex !== -1) {
    checkPassword.value[nextIndex] = number.toString();
  }
  if (checkPassword.value[3] !== "") {
    if (
      inputPasswordArray.value[0] === checkPassword.value[0] &&
      inputPasswordArray.value[1] === checkPassword.value[1] &&
      inputPasswordArray.value[2] === checkPassword.value[2] &&
      inputPasswordArray.value[3] === checkPassword.value[3]
    ) {
      // 비밀번호 입력이 끝났으면 비밀번호 String화 및 api 호출
      changePasswordArrayToString();
      requestPasswordConfirm();
    } else {
      failPass.value = true;
      checkPassword.value = ["", "", "", ""];
    }
  }
};

const backspace = (menu) => {
  if (menu == 0) {
    // 입력된 마지막 인덱스를 찾습니다.
    const lastIndex = inputPasswordArray.value.lastIndexOf(
      inputPasswordArray.value
        .slice()
        .reverse()
        .find((value) => value !== "")
    );

    // 입력된 마지막 인덱스의 값을 지웁니다.
    if (lastIndex !== -1) {
      inputPasswordArray.value[lastIndex] = "";
    }
  } else if (menu == 1) {
    // 입력된 마지막 인덱스를 찾습니다.
    const lastIndex = checkPassword.value.lastIndexOf(
      checkPassword.value
        .slice()
        .reverse()
        .find((value) => value !== "")
    );

    // 입력된 마지막 인덱스의 값을 지웁니다.
    if (lastIndex !== -1) {
      checkPassword.value[lastIndex] = "";
    }
  }
};

// 입력된 숫자 배열을 하나의 숫자로 바꾼다.
const stringPassword = ref("");
const changePasswordArrayToString = () => {
  console.log("비밀번호 배열: ", checkPassword.value);
  for (const digit in checkPassword.value) {
    stringPassword.value += checkPassword.value[digit];
    console.log("직렬화된 비밀번호: ", stringPassword.value);
  }
};

import { useRemitStore } from "~/stores/remitStore";
const remitStore = useRemitStore();
name.value = remitStore.memberName;
const requestPasswordConfirm = async () => {
  // 비밀번호가 맞는지 확인
  await confirmAccountPassword(
    {
      accountId: remitStore.remitInfo.myAccountId,
      password: stringPassword.value,
    },
    (data) => {
      console.log("비밀번호가 조회되었습니다: ", data.data.data.isApproved);
      if (data.data.data.isApproved == true)
        router.push("/bank/remit/confirm-remit");
      else if (data.data.data.isApproved == false) {
        alert("올바른 계좌 비밀번호가 아닙니다.");
        checkPassBoolean.value = false;
        inputPasswordArray.value = "";
        checkPassword.value = "";
      }
    },
    (error) => {
      console.log("조회할 수 없습니다.: ", error);
    }
  );
};
</script>

<template>
  <div
    v-if="!checkPassBoolean && !passValidation"
    class="flex justify-center pt-20"
  >
    <div class="flex flex-col">
      <p class="text-xl font-bold text-center">{{ name }}님 계좌에 사용할</p>
      <p class="mb-4 text-xl font-bold text-center">비밀번호를 입력해주세요</p>
      <div class="flex justify-between mb-[20%]">
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="inputPasswordArray[0]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="inputPasswordArray[1]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="inputPasswordArray[2]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="inputPasswordArray[3]"
          readonly
        />
      </div>
      <div class="grid grid-cols-3 gap-[2rem]">
        <button
          v-for="(number, index) in keypadNumbers"
          :key="number"
          @click="enterNumber(number)"
          class="flex items-center justify-center w-12 h-12 text-3xl font-bold text-gray-color"
        >
          <div v-if="index != keypadNumbers.length - 1">{{ number }}</div>
          <div v-else>
            <div class="w-8 h-8" @click="backspace(0)">
              <img
                :src="getImageUrl('back-number-icon.png', 0)"
                alt="back-number"
              />
            </div>
          </div>
        </button>
      </div>
    </div>
  </div>
  <div v-if="checkPassBoolean" class="flex justify-center pt-20">
    <div class="flex flex-col">
      <p class="text-xl font-bold text-center">입력한 비밀번호를</p>
      <p class="mb-4 text-xl font-bold text-center">확인해주세요</p>
      <div
        v-if="failPass"
        class="pb-2 font-bold text-center text-negative-color"
      >
        비밀번호가 일치하지 않습니다!
      </div>
      <div class="flex justify-between mb-[20%]">
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="checkPassword[0]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="checkPassword[1]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="checkPassword[2]"
          readonly
        />
        <input
          class="w-12 h-12 text-5xl text-center border-2 border-gray-300 rounded"
          type="password"
          v-model="checkPassword[3]"
          readonly
        />
      </div>
      <div class="grid grid-cols-3 gap-[2rem]">
        <button
          v-for="(number, index) in keypadNumbers"
          :key="number"
          @click="checkEnterNumber(number)"
          class="flex items-center justify-center w-12 h-12 text-3xl font-bold text-gray-color"
        >
          <div v-if="index != keypadNumbers.length - 1">{{ number }}</div>
          <div v-else>
            <div class="w-8 h-8" @click="backspace(1)">
              <img
                :src="getImageUrl('back-number-icon.png', 0)"
                alt="back-number"
              />
            </div>
          </div>
        </button>
      </div>
    </div>
  </div>
</template>

<style></style>
