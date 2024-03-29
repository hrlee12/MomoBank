<script setup>
import { ref } from "vue";

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
const emit = defineEmits(["update:isVisible"]);
const close = () => {
  emit("update:isVisible", false);
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
    close();
  }
  document.removeEventListener("mouseup", stopDrag);
  document.removeEventListener("mousemove", dragging);
  document.removeEventListener("touchend", stopDrag);
  document.removeEventListener("touchmove", dragging);
};

// 임시 데이터
const bankList = ref([
  { bankId: 0, bankTitle: "모모은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 1, bankTitle: "모뭬은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 2, bankTitle: "모무은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 3, bankTitle: "모먀은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 4, bankTitle: "모묘은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 5, bankTitle: "모미은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 6, bankTitle: "모뫄은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 7, bankTitle: "모므은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 8, bankTitle: "모머은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 9, bankTitle: "모먀은행", bankLogoUrl: "logo-icon.png" },
  { bankId: 10, bankTitle: "모며은행", bankLogoUrl: "logo-icon.png" },
]);
</script>

<template>
  <transition name="slide">
    <div v-if="isVisible" class="modal-overlay" @click="close">
      <div class="modal-container" @click.stop>
        <!-- Modal Content Here -->
        <!-- 끌어내릴 수 있다는 표시 -->
        <div
          class="drag-icon"
          @mousedown="startDrag"
          @touchstart="startDrag"
        ></div>
        <div class="method-container">
          <!-- 은행사 리스트 -->
          <div
            v-for="(bank, index) in bankList"
            :key="index"
            class="method-content"
            @click="method = bank.bankId"
          >
            <img :src="getImageUrl(`${bank.bankLogoUrl}`, 0)" alt="" />
            <p>{{ bank.bankTitle }}</p>
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
.modal-container {
  width: 100%;
  background: white;
  padding: 10px 20px;
  box-sizing: border-box;
  border-radius: 20px 20px 0 0;
  display: flex;
  flex-direction: column;
}

img {
  height: 3vh;
}

.method-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(12vh, auto));
  gap: 4vw;
  overflow: hidden;

  overflow-y: auto; /* 세로 방향 스크롤 활성화 */
  max-height: 50vh; /* 컨테이너의 최대 높이 설정 */
}

.method-content {
  aspect-ratio: 1 / 1; /* 정사각형 비율 설정 */
  justify-content: space-evenly;
  padding: 5%;

  p {
    color: black;
    font-weight: bold;
  }
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
</style>
