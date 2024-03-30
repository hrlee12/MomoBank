<script setup>
const route = useRoute();

// TODO : onMounted 해서 데이터들 가져와야함

const feedComments = [
  { id: 1, value: "그립다!! 또 가고싶다!!", author: "김성수" },
  { id: 2, value: "그립다!! 또 가고싶다!!1", author: "김성수" },
  { id: 3, value: "그립다!! 또 가고싶다!!2", author: "김성수" },
  { id: 4, value: "그립다!! 또 가고싶다!!3", author: "김성수" },
  { id: 5, value: "그립다!! 또 가고싶다!!4", author: "김성수" },
  { id: 6, value: "그립다!! 또 가고싶다!!5", author: "김성수" },
  { id: 7, value: "그립다!! 또 가고싶다!!6", author: "김성수" },
  { id: 7, value: "그립다!! 또 가고싶다!!6", author: "김성수" },
  { id: 7, value: "그립다!! 또 가고싶다!!6", author: "김성수" },
  { id: 7, value: "그립다!! 또 가고싶다!!6", author: "김성수" },
  { id: 7, value: "그립다!! 또 가고싶다!!6", author: "김성수" },
];

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// 모달이 켜졌는지 꺼졌는지 props 및 emit
const props = defineProps({
  isVisible: Boolean,
});
const emit = defineEmits(["update"]);
// 모달 종료 함수
const close = (data) => {
  // emit으로 isVisible, bank 정보 반환
  emit("update", { isVisible: false, data });
};

// 드래그 감지 함수
const startY = ref(0);
const endY = ref(0);

const startDrag = (event) => {
  startY.value = event.touches ? event.touches[0].clientY : event.clientY;
  document.addEventListener("mouseup", stopDrag);
  document.addEventListener("mousemove", dragging);
  document.addEventListener("touchend", stopDrag);
  document.addEventListener("touchmove", dragging);
};

const dragging = (event) => {
  endY.value = event.touches ? event.touches[0].clientY : event.clientY;
};

const stopDrag = () => {
  if (endY.value - startY.value > 50) {
    // If dragged down significantly
    close("");
  }
  document.removeEventListener("mouseup", stopDrag);
  document.removeEventListener("mousemove", dragging);
  document.removeEventListener("touchend", stopDrag);
  document.removeEventListener("touchmove", dragging);
};
</script>

<template>
  <transition name="slide">
    <div v-if="isVisible" class="modal-overlay" @click="close('')">
      <div class="modal-container" @click.stop>
        <!-- Modal Content Here -->
        <!-- 끌어내릴 수 있다는 표시 -->
        <div
          class="drag-icon"
          @mousedown="startDrag"
          @touchstart="startDrag"
        ></div>
        <div class="max-h-[50vh] overflow-auto">
          <div class="comments">댓글</div>
          <div v-if="route.name === 'groups-feed-detail'">
            <div
              v-for="item in feedComments"
              :key="item.id"
              class="comments-child"
            >
              <div>
                <img
                  class="comments-img"
                  :src="getImageUrl('tiger-image.jpg', 1)"
                  alt="profile-img"
                />
              </div>
              <div class="flex flex-col">
                <div class="font-bold">{{ item.author }}</div>
                <div class="text-sm">{{ item.value }}</div>
              </div>
            </div>
          </div>
          <div class="flex">
            <div>
              <img
                class="rounded-full w-11 h-11"
                :src="getImageUrl('tiger-image.jpg', 1)"
                alt="tiger-image"
              />
            </div>
            <input
              type="text"
              name="comments"
              id="comments"
              placeholder="댓글 추가.."
            />
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.slide-enter-active,
.slide-leave-active {
  transition: opacity 0.5s, transform 0.5s;
  opacity: 1;
}
.slide-enter-from,
.slide-leave-to {
  opacity: 0;
  transform: translateY(100%);
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  z-index: 1000;
}

.comments {
  font-weight: bold;
  text-align: center;
  border-bottom: 1px solid $light-gray-color;
  padding-bottom: 3%;
}

.modal-container {
  width: 100%;
  background: white;
  padding: 10px 20px;
  box-sizing: border-box;
  border-radius: 20px 20px 0 0;
  display: flex;
  flex-direction: column;
}

.drag-icon {
  width: 20%;
  height: 10%;
  min-width: 100px; /* 고정 너비 */
  min-height: 5px; /* 고정 높이 */
  border-radius: 20px;
  align-self: center;
  background-color: $light-gray-color;
  margin-bottom: 4vh;
}

.comments-child {
  display: flex;
  align-items: center;
  padding: 2% 0;
}

.comments-img {
  border-radius: 100%;
  width: 2.75rem;
  height: 2.75rem;
  margin-right: 0.5rem;
}
</style>
