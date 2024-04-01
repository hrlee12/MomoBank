<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleRecommend from "~/components/bank/SimpleRecommend.vue";

const router = useRouter();
const allAccountProducts = ref([
  {
    accountProductList: [
      {
        accountProductId: 1,
        accountProductName: "name",
        bankName: "모모",
        description: "description",
        interestRate: 1.1,
      }, // account
    ],
    accountType: "입출금자유예금",
  }, // product
  {
    accountProductList: [
      {
        accountProductId: 2,
        accountProductName: "name",
        bankName: "모모",
        description: "description",
        interestRate: 1.4,
      },
    ],
    accountType: "입출금자유예금",
    accountType: "정기예금",
  },
  {
    accountProductList: [
      {
        accountProductId: 3,
        accountProductName: "name",
        bankName: "모모",
        description: "description",
        interestRate: 1.2,
      },
    ],
    accountType: "입출금자유예금",
    accountType: "적금",
  },
]);

const accountSelect = (id) => {
  console.log(id);
  router.push(`/bank/account-create/account-agree`);
};

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

definePageMeta({
  layout: "action",
});
</script>

<template>
  <div class="card-container">
    <div class="content">
      <div v-for="(product, index) in allAccountProducts" :key="index">
        <div class="left">{{ product.accountType }}</div>
        <SimpleRecommend
          v-for="(account, index) in product.accountProductList"
          :key="index"
          :id="account.accountProductId"
          :name="account.accountProductName"
          :interest="account.interestRate"
          :imgUrl="getImageUrl('logo-icon.png', 0)"
          :corpName="account.bankName"
          @click="accountSelect(account.accountProductId)"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/card.scss";

.card-container {
  padding: 10% 0;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.content {
  display: flex;
  justify-content: space-between;

  flex-direction: column;
  text-align: center;
}

.left {
  padding-left: 5%;
  color: black;
  font-weight: bold;
  width: 50%;
}
</style>
