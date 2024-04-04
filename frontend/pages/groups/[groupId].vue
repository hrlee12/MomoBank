<!-- TODO : API 연동 시 변경 사항
- 피드 게시글 상세보기 눌렀을 떄 다른 게시글까지 변경되는거 수정
- api 연동할 때 파일명 변경하기
- 예산, 거래내역 링크 연결 모두 id로 연결되기 때문에 link 설정 다 동적으로 변경해줘야함.
-->
<script setup>
import AccountInformation from "~/components/group/AccountInformation.vue";
import { useGroupStore } from "@/stores/group";

const groupStore = useGroupStore();

const { groupId } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일

import { useGroupApi } from "~/api/groups";

const { getGroupFeedList, getGroupHome, getGroupNoticeList } = useGroupApi();

const fetchGroupFeeds = async (groupId) => {
  try {
    const response = await getGroupFeedList(groupId);
    return response.data;
  } catch (error) {
    console.error("피드 목록을 불러오는 데 실패했습니다.", error);
  }
};

const groupData = ref({});

const remitStore = useRemitStore();

const memberId = remitStore.memberId;

const fetchGroupHome = async (groupId, memberId) => {
  try {
    const response = await getGroupHome(groupId, memberId);
    return response.data;
  } catch (error) {
    console.error("그룹 홈 데이터를 불러오는 데 실패했습니다.", error);
  }
};

const fetchGroupNoticeList = async (groupId) => {
  try {
    const response = await getGroupNoticeList(groupId);
    return response.data;
  } catch (error) {
    console.error("그룹 공지사항 데이터를 불러오는 데 실패했습니다.", error);
  }
};

const feedList = ref(null);
const noticeTitle = ref("");

onMounted(() => {
  fetchGroupFeeds(groupId).then((response) => {
    if (response.content[0].length !== 0) {
      feedList.value = response.content;
    }
    console.log(feedList.value);
  });

  fetchGroupNoticeList(groupId).then((response) => {
    noticeTitle.value = response[0].title;
  });

  fetchGroupHome(groupId, memberId).then((response) => {
    groupData.value = response.data;
    const groupName = groupData.value.groupName;
    groupStore.updateGroupHeaderName(groupName);
    groupStore.updateAccountNumber(groupData.value.accountNumber);
    groupStore.updateGroupId(groupId);
    groupStore.updatePaymentStatus(groupData.value.status);
    groupStore.updateGroupMemberId(groupData.value.groupMemberId);
    groupStore.updateGroupBalance(groupData.value.balance);
    groupStore.updateGroupRole(groupData.value.role);
    console.log(groupData.value);
  });
});

definePageMeta({
  layout: "groups",
});

// Define a method to dynamically require images
const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// 피드 내용 상세보기(더보기 클릭)

const fullText = ref(noticeTitle.value);
const showFullText = ref(false);

const displayText = computed(() => {
  return showFullText.value ? fullText.value : fullText.value;
});

const isTextOverflow = computed(() => {
  // 이 예제에서는 단순화를 위해 길이를 기준으로 판단합니다.
  // 실제로는 텍스트와 컨테이너의 너비를 비교하는 등의 방법을 사용할 수 있습니다.
  return fullText.value.length > 20;
});

const toggleText = () => {
  showFullText.value = !showFullText.value;
};
</script>

<template>
  <div class="bg-white h-44 rounded-b-[14px]">
    <div>
      <!-- 상세, 납부 완료, 접기/펴기 아이콘 -->
      <div class="flex flex-row justify-between">
        <NuxtLink :to="`/groups/detail/${groupId}`">
          <div
            class="flex items-center justify-center w-10 h-6 ml-4 bg-light-gray-color rounded-xl"
          >
            <div class="text-gray-color text-[13px]">상세</div>
          </div>
        </NuxtLink>

        <div v-if="groupData.status" class="items-center">
          <p class="text-positive-color text-[13px]">납부 완료</p>
        </div>
        <div v-if="!groupData.status" class="items-center">
          <p class="text-negative-color text-[13px]">납부 요망</p>
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
  <nuxt-link v-if="noticeTitle !== ''" :to="`/groups/announcement/${groupId}`">
    <div
      class="flex items-center justify-between w-full h-12 mx-auto mt-2 bg-white rounded-xl"
    >
      <div>
        <img class="w-8 h-8 ml-4" :src="getImageUrl('notice-icon.png', 0)" />
      </div>
      <div class="overflow-hidden text-ellipsis whitespace-nowrap w-52">
        {{ noticeTitle }}
      </div>
      <div class="mr-3 text-sm font-semibold text-gray-color">전체보기</div>
    </div>
  </nuxt-link>

  <!-- 피드리스트 -->
  <div v-if="feedList === null" class="font-bold text-center">
    <p>피드가 존재하지 않습니다.</p>
  </div>
  <div class="pb-16">
    <div
      v-for="item in feedList"
      :key="item.id"
      class="w-full h-full mx-auto mt-2 bg-white"
    >
      <div class="pb-4 border-b-2 border-light-gray-color">
        <!-- 프로필, 닉네임, 게시일 -->
        <div class="flex items-center pt-3 ml-2">
          <div>
            <img
              class="w-8 h-8"
              :src="getImageUrl('user-icon-3.png', 0)"
              alt="user-icon"
            />
          </div>
          <div class="ml-2">싸피</div>
          <div class="ml-2 text-sm text-gray-color">
            {{ item.createdAt.slice(0, 10) }}
          </div>
        </div>
        <!-- 이미지 -->
        <div class="mt-2">
          <img
            class="w-full h-64"
            :src="item.mediaList[0].mediaUrl"
            alt="image-1"
          />
        </div>
        <!-- 하트 -->
        <div class="w-6 h-6 mt-2 ml-2">
          <img :src="getImageUrl('like.png', 0)" alt="like" />
        </div>
        <!-- 좋아요 -->
        <div class="ml-2 font-bold text-[13px]">
          좋아요 {{ item.likesCount }}개
        </div>

        <!-- 피드 더보기 상세 내용 -->
        <div
          v-if="!showFullText"
          class="ml-2 overflow-hidden cursor-pointer w-72 text-ellipsis whitespace-nowrap text-gray-color"
          @click="toggleText"
        >
          {{ item.content }}
        </div>

        <div
          v-if="isTextOverflow && !showFullText"
          class="ml-2 cursor-pointer text-gray-color"
          @click="toggleText"
        >
          더보기
        </div>

        <div
          v-if="showFullText"
          class="ml-2 cursor-pointer text-gray-color"
          @click="toggleText"
        >
          {{ fullText }}
        </div>

        <!-- 댓글 모두 보기 -->
        <div class="mt-2 ml-2 cursor-pointer text-gray-color text-[15px]">
          댓글 {{ item.commentsCount }}개 모두 보기
        </div>

        <!-- 댓글 달기 -->
        <div class="ml-2 cursor-pointer text-gray-color text-[15px]">
          댓글 달기
        </div>
      </div>
    </div>
  </div>
</template>
