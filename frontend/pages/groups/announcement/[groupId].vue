<script setup>
definePageMeta({
  layout: "groups",
});

import { useGroupApi } from "~/api/groups";

const { groupId } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일

const { getGroupNoticeList } = useGroupApi();

const fetchGroupNoticeList = async (groupId) => {
  try {
    const response = await getGroupNoticeList(groupId);
    return response.data;
  } catch (error) {
    console.error("공지사항 목록을 불러오는 데 실패했습니다.", error);
  }
};

const groupNoticeData = ref({});

onMounted(() => {
  fetchGroupNoticeList(groupId).then((response) => {
    groupNoticeData.value = response;
    console.log(groupNoticeData.value);
  });
});

// Define a method to dynamically require images
const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>
<template>
  <div class="h-screen bg-white">
    <div class="flex justify-between">
      <div></div>
      <nuxt-link :to="`/groups/announcement/add/`">
        <div class="w-6 h-6 mt-4 mr-4">
          <img :src="getImageUrl('add-icon2.png', 0)" alt="add-icon" />
        </div>
      </nuxt-link>
    </div>
    <div v-for="notice in groupNoticeData" :key="notice.id">
      <nuxt-link :to="`/groups/announcement/detail/${notice.notedId}`">
        <div class="flex justify-center">
          <div
            class="flex flex-col justify-center-center w-11/12 h-11 mt-8 pl-5 border-b-[1px] border-light-gray-color"
          >
            <div class="flex items-center pb-2 text-sm font-bold h-1/6">
              <div
                class="w-9/12 overflow-hidden whitespace-nowrap text-ellipsis"
              >
                {{ notice.title }}
              </div>
            </div>
            <div class="mt-4 text-xs">
              <div class="flex items-center">
                <div>{{ notice.updatedAt.split("T")[0] }}</div>
                <div class="ml-2">{{ notice.memberName }}</div>
                <div class="w-3 h-3 ml-1">
                  <img :src="getImageUrl('red-crown.png', 0)" alt="파란 왕관" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </nuxt-link>
    </div>
  </div>
</template>
