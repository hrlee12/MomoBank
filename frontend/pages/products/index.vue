<script setup>
// Index vue inside a sub folder is a root path of the sub folder
// This index path is /products

// This loads the layout name products
definePageMeta({
  layout: "products",
});

// Fetch the products
const { data: products } = await useFetch("https://fakestoreapi.com/products");
</script>

<template>
  <div>
    <div class="grid grid-cols-4 gap-5">
      <div v-for="item in products" :key="item">
        <NuxtLink :to="`/products/${item.id}`"> {{ item.title }}</NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
