<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // useRouter 추가
import { useUserApi } from "@/api/user";
import { useGroupApi } from "@/api/groups";
import { useRemitStore } from "~/stores/remitStore";
import { useBankApi } from "@/api/bank";

const { getMyAccountList } = useBankApi();
const { loginRequest } = useUserApi();
const { createInviteAuthCode, groupJoinWithInviteCode } = useGroupApi();

const router = useRouter(); // useRouter 인스턴스 생성
const remitStore = useRemitStore();
const groupStore = useGroupStore();

definePageMeta({
  layout: "user",
});

const loginInfo = ref({
  userId: "",
  userPassword: "",
});

// 로그인 요청시 재확인

const joinWithInviteCode = async () => {
  await groupJoinWithInviteCode(
    {
      groupId: groupStore.groupId,
      memberId: remitStore.memberLoginId,
      accountId: 66,
      authToken: groupStore.inviteAuthCode,
    },
    (data) => {
      console.log("모임 가입 완료", data);
      router.push(`/groups/${groupStore.groupId}`);
    },
    (error) => {
      console.log("모임 가입 실패", error);
    }
  );
};

const createAuthCode = async () => {
  try {
    const response = await createInviteAuthCode(
      groupStore.inviteCode,
      remitStore.memberLoginId
    );
    if (response.status === 200) {
      console.log(response.data.data.authToken);
      groupStore.inviteAuthCode = response.data.data.authToken;

      // 모임 가입
      joinWithInviteCode();
    }
  } catch (error) {
    console.error(error); // 오류 처리
  }
};

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
      // console.log(data);
      // 모든 검증을 통과했을 때
      remitStore.memberId = data.data.data.memberId;
      remitStore.memberName = data.data.data.name;
      remitStore.memberLoginId = data.data.data.id;
      console.log(remitStore.memberLoginId);
      console.log(groupStore.inviteCode);

      // 만약 초대코드를 받고 로그인 창으로 이동한 경우에는 로그인 했을 떄 바로 로그인 홈으로 이동한다.
      //TODO : 해당하는 모임에 가입하고, router로 이동시키면 될 것 같음.
      if (groupStore.inviteStatus) {
        // auth코드 생성
        createAuthCode(groupStore.inviteCode, remitStore.memberLoginId);
      }
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
