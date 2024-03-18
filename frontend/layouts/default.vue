<script setup></script>

<template>
  <div>
    <header class="shadow-sm bg-white">
      <nav class="container mx-atuo p-4 flex justify-between">
        <NuxtLink to="/" class="font-bold">Nuxt Dojo</NuxtLink>
        <ul class="flex gap-4">
          <!-- Use NuxtLink instead of anchor tag -->
          <!-- anchor tag makes a fresh requset to the server everytime
                Which means it doesn't work as a SPA -->
          <!-- NuxtLink in the other hand, intercepts the request to the server
                and swap out the components which means it's quicker 
                Also, NuxtLink makes its anchor tag have its unique class
                such as router-link-active and router-link-exact-active those you can
                design it specifically when that router is active-->
          <li><NuxtLink to="/">Home</NuxtLink></li>
          <li><NuxtLink to="/bank">Bank</NuxtLink></li>
          <li><NuxtLink to="/products" class="btn">Products</NuxtLink></li>
        </ul>
      </nav>
    </header>

    <!-- output the page content -->
    <div class="container mx-auto p-4">
      <slot />
    </div>
  </div>

  <footer class="shadow-sm bg-white">
    <nav class="container mx-atuo p-4 flex justify-between">
      <NuxtLink to="/" class="font-bold">Nuxt Dojo</NuxtLink>
      <ul class="flex gap-4">
        <!-- Use NuxtLink instead of anchor tag -->
        <!-- anchor tag makes a fresh requset to the server everytime
                Which means it doesn't work as a SPA -->
        <!-- NuxtLink in the other hand, intercepts the request to the server
                and swap out the components which means it's quicker 
                Also, NuxtLink makes its anchor tag have its unique class
                such as router-link-active and router-link-exact-active those you can
                design it specifically when that router is active-->
        <li><NuxtLink to="/">Home</NuxtLink></li>
        <li><NuxtLink to="/bank">Bank</NuxtLink></li>
        <li><NuxtLink to="/products" class="btn">Products</NuxtLink></li>
      </ul>
    </nav>
  </footer>
</template>

<style lang="scss" scoped>
.router-link-exact-active {
  color: #12b488;
}

footer {
  position: fixed;
  width: 100%;
  bottom: 0%;
}
</style>
