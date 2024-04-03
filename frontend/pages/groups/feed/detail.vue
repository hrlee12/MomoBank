<script setup>
import CompleteModal from "~/components/layout/CompleteModal.vue";
import GroupsBottomSheetModal from "~/components/layout/GroupsBottomSheetModal.vue";
import { useRoute } from 'vue-router';
import { useGroupApi } from "~/api/groups";
const { getMyFeed } = useGroupApi();

const route = useRoute();
const feedId = route.query.feedId;




const fetchMyFeed = async (groupId) => {
  try {
    const response = await getMyFeed(groupId);

    const filteredData = response.data.filter(data => data.feedId == feedId);

    for (var i = 0; i < filteredData[0].comments.length; i++) {
      console.log(filteredData[0].comments[i]);
      filteredData[0].comments[i].id = filteredData[0].comments[i].commentId
      filteredData[0].comments[i].value = filteredData[0].comments[i].content

    }
    feed.value.comments = filteredData[0].comments;

    console.log(JSON.stringify(filteredData[0].mediaList));


    const convertArray = (original) => {
      return original.map((item, index) => {
        return {
          id: item.mediaId,
          url: item.mediaUrl
        };
      });
    };
    feed.value.image = convertArray(filteredData[0].mediaList);
    imageList.value = convertArray(filteredData[0].mediaList);
    feed.value.contents = filteredData[0].content
    feed.value.name = filteredData[0].groupMemberName
    feed.value.likeCnt = filteredData[0].likesCount
    feed.value.commentsCnt = filteredData[0].commentsCount


    return response.data;
  } catch (error) {
    console.error("모임 인원 목록을 불러오는 데 실패했습니다.", error);
  }
};

onMounted(() => {
  // 경고!! 여기서는 10번 유저를 임시로 했는데, 실제 로그인한 그룹 멤버 아이디로 바꾸어줘야함
  fetchMyFeed(10).then((response) => {

  });
});



definePageMeta({
  layout: "groups",
});

const feed = ref({
  id: 6,
  likeCnt: 6,
  contents: "5반 5린이들과 함께간 일본 후쿠오카에서 사진 한장!",
  comments: [
    { id: 23, value: "와우!!" },
    { id: 24, value: "언블리버블!!" },
    { id: 25, value: "브라비씨아!" },
  ],
  commentsCnt: 7,
  created: "2024-03-29",
  updated: "2024-03-29",
  image: [
    { id: 13, url: "tiger-image.jpg" },
    { id: 14, url: "tiger-image2.jpg" },
    { id: 15, url: "tiger-image3.jpg" },
    { id: 16, url: "image-1.png" },
  ],
});

const imageList = ref(feed.value.image);

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const showFullText = ref(false);

const isTextOverflow = computed(() => {
  // 이 예제에서는 단순화를 위해 길이를 기준으로 판단합니다.
  // 실제로는 텍스트와 컨테이너의 너비를 비교하는 등의 방법을 사용할 수 있습니다.
  return feed.value.contents.length > 20;
});

const toggleText = () => {
  showFullText.value = !showFullText.value;
};

const isDropdownVisible = ref(false);

const cancelButton = true;
const visibleModal = ref(false);
const visibleBottomModal = ref(false);
const visibleComments = ref(false);

// 사용자 정의 드롭다운 메뉴를 토글하는 함수
const toggleDropdown = () => {
  isDropdownVisible.value = !isDropdownVisible.value;
};
const editMode = ref(false);

const selectOption = (option) => {
  if (option === "edit") {
    editMode.value = true;
  } else if (option === "delete") {
    visibleModal.value = true;
  }
  isDropdownVisible.value = false; // 드롭다운 메뉴를 닫습니다.
};

const cancelEdit = () => {
  editMode.value = false;
};

const isVisibleModal = (value) => {
  visibleModal.value = value;
};

function removeImage(imageId) {
  imageList.value = imageList.value.filter((item) => item.id !== imageId);
}

