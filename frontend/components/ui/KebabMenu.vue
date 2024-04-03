<script setup>
import { ref } from "vue";
const isVisible = ref(false);

const toggleMenu = (event) => {
  event.stopPropagation();
  isVisible.value = !isVisible.value;
};

// 이벤트를 발생시키기 위한 emits 옵션 정의
const emit = defineEmits(["deleteAccount"]);

const requestAccountDeletion = async () => {
  // accountDeletion 이벤트를 발생시키며 부모 컴포넌트에 알림
  emit("deleteAccount");
};
</script>

<template>
  <div class="kebab" @click="toggleMenu">
    <figure></figure>
    <figure></figure>
    <figure></figure>
    <ul class="dropdown" :class="{ active: isVisible }">
      <li @click="requestAccountDeletion">삭제하기</li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

$cubic-out: cubic-bezier(0.32, 2.04, 0.85, 0.54);
$cubic-in: cubic-bezier(0.72, 1.2, 0.71, 0.72);

$cyan: $gray-color;
$yellow: #ffeb3b;
$grey: #9e9e9e;

$shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.16), 0 2px 8px 0 rgba(0, 0, 0, 0.12);

// Kebab styling

.kebab {
  cursor: pointer;
  position: relative;
  display: inline-block;
  box-sizing: border-box;
  top: 12px;

  figure {
    width: 4px;
    height: 4px;
    border-radius: 5px;
    background: $cyan;
    margin: 3px 0;
  }
}

// Other styling
h1 {
  font-size: 26px;
  background: $cyan;
  color: white;
  padding: 40px 0 40px 20%;
  margin-bottom: 50px;
}

.dropdown {
  position: absolute;
  right: 0;
  top: 2em;
  transition: all 0.25s ease-out;
  transform: scale(0);
  transform-origin: 100% 0;
  box-shadow: $shadow;
  border-radius: 10px;
  background-color: white;

  li {
    color: darken($grey, 20%);
    text-decoration: none;
    display: flex;
    min-width: 100px;
    padding: 1em 18px;
    cursor: pointer;
    &:hover {
      background: lighten($grey, 30%);
    }
  }
}

.dropdown.active {
  transform: scale(1);
  transition: all 0.25s cubic-bezier(0.5, 1.8, 0.9, 0.8);
}
</style>
