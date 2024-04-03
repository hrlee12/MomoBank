<script setup>
import { useGroupApi } from "~/api/groups";
import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();
const { getGroupMembers, createInviteCode } = useGroupApi();

const groupMembers = ref([]);

const groupId = groupStore.groupId;

const fetchGroupMembers = async (groupId) => {
  try {
    const response = await getGroupMembers(groupId);
    return response.data;
  } catch (error) {
    console.error("모임 인원 목록을 불러오는 데 실패했습니다.", error);
  }
};
const inviteBoolean = ref(false);

onMounted(() => {
  inviteBoolean.value = false;
  fetchGroupMembers(groupId).then((response) => {
    groupMembers.value = response.data;
    console.log(groupMembers.value);
  });
});

definePageMeta({
  layout: "groups",
});

const inviteCode = ref("");

const invite = async () => {
  try {
    const response = await createInviteCode(groupId);
    if (response.status === 200) {
      inviteBoolean.value = true;
      inviteCode.value = response.data.data.inviteLink;
      await navigator.clipboard.writeText(inviteCode.value); // 클립보드에 초대코드 자동으로 복사
    }
  } catch (error) {
    console.error(error); // 오류 처리
  }
};

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<!-- TODO : 유저인지, 방장인지 권한에 따라 납임 수정, 추방 보일지 정하기 -->
<template>
  <div class="h-screen bg-white">
    <!-- 플러스 아이콘 -->
    <div class="flex justify-between">
      <div></div>
      <div class="w-6 h-6 mt-4 mr-4" @click="invite">
        <img :src="getImageUrl('add-icon2.png', 0)" alt="add-icon" />
      </div>
    </div>
    <div class="flex justify-center">
      <div
        class="flex flex-row items-center w-11/12 h-8 mt-4 font-bold rounded-3xl pl-7 bg-light-gray-color"
      >
        <div class="basis-1/4">이름</div>
        <div class="basis-2/3">신뢰도</div>
        <div class="basis-1/4">총 {{ groupMembers.length }}명</div>
      </div>
    </div>
    <div class="text-xs">
      <div
        v-for="member in groupMembers"
        :key="member.id"
        class="flex justify-center"
      >
        <div
          class="flex flex-row items-center w-11/12 h-11 mt-3 pl-5 border-b-[1px] border-light-gray-color"
        >
          <div class="flex items-center basis-3/12">
            <div class="pr-1 font-bold">{{ member.name }}</div>
            <div v-if="member.role === '모임장'" class="w-4 h-4">
              <img :src="getImageUrl('red-crown.png', 0)" alt="red-crown" />
            </div>
          </div>
          <div class="pl-1 basis-4/12">{{ member.sincerity }}점</div>
        </div>
      </div>
    </div>
  </div>
  <div v-if="inviteBoolean" class="relative flex justify-center">
    <div
      class="absolute w-10/12 px-1 py-1 text-center rounded-lg bg-light-gray-color bottom-40"
    >
      초대코드가 클립보드에 복사되었습니다
    </div>
  </div>
</template>
