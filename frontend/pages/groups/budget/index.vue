<script setup>
import AccountInformation from "~/components/group/AccountInformation.vue";

import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();
const remitStore = useRemitStore();

import { useGroupApi } from "~/api/groups";

const { getGroupBudget } = useGroupApi();

const groupBudgetList = ref({});

const memberId = remitStore.memberId;

const groupId = groupStore.groupId;

const fetchGroupBudgetList = async (groupId, memberId) => {
  try {
    const response = await getGroupBudget(groupId, memberId);
    return response.data;
  } catch (error) {
    console.error("그룹 예산 데이터 리스트를 불러오는 데 실패했습니다.", error);
  }
};

onMounted(() => {
  fetchGroupBudgetList(groupId, memberId).then((response) => {
    groupBudgetList.value = response.data;
    console.log(groupBudgetList.value);
  });
});

definePageMeta({
  layout: "groups",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const calPercentage = (goalBudget, curBudget) => {
  return (curBudget / goalBudget) * 100;
};
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

  <!-- 공지사항 -->
  <div
    class="flex items-center justify-between w-11/12 h-12 mx-auto mt-5 bg-white rounded-xl"
  >
    <div>
      <img class="w-8 h-8 ml-4" :src="getImageUrl('notice-icon.png', 0)" />
    </div>
    <div class="overflow-hidden text-ellipsis whitespace-nowrap w-52">
      연체하면 알지? ^^
    </div>
    <div class="mr-3 text-sm font-semibold text-gray-color">전체보기</div>
  </div>
  <div class="mb-20">
    <nuxt-link
      v-for="budgetItem in groupBudgetList"
      :key="budgetItem.id"
      :to="`/groups/budget/${budgetItem.budgetId}`"
    >
      <div class="flex flex-col items-center">
        <div
          class="flex justify-center w-3/12 mt-5 rounded-lg bg-light-gray-color"
        >
          <div class="font-bold">
            {{ budgetItem.finalDueDate }}
          </div>
        </div>
        <div
          class="flex flex-col w-11/12 h-32 mt-2 bg-white border-8 border-white rounded-2xl"
        >
          <!-- setting icon -->
          <div class="flex justify-between">
            <div
              class="overflow-hidden text-base font-bold text-ellipsis w-52 whitespace-nowrap"
            >
              {{ budgetItem.name }}
            </div>
            <!-- TODO : 납부 완/미완을 데이터 넘어오면 그걸로 if문 넣고 색상 바꾸자. -->
            <div
              v-if="budgetItem.status"
              class="text-positive-color text-[13px]"
            >
              납부 완료
            </div>
            <div
              v-if="!budgetItem.status"
              class="text-negative-color text-[13px]"
            >
              납부 요망
            </div>
            <div class="w-5 h-5">
              <img
                :src="getImageUrl('setting-icon.png', 0)"
                alt="setting icon"
              />
            </div>
          </div>
          <!-- 매월 x일 입금, 월마다 입금 금액 -->
          <div class="flex justify-between pt-2 mb-3">
            <div class="text-sm font-bold">
              매월 {{ budgetItem.monthlyDueDate }}일
            </div>
            <div class="text-sm font-bold">
              {{ budgetItem.monthlyFee }}원/월
            </div>
          </div>
          <!-- 납입 여부 -->
          <div class="flex items-center justify-between">
            <div></div>
          </div>
          <div class="flex items-center justify-between">
            <div class="pr-1 text-xs whitespace-nowrap">0원</div>
            <!-- 게이지로 표시 -->
            <div class="relative w-full h-7">
              <div
                class="absolute inset-0 overflow-hidden bg-gray-200 rounded-full"
              >
                <div></div>
                <div
                  class="h-full bg-main-color"
                  :style="{
                    width:
                      calPercentage(
                        budgetItem.finalFee,
                        budgetItem.currentFee
                      ) + '%',
                  }"
                ></div>
              </div>
              <div
                class="absolute flex items-center justify-center w-full h-full"
              >
                <span class="font-bold">{{ budgetItem.currentFee }}원</span>
              </div>
            </div>
            <div class="text-xs w-7">{{ budgetItem.finalFee / 10000 }}만원</div>
          </div>
        </div>
      </div>
    </nuxt-link>

    <!-- 예산 추가 -->
    <nuxt-link v-if="groupStore.groupRole === '모임장'" to="/groups/budget/add">
      <div
        class="flex items-center justify-center w-11/12 h-16 mx-auto mt-5 bg-light-gray-color rounded-xl"
      >
        <div>
          <img class="w-8 h-8" :src="getImageUrl('add-icon.png', 0)" />
        </div>
      </div>
    </nuxt-link>
  </div>
</template>
