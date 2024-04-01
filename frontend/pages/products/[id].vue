<script setup>
// This file path is /products/:id
const { id } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일
const uri = "http://fakestoreapi.com/products/" + id;

// Fetch the product
// If we use the key parameter, when we fetch, if the id is different, it will auto request for a new data
const { data: product } = await useFetch(uri, { key: id });

definePageMeta({
  layout: "products",
});
</script>

<template>
  <div>
    <p>{{ product.title }}</p>
    <p>{{ product.price }}</p>
    <p>{{ product.id }}</p>
  </div>
</template>

<style lang="scss" scoped></style>
