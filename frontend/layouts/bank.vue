<script setup>
// Define a method to dynamically require images
const getImageUrl = (imageName) => {
  // Note: You might need to adjust the path depending on your project structure
  return "/icon/" + imageName;
};
</script>

<template>
  <div class="layout-container">
    <header>
      <nav>
        <div class="menu">
          <div>
            <img :src="getImageUrl('user-icon.png')" alt="" />
            <NuxtLink to="/" class="font-bold"><h1>엄세현</h1></NuxtLink>
          </div>
          <NuxtLink to="/bank/account-list" class="list-link"
            >전체계좌</NuxtLink
          >
        </div>
        <img :src="getImageUrl('bell-icon.png')" alt="" />
      </nav>
    </header>

    <!-- output the page content -->
    <div class="container">
      <slot />
    </div>

    <footer>
      <ul class="menu">
        <li><NuxtLink to="/">Home</NuxtLink></li>
        <li><NuxtLink to="/bank">Bank</NuxtLink></li>
        <li><NuxtLink to="/products">Products</NuxtLink></li>
      </ul>
    </footer>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.router-link-exact-active {
  color: #12b488;
}

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

      div {
        display: flex;
        margin-right: 4%;
      }

      .list-link {
        border-radius: 20px;
        border: 1px solid $gray-color;
        font-size: 1vw 2vh;
        color: $gray-color;
        margin-top: 0.5vh;
        padding-top: 0.5vh;
        min-width: 80px;
        width: 11vw;
        height: 4.5vh;
        text-align: center;
      }
    }
  }
}

.container {
  display: flex;
  justify-content: center;
  margin-bottom: 15%;
}

footer {
  position: fixed;
  width: 100%;
  bottom: 0%;
  background-color: white;

  .menu {
    display: flex;
    width: 100%;
    height: 10%;
    justify-content: space-evenly;
    padding: 4%;
  }
}
</style>
