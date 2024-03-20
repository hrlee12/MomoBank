<script setup>
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const menuItems = [
  { name: "메인", path: "/bank", icon: "logo-icon.png" },
  { name: "전체계좌", path: "/bank/account-list", icon: "card-icon.png" },
  { name: "프로필", path: "/bank/profile", icon: "user-icon.png" },
  { name: "모임", path: "/meetings", icon: "main2-icon.png" },
  { name: "계좌 개설", path: "/bank/remit", icon: "add-icon.png" },
];
</script>

<template>
  <footer>
    <ul class="menu">
      <li v-for="(item, index) in menuItems" :key="index">
        <NuxtLink :to="item.path">
          <img :src="getImageUrl ? getImageUrl(item.icon, 0) : ''" alt="" />
          <span>{{ item.name }}</span>
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

footer {
  position: fixed;
  width: 100%;
  min-height: 70px;
  bottom: 0;
  background-color: white;
  vertical-align: middle;
  padding: 1.5vh 1.5vh 1vh 1.5vh;

  .menu {
    display: flex;
    width: 100%;
    justify-content: space-around;

    li {
      display: flex;
      justify-content: center;
      text-align: center;
      font-size: 13px;

      a {
        display: flex;
        flex-direction: column;

        img {
          width: 7vw;
          height: 4vh;
          object-fit: contain;
          align-self: center;
        }
      }
    }
  }
}
</style>
