<script setup></script>

<template>
  <div>
    <header class="shadow=sm bg-white">
      <nav class="container mx-auto p-4">
        <NuxtLink to="/" class="font-bold">Nuxt Dojo</NuxtLink>
      </nav>
    </header>

    <!-- output the page content -->
    <div class="container mx-auto p-4">
      <slot />
    </div>

    <footer class="container mx-auto p-4 flex justify-between">
      <ul class="flex gap-4">
        <li><NuxtLink to="/">Home</NuxtLink></li>
        <li><NuxtLink to="/about">About</NuxtLink></li>
        <li><NuxtLink to="/products">Products</NuxtLink></li>
      </ul>
    </footer>
  </div>
</template>

<style lang="scss" scoped>
.router-link-exact-active {
  color: #12b488;
}
</style>
