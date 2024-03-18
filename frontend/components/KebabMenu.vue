<script setup>
import { ref } from "vue";

const isVisible = ref(false);

const toggleMenu = (event) => {
  // Prevent the click from immediately closing the menu
  event.stopPropagation();
  isVisible.value = !isVisible.value;
};
</script>

<template>
  <div class="kebab-menu" @click="toggleMenu">
    <div class="kebab-icon">
      <span></span>
      <span></span>
      <span></span>
    </div>
    <div class="menu-content" :class="{ show: isVisible }">
      <a href="#">Option 1</a>
      <a href="#">Option 2</a>
      <a href="#">Option 3</a>
    </div>
  </div>
</template>

<style scoped lang="scss">
.kebab-menu {
  position: relative;
  display: inline-block;
}

.kebab-icon {
  cursor: pointer;
  display: block;
}

.kebab-icon span {
  display: block;
  width: 20px;
  height: 2px;
  background-color: #333;
  margin: 5px 0;
}

.menu-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 100px;
  right: 0px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 12px 16px;
  z-index: 1;
}

.kebab-icon:hover + .menu-content,
.menu-content:hover {
  display: block;
}

.menu-content a {
  color: black;
  text-decoration: none;
  display: block;
  padding: 8px 0;
}

.menu-content a:hover {
  background-color: #ddd;
}
</style>
