<script setup>
import SimpleGroup from "~/components/bank/SimpleGroup.vue";
import AddBox from "~/components/bank/AddBox.vue";

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
    <div v-for="(group, index) in groups" :key="index">
      <SimpleGroup
        :groupName="group.groupName"
        :groupMoney="group.groupMoney"
        :groupJoinDate="group.groupJoinDate"
        :state="group.state"
        :delayDate="group.delayDate"
        :groupDescription="group.groupDescription"
        :groupMemberCount="group.groupMemberCount"
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
