<script setup>
import AccountInformation from "~/components/group/AccountInformation.vue";
import AccountHistory from "~/components/bank/AccountHistory.vue";

import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();
const remitStore = useRemitStore();

import { useGroupApi } from "~/api/groups";

const { getTransactionHistory } = useGroupApi();

const memberId = remitStore.memberId;

const fetchTransactionHistory = async () => {
  try {
    const response = await getTransactionHistory(
      memberId,
      groupStore.accountId
    );
    return response.data;
  } catch (error) {
    console.error("거래내역 데이터를 불러오는 데 실패했습니다.", error);
  }
};

const accountList = ref([]);

onMounted(() => {
  fetchTransactionHistory(groupStore.groupId, groupStore.accountId).then(
    (response) => {
      accountList.value = response.data.totalTransferList;
      console.log(accountList.value);
    }
  );
});

definePageMeta({
  layout: "groups",
});

// TODO : 일단 API 안나와서 패스
//
</script>

<template>
  <div class="bg-white h-44 rounded-b-[14px]">
    <div>
      <!-- 상세, 납부 완료, 접기/펴기 아이콘 -->
      <div class="flex flex-row justify-between">
        <NuxtLink to="/groups/detail">
          <div class="flex items-center justify-center w-10 h-6 ml-4">
            <div></div>
          </div>
        </NuxtLink>

        <div class="items-center">
          <p
            v-if="groupStore.paymentStatus"
            class="text-positive-color text-[13px]"
          >
            납부 완료
          </p>
          <p
            v-if="!groupStore.paymentStatus"
            class="text-negative-color text-[13px]"
          >
            납부 요망
          </p>
        </div>
        <div class="w-8 h-6 mr-4"></div>
      </div>

      <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
      <AccountInformation></AccountInformation>
      <!-- 실선, 거래 내역, 예산 -->
      <div class="flex justify-center">
        <div class="mt-4 border-t-[1px] border-t-light-gray-color w-80"></div>
      </div>
      <div class="flex justify-center">
        <div class="flex mt-3 w-80">
          <nuxt-link to="/groups/transaction-history">
            <div
              class="font-semibold text-[17px] w-40 text-center border-r-[1px] text-main-color"
            >
              거래내역
            </div>
          </nuxt-link>

          <nuxt-link to="/groups/budget">
            <div
              class="font-semibold text-[17px] w-40 text-center text-main-color"
            >
              예산
            </div>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>

  <div class="px-4">
    <div
      v-if="accountList.length == 0 || accountList == undefined"
      class="mt-[2%] mb-[2%] bg-white rounded-[15px] pt-[3vh] pr-[5vw] pb-[10vh] pl-[5vh] min-h-[60vh] text-center"
    >
      조회된 기록이 없습니다.
    </div>
    <div v-else class="history-container">
      <AccountHistory
        v-for="(history, index) in accountList"
        :key="index"
        :history="history"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";
@import "~/assets/css/tailwind.css";
</style>
