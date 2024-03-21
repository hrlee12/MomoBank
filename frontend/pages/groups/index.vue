<!-- TODO : API 연동 시 변경 사항
- DB 데이터 연결
- NuxtLink 연결
- 피드 게시글 상세보기 눌렀을 떄 다른 게시글까지 변경되는거 수정

-->
<script setup>
import { ref, computed } from "vue";
import GroupsHeader from "~/components/layout/GroupsHeader.vue";
import AccountInformation from "~/components/group/AccountInformation.vue";
import GroupFooter from "~/components/layout/GroupFooter.vue";

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

const fullText = ref("5반5린이들과 함께간 일본 후쿠오카에서 사진 한장!");
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
  <div class="bg-white h-60 rounded-b-[14px]">
    <div>
      <!-- 상세, 납부 완료, 접기/펴기 아이콘 -->
      <div class="flex flex-row justify-between">
        <NuxtLink to="/groups/detail">
          <div
            class="flex items-center justify-center w-10 h-6 ml-4 bg-light-gray-color rounded-xl"
          >
            <div class="text-gray-color text-[13px]">상세</div>
          </div>
        </NuxtLink>

        <div class="items-center">
          <p class="text-positive-color text-[13px]">납부 완료</p>
        </div>
        <div class="w-8 h-6 mr-4">
          <img
            class="rotate-90"
            :src="getImageUrl('arrow-icon.png', 0)"
            alt="arrow-icon"
          />
        </div>
      </div>

      <!-- 계좌 번호, 담긴 금액, 숨김 버튼 -->
      <AccountInformation></AccountInformation>
      <!-- 실선, 거래 내역, 예산 -->
      <div class="flex justify-center">
        <div class="mt-4 border-t-[1px] border-t-light-gray-color w-80"></div>
      </div>
      <div class="flex justify-center">
        <div class="flex mt-3 w-80">
          <div
            class="font-semibold text-[17px] w-40 text-center border-r-[1px] text-main-color"
          >
            거래내역
          </div>
          <div
            class="font-semibold text-[17px] w-40 text-center text-main-color"
          >
            예산
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 공지사항 -->
  <div
    class="flex items-center justify-between h-12 mx-auto mt-2 bg-white w-96 rounded-xl"
  >
    <div>
      <img class="w-8 h-8 ml-4" :src="getImageUrl('notice-icon.png', 0)" />
    </div>
    <div class="overflow-hidden text-ellipsis whitespace-nowrap w-52">
      드디어 일본여행! Let's go!!!!!!!!!!!!!!!!!!!
    </div>
    <div class="mr-3 text-sm font-semibold text-gray-color">전체보기</div>
  </div>

  <!-- 피드리스트 -->
  <div class="w-full h-full pb-16 mx-auto mt-2 bg-white">
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
        <div class="ml-2">kimsungsu_97</div>
        <div class="ml-2 text-sm text-gray-color">2일전</div>
      </div>
      <!-- 이미지 -->
      <div class="mt-2">
        <img
          class="w-full h-64"
          :src="getImageUrl('image-1.png', 1)"
          alt="image-1"
        />
      </div>
      <!-- 하트 -->
      <div class="w-6 h-6 mt-2 ml-2">
        <img :src="getImageUrl('like.png', 0)" alt="like" />
      </div>
      <!-- 좋아요 -->
      <div class="ml-2 font-bold text-[13px]">좋아요 5개</div>

      <!-- 피드 더보기 상세 내용 -->
      <div
        v-if="!showFullText"
        class="ml-2 overflow-hidden cursor-pointer w-72 text-ellipsis whitespace-nowrap text-gray-color"
        @click="toggleText"
      >
        {{ displayText }}
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
        댓글 7개 모두 보기
      </div>

      <!-- 댓글 달기 -->
      <div class="ml-2 cursor-pointer text-gray-color text-[15px]">
        댓글 달기
      </div>
    </div>

    <!-- 게시글 2 -->
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
        <div class="ml-2">crazy_97</div>
        <div class="ml-2 text-sm text-gray-color">3일전</div>
      </div>
      <!-- 이미지 -->
      <div class="mt-2">
        <img
          class="w-full h-64"
          :src="getImageUrl('image-2.png', 1)"
          alt="image-2"
        />
      </div>
      <!-- 하트 -->
      <div class="w-6 h-6 mt-2 ml-2">
        <img :src="getImageUrl('like.png', 0)" alt="like" />
      </div>
      <!-- 좋아요 -->
      <div class="ml-2 font-bold text-[13px]">좋아요 100개</div>

      <!-- 피드 더보기 상세 내용 -->
      <div
        v-if="!showFullText"
        class="ml-2 overflow-hidden cursor-pointer w-72 text-ellipsis whitespace-nowrap text-gray-color"
        @click="toggleText"
      >
        {{ displayText }}
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
        댓글 315개 모두 보기
      </div>

      <!-- 댓글 달기 -->
      <div class="ml-2 cursor-pointer text-gray-color text-[15px]">
        댓글 달기
      </div>
    </div>
  </div>
</template>
