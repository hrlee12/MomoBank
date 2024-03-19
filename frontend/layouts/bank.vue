<script setup>
import { inject } from "vue";

const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="layout-container">
    <header>
      <nav>
        <div class="menu">
          <NuxtLink to="/" class="font-bold">
            <!-- getImageUrl함수가 존재할 때만 동작 -->
            <img
              :src="getImageUrl ? getImageUrl('user-icon.png', 0) : ''"
              alt=""
            />
            <h1>엄세현</h1></NuxtLink
          >

          <NuxtLink to="/bank/account-list" class="list-link"
            >전체계좌</NuxtLink
          >
        </div>
        <img :src="getImageUrl ? getImageUrl('bell-icon.png', 0) : ''" alt="" />
      </nav>
    </header>

    <!-- output the page content -->
    <div class="container">
      <slot />
    </div>

    <BankFooter />
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

// .router-link-exact-active {
//   color: $primary-color;
// }

h1 {
  padding-top: 0.5vh;
  font-size: 2vw 5vh;
  border-bottom: 1px solid black;
}

header {
  width: 100%;
  padding: 2vh;

  nav {
    display: flex;
    justify-content: center;

    img {
      height: 4.5vh;
    }

    .menu {
      display: flex;
      min-width: 80%;

      a {
        display: flex;
        margin-right: 4%;
        justify-content: center;
      }

      .list-link {
        border-radius: 30px;
        border: 1px solid $gray-color;
        font-size: 1vw 2vh;
        color: $gray-color;
        margin-top: 0.5vh;
        min-width: 80px;
        width: 11vw;
        padding-top: 0.7vh;
        font-size: 2vh;
        height: 4.5vh;
        text-align: center;
      }
    }
  }
}

.container {
  display: flex;
  justify-content: center;
  min-height: 90vh;
}
</style>
