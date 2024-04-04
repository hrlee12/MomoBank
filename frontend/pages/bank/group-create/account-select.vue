<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import { useBankApi } from "~/api/bank";
import { useGroupApi } from "~/api/groups";

const { getMyAccountList } = useBankApi();
const { createNewGroup } = useGroupApi();

const remitStore = useRemitStore();
const groupStore = useGroupStore();

definePageMeta({
  layout: "action",
});

// 슬라이드 데이터
const myAccountList = ref([]);

// 전체 계좌 리스트 받는 함수
onMounted(async () => {
  try {
    const memberId = remitStore.memberId; // 예시 ID
    const response = await getMyAccountList(memberId);
    myAccountList.value = response.data.data.myAccountList;
    console.log("사용자 전체 계좌 리스트: ", myAccountList.value);
  } catch (error) {
    console.error(error);
  }
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const accounts = ref([
  // { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
]);

const isSelected = ref(false);
const selectedId = ref();
const isLoading = ref(false); // 로딩 상태 관리
const method = ref(-1);

const selectAccount = (index) => {
  console.log(index);
  selectedId.value = index;
  isSelected.value = true;
  // console.log(selectedId.value + " " + isSelected.value);
};

const router = useRouter();

// 비동기 데이터 로딩을 시뮬레이션하는 함수
const loadData = async () => {
  isLoading.value = true; // 로딩 시작
  try {
    // 데이터 로딩 로직 (여기서는 setTimeout을 사용하여 시뮬레이션)
    await new Promise((resolve) => setTimeout(resolve, 5000)); // 5초 대기
    // 데이터 로딩 완료
    isLoading.value = false; // 로딩 완료
    router.push("/bank/group-create/card-select"); // 로딩 완료 후 card-select 페이지로 이동
  } catch (error) {
    console.error("데이터 로딩 중 오류 발생:", error);
    isLoading.value = false; // 에러 시 로딩 상태 해제
  }
};

const goCreateBankAccount = () => {
  router.push("/bank/account-create");
};

const requestCreateNewGroup = async () => {
  await createNewGroup(
    {
      memberId: remitStore.memberId,
      description: groupStore.createGroupDesc,
      groupName: groupStore.createGroupName,
      myAccountId: selectedId.value,
      accountId: selectedId.value,
    },
    (data) => {
      alert("모임이 성공적으로 생성되었습니다.");
      console.log(data.data.data.groupId);
      router.push(`/groups/${data.data.data.groupId}`);
    },
    (error) => {
      alert("모임을 생성하는데 실패했습니다.");
      console.log(error);
    }
  );
};

const makeGroup = () => {
  // group 생성 api 호출
  requestCreateNewGroup();
};
</script>

<template>
  <div v-if="method == -1" class="account-container">
    <div class="method-container">
      <div class="method-content" @click="method = 0">
        <img :src="getImageUrl('card-icon.png', 0)" alt="" />
        <h3>기존 계좌 연결</h3>
      </div>
      <div class="method-content" @click="goCreateBankAccount">
        <img :src="getImageUrl('logo-icon.png', 0)" alt="" />
        <h3>신규 계좌 생성</h3>
      </div>
    </div>
  </div>
  <div v-else-if="method == 0" class="account-container">
    <div
      v-for="(account, index) in myAccountList"
      :key="index"
      @click="selectAccount(account.accountId)"
      class="account"
      :class="{ selected: selectedId == account.accountId }"
    >
      <SimpleAccount
        :accountName="account.accountProductName"
        :accountNumber="account.accountNumber"
        :money="account.balance"
        :status="false"
      />
    </div>
    <button v-if="!isSelected" class="second-btn">다음</button>
    <button v-else class="prime-btn" @click="makeGroup">다음</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.method-container {
  padding-top: 5vh;
}

.account-container {
  padding: 3% 0 7% 0;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 20px;
  height: 100%;
  width: 90%;
}
.account {
  border-radius: 20px;
  border-color: none;
}

.prime-btn,
.second-btn {
  width: 100% !important;
  border-radius: 15px !important;
}
</style>
