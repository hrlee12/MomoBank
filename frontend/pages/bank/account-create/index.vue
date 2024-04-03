<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleRecommend from "~/components/bank/SimpleRecommend.vue";
import BankFooter from "~/components/layout/BankFooter.vue";
import { useBankApi } from "~/api/bank";

const router = useRouter();
const { getBankAccountTypeList } = useBankApi();
const allAccountProducts = ref([
  // {
  //   accountProductList: [
  //     {
  //       accountProductId: 1,
  //       accountProductName: "name",
  //       bankName: "모모",
  //       description: "description",
  //       interestRate: 1.1,
  //     }, // account
  //   ],
  //   accountType: "입출금자유예금",
  // }, // product
]);

import { useRemitStore } from "~/stores/remitStore";
const remitStore = useRemitStore();

const accountSelect = (id) => {
  console.log(id);
  remitStore.createBankAccountProductId = id;
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

const requestCreatableBankAccountTypeList = async () => {
  try {
    const response = await getBankAccountTypeList();
    console.log(
      "생성 가능한 계좌 목록 조회 성공: ",
      response.data.data.allAccountProducts
    );
    allAccountProducts.value = response.data.data.allAccountProducts;
  } catch (error) {
    console.log("생성 가능한 계좌 목록 조회 실패: ", error);
  }
};

onMounted(() => {
  requestCreatableBankAccountTypeList();
});
</script>

<template>
  <div class="card-container">
    <div class="content">
      <div
        v-for="(product, index) in allAccountProducts"
        :key="index"
        class="gap"
      >
        <div class="left">{{ product.accountType }}</div>
        <SimpleRecommend
          v-for="(account, index) in product.accountProductList"
          :key="index"
          :id="account.accountProductId"
          :name="account.accountPRoductName"
          :description="account.description"
          :interest="account.interestRate"
          :imgUrl="getImageUrl('logo-icon.png', 0)"
          :corpName="account.bankName"
          @click="accountSelect(account.accountProductId)"
        />
      </div>
    </div>
  </div>
  <BankFooter />
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/card.scss";

.card-container {
  padding-bottom: 15%;
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

.gap {
  padding: 3% 0;
}
</style>
