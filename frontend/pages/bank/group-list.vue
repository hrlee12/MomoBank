<script setup>
import SimpleGroup from "~/components/bank/SimpleGroup.vue";
import AddBox from "~/components/bank/AddBox.vue";
import { useGroupApi } from "~/api/groups";

const { getMyGroups } = useGroupApi();

const myGroups = ref([]);
const remitStore = useRemitStore();
const memberId = remitStore.memberId;

const fetchMyGroups = async (memberId) => {
  try {
    const response = await getMyGroups(memberId);
    return response.data;
  } catch (error) {
    console.error("나의 모임 목록을 불러오는 데 실패했습니다.", error);
  }
};

const router = useRouter();

definePageMeta({
  layout: "bank-home",
});

// 각각의 그룹 페이지로 이동
const goToGroup = (groupId) => {
  router.push(`/groups/` + groupId);
};

// 전체 계좌 리스트 받는 함수
onMounted(async () => {
  fetchMyGroups(memberId).then((response) => {
    myGroups.value = response.data.groupList;
    console.log(myGroups.value);
  });
});

definePageMeta({
  layout: "bank",
});

const groups = ref([
  {
    groupName: "저축은행",
    groupMoney: 1000000,
    groupJoinDate: "2024-03-12",
    state: true,
    delayDate: 0,
    groupDescription:
      "설명이에요 이게 길어지면 어떻게 될까요? 한 번 실험해 봅시다.",
    groupMemberCount: 6,
  },
  {
    groupName: "효리모임",
    groupMoney: 1000000,
    groupJoinDate: "2024-03-12",
    state: true,
    delayDate: 0,
    groupDescription: "설명이에요",
    groupMemberCount: 6,
  },
  {
    groupName: "성수모임",
    groupMoney: 1000000,
    groupJoinDate: "2024-03-12",
    state: true,
    delayDate: 0,
    groupDescription: "설명이에요",
    groupMemberCount: 6,
  },
  {
    groupName: "소이모임",
    groupMoney: 1000000,
    groupJoinDate: "2024-03-12",
    state: false,
    delayDate: 3,
    groupDescription: "설명이에요",
    groupMemberCount: 6,
  },
]);
</script>

<template>
  <div class="account-container">
    <div v-for="(group, index) in myGroups" :key="index">
      <SimpleGroup
        :groupId="group.groupId"
        :groupName="group.name"
        :groupMoney="group.monthlyFee"
        :state="group.status"
        :delayDate="group.monthlyFee"
        :groupDescription="group.description"
        :groupMemberCount="group.joinMembers"
      />
    </div>
    <AddBox to="/bank/group-create" />
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";

.account-container {
  padding: 5%;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 30px;
}
</style>