const adjustHeight = (event) => {
  const textarea = event.target;
  textarea.style.height = "auto"; // 높이를 초기화
  textarea.style.height = textarea.scrollHeight + "px"; // scrollHeight를 사용하여 높이 조정
};

const visibleBottomModalClick = () => {
  visibleBottomModal.value = true;
  visibleComments.value = true;
};

const handleUpdate = (event) => {
  visibleBottomModal.value = event.isVisible;
  visibleComments.value = event.commentsVisible;
};

// TODO : 수정 버튼 클릭했을 떄 API 호출.
</script>
<template>
  <div class="h-screen bg-white">
    <div class="flex items-center px-4 pt-4">
      <div class="mr-2">
        <img class="rounded-full w-11 h-11" :src="getImageUrl('tiger-image.jpg', 1)" alt="tiger-image" />
      </div>
      <div class="text-xl font-bold basis-11/12">{{ feed.name }}</div>
      <div v-if="!editMode" class="w-1">
        <img @click="toggleDropdown" :src="getImageUrl('kebab-icon.png', 0)" alt="kebab-icon" />
      </div>
      <div v-if="editMode" class="relative right-0">
        <div class="absolute top-[-0.8rem] w-10 font-bold text-main-color right-14">
          <div>수정</div>
        </div>
        <div class="absolute right-0 top-[-0.8rem] w-10 font-bold text-negative-color" @click="cancelEdit">
          <div>취소</div>
        </div>
      </div>
    </div>
    <div class="relative">
      <div v-if="isDropdownVisible" class="absolute right-0 z-50 h-24 bg-white border-2 w-28">
        <div class="text-center">
          <div @click="selectOption('edit')" class="px-2 py-3 border-b border-gray-color">
            수정
          </div>
          <div @click="selectOption('delete')" class="pt-3">삭제</div>
        </div>
      </div>
    </div>

    <Swiper class="container w-full h-80">
      <SwiperSlide v-for="item in imageList" :key="item.id" class="w-full h-auto mt-4">
        <div v-if="editMode" class="relative">
          <div class="absolute top-0 w-10 h-10 text-2xl font-bold border-4 border-white rounded-full right-2"
            @click="removeImage(item.id)">
            <p class="text-center">X</p>
          </div>
        </div>
        <img :src="item.url" alt="" class="object-cover w-full h-full" />
      </SwiperSlide>
    </Swiper>
    <div class="px-3 py-4 border-b border-light-gray-color">
      <img :src="getImageUrl('like.png', 0)" alt="like" class="w-5 h-5" />
      <div class="pt-1 font-bold">좋아요 {{ feed.likeCnt }}개</div>
      <div v-if="!showFullText && !editMode" class="pt-1 overflow-hidden w-72 text-ellipsis whitespace-nowrap">
        {{ feed.contents }}
      </div>
      <div v-if="isTextOverflow && !showFullText && !editMode" @click="toggleText" class="pt-1">
        더보기
      </div>
      <div v-if="editMode">
        <textarea type="text" name="contents" id="contents"
          class="w-full overflow-hidden text-base border-none resize-none" v-model="feed.value.contents"
          @input="adjustHeight"></textarea>
      </div>
      <div v-if="showFullText && !editMode" @click="toggleText" class="pt-2">
        {{ feed.contents }}
      </div>
      <div @click="visibleBottomModalClick">
        댓글 {{ feed.commentsCnt }}개 모두 보기
      </div>
      <div>댓글 달기...</div>
      <div>{{ feed.created }}</div>
    </div>
  </div>
  <CompleteModal v-if="visibleModal" :cancelButton="cancelButton" @visible-modal="isVisibleModal" class="z-50">
  </CompleteModal>
  <GroupsBottomSheetModal :isComments="visibleComments" :isVisible="visibleBottomModal" @comments-update="handleUpdate">
  </GroupsBottomSheetModal>
  <div v-if="visibleBottomModal" class="modal-bg"></div>
</template>
<style lang="scss" scoped>
.modal-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 999;
}
</style>
