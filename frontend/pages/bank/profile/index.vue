<script setup>
import { ref } from "vue";
import BankFooter from "~/components/layout/BankFooter.vue";
import { useRouter } from "vue-router"; // useRouter 추가
import { useUserApi } from "~/api/user";

const router = useRouter(); // useRouter 인스턴스 생성
const { getMyPageData } = useUserApi();

definePageMeta({
  layout: "user",
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// 유저 정보
const userInfo = ref({
  id: "",
  name: "",
  birthDate: "",
  phoneNumber: "",
});

// 휴대전화번호에 '-' 추가
const onPhone = () => {
  userInfo.value.phoneNumber = userInfo.value.phoneNumber
    .replace(/[^0-9]/g, "")
    .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
};

// 수정 구분 처리
const goEdit = (param) => {
  var pageName = param == 0 ? "phone-edit" : "password-edit";
  router.push(`/bank/profile/${pageName}`);
};

// 유저 스토어 불러오기
import { useRemitStore } from "~/stores/remitStore";
const remitStore = useRemitStore();

// 유저 정보 불러오는 api 요청
const getUserInfo = async () => {
  try {
    const response = await getMyPageData(remitStore.memberLoginId);
    userInfo.value = response.data.data;
    onPhone();
    console.log("유저데이터 불러오기 성공: ", userInfo.value);
  } catch (error) {
    console.log("유저데이터 불러오기 실패: ", error);
  }
};

onMounted(async () => {
  await getUserInfo();
});
</script>

<template>
  <!-- 메인 모임 리스트 -->
  <div class="container content">
    <div class="club-content">
      <div class="item">
        <p>아이디</p>
        <h3 v-if="userInfo != undefined">{{ userInfo.id }}</h3>
      </div>
    </div>
    <div class="club-content">
      <div class="item">
        <p>이름</p>
        <h3 v-if="userInfo != undefined">{{ userInfo.name }}</h3>
      </div>
    </div>
    <div class="club-content">
      <div class="item">
        <p>생년월일</p>
        <h3 v-if="userInfo != undefined">{{ userInfo.birthDate }}</h3>
      </div>
    </div>
    <div class="club-content">
      <div class="item">
        <p>전화번호</p>
        <h3 v-if="userInfo != undefined">{{ userInfo.phoneNumber }}</h3>
        <img
          :src="getImageUrl('arrow-icon2.png', 0)"
          alt="phone number edit"
          @click="goEdit(0)"
        />
      </div>
    </div>
    <div class="club-content">
      <div class="item">
        <p>비밀번호</p>
        <img
          :src="getImageUrl('arrow-icon2.png', 0)"
          alt="password edit"
          @click="goEdit(1)"
        />
      </div>
    </div>
  </div>
  <BankFooter />
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";
@import "@/assets/css/content.scss";

img {
  height: 2vh;
  align-self: center;
  position: absolute;
  right: 2%;
}

.container {
  width: 85%;
  padding: 0 1vh;

  .center {
    align-self: center;
    color: $gray-color;
  }

  .item {
    display: flex;
    justify-content: space-between;
    padding: 1.5vh;
    position: relative;

    p {
      width: 35%;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      align-self: center;
    }

    h3 {
      width: 65%;
    }

    .icon-item {
      display: flex;
      justify-content: space-around;
      min-width: 45px;
    }
  }

  .club-content + .club-content {
    border-top: solid 1px $light-gray-color;
  }
}
</style>
