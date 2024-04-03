<script setup>
const groupStore = useGroupStore();

// TODO : 생각해보니 Footer가 현재 바라보고 있는 모임 id를 기준으로 link가 설정되어야함.
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const groupId = computed(() => groupStore.groupId);
const tempPath = "/groups/" + groupId.value;
const menuItems = [
  { name: "모임메인", path: tempPath, icon: "main2-icon.png" },
  { name: "예산", path: "/groups/budget", icon: "budget-icon.png" },
  { name: "마이피드", path: "/groups/feed/my", icon: "user-icon.png" },
  { name: "리포트", path: "/groups/report", icon: "report-icon.png" },
  { name: "추가메뉴", path: "/groups/menu", icon: "apss-icon.png" },
];
</script>

<template>
  <footer>
    <ul
      class="fixed bottom-0 flex items-center justify-around w-full h-16 mt-16 bg-white"
    >
      <li v-for="(item, index) in menuItems" :key="index">
        <NuxtLink :to="item.path">
          <div class="w-6 h-8 item-icon">
            <img
              :src="getImageUrl ? getImageUrl(item.icon, 0) : ''"
              :alt="item.icon"
            />
          </div>

          <div class="text-xs">{{ item.name }}</div>
        </NuxtLink>
      </li>
    </ul>
  </footer>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

// NuxtLink가 활성화되면 실행할 스타일
.router-link-exact-active {
  filter: invert(26%) sepia(59%) saturate(7496%) hue-rotate(206deg)
    brightness(96%) contrast(101%);
}

.item-icon {
  margin: 0 auto;
}
</style>
