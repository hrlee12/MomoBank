<script setup>
const { id } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일
definePageMeta({
  layout: "bank",
});

// 받아온 id로 account-detail과 get-transfer api호출

const account = ref({
  accountId: 0,
  productType: "MOMO뱅크",
  productName: "저축은행",
  accountNumber: "123-1234-12345",
  balance: 1000000,
});
</script>

<template>
  <div>history</div>
</template>

<style lang="scss" scoped></style>
