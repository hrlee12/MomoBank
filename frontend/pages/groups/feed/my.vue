<script setup>
import { useGroupApi } from "~/api/groups";

const { getMyFeed } = useGroupApi();

const feedTab = ref("feed");

const totalPay = "600,000원";

function selectTab(selectedTab) {
  feedTab.value = selectedTab;
}

const feedList = ref([]);

const fetchMyFeed = async (groupId) => {
  try {
    const response = await getMyFeed(groupId);
    console.log(response.data[0]);

    feedList.value = response.data;
    return response.data;
  } catch (error) {
    console.error("모임 인원 목록을 불러오는 데 실패했습니다.", error);
  }
};

onMounted(() => {
  // 경고!! 여기서는 10번 유저를 임시로 했는데, 실제 로그인한 그룹 멤버 아이디로 바꾸어줘야함
  fetchMyFeed(10).then((response) => {});
});

// TODO : mounted 해서 나의 피드 목록을 모두 불러와야함.
// TODO : 만약 피드나 정보가 없을 경우 "친구들과의 소중한 순간을 기록해보세요"와 같은 문구를 화면에 뿌려주자

definePageMeta({
  layout: "groups",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="h-screen bg-white">
    <div class="flex flex-col items-center pt-4">
      <!-- 프로필 -->
      <div class="mx-auto">
        <img
          class="w-16 h-16 rounded-full"
          :src="getImageUrl('image-1.png', 1)"
          alt="image-1"
        />
      </div>
      <!-- 이름 -->
      <div class="font-bold">싸피</div>
    </div>
    <!-- 가입 날짜, 피드 등록 아이콘 -->
    <div class="flex items-center justify-between">
      <div></div>
      <!-- pl을 선언한것은 +아이콘의 크기만큼 우측으로 이동 시켜서 중앙 정렬하게하기 위함이다. -->
      <div class="pl-11 text-gray-color">가입날짜: 2024-03-22</div>
      <nuxt-link to="/groups/feed/write">
        <div class="pr-3">
          <div
            class="flex items-center justify-center w-8 h-8 border-2 rounded-md border-gray-color"
          >
            <img
              class="w-5 h-5"
              :src="getImageUrl('add-icon2.png', 0)"
              alt="add-icon2"
            />
          </div>
        </div>
      </nuxt-link>
    </div>

    <!-- 게시글, 정보 탭-->
    <div class="flex justify-center mt-4">
      <button
        class="w-1/2 pb-2 border-b-[1px] border-main-color"
        :class="
          feedTab === 'feed'
            ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
            : 'w-1/2 border-transparent'
        "
        @click="selectTab('feed')"
      >
        게시글
      </button>
      <button
        class="w-1/2 pb-2 border-b-[1px] border-main-color"
        :class="
          feedTab === 'myInfo'
            ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
            : 'w-1/2  border-transparent'
        "
        @click="selectTab('myInfo')"
      >
        정보
      </button>
    </div>

    <!-- 피드 이미지 박스 나열 -->
    <div v-if="feedTab === 'feed'" class="container px-2 pt-4 mx-auto bg-white">
      <div class="grid grid-cols-3 gap-[0.15rem]">
        <div v-for="item in feedList" :key="item.id" class="aspect-square">
          <nuxt-link :to="`/groups/feed/detail?feedId=${item.feedId}`">
            <img
              class="object-cover w-full h-full"
              :src="item['mediaList'][0]['mediaUrl']"
            />
          </nuxt-link>
        </div>
      </div>
    </div>
    <!-- 정보 탭 눌렀을 때 -->
    <div v-if="feedTab === 'myInfo'">
      <div
        class="flex items-center justify-between w-11/12 h-8 px-4 mx-auto mt-4 bg-light-gray-color rounded-xl"
      >
        <div class="font-bold">총 납부 금액</div>
        <div class="font-bold">{{ totalPay }}</div>
      </div>

      <!-- 년도, 총 금액 bar -->
      <div
        class="flex items-center w-11/12 h-8 mx-auto mt-6 bg-gray-100 rounded-xl"
      >
        <div class="pl-3 font-bold">2024년</div>
      </div>

      <!-- 월, 납부 회비, 미납 회비 -->
      <div class="flex justify-around mt-3">
        <div class="flex items-center">
          <div>3월</div>
        </div>
        <div>
          <div>납부 회비: 200,000원</div>
          <div>미납 회비: 0원</div>
        </div>
      </div>
      <div class="flex justify-around mt-3">
        <div class="flex items-center">
          <div>2월</div>
        </div>
        <div>
          <div>납부 회비: 200,000원</div>
          <div>미납 회비: 0원</div>
        </div>
      </div>
      <div class="flex justify-around mt-3">
        <div class="flex items-center">
          <div>1월</div>
        </div>
        <div>
          <div>납부 회비: 200,000원</div>
          <div>미납 회비: 0원</div>
        </div>
      </div>
    </div>
  </div>
</template>
