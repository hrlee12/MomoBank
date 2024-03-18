<script setup></script>

<template>
  <div class="layout-container">
    <header class="">
      <nav class="menu">
        <NuxtLink to="/" class="font-bold">Nuxt Dojo</NuxtLink>
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

.container {
  display: flex;
  justify-content: center;
}

footer {
  position: fixed;
  width: 100%;
  bottom: 0%;

  .menu {
    display: flex;
    width: 100%;
    height: 10%;
    justify-content: space-evenly;
    padding: 4%;
  }
}
</style>
