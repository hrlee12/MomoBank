<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleRecommend from "~/components/bank/SimpleRecommend.vue";

const router = useRouter();
const imgUrl = "~/icon/logo-icon.png";
const allAccountProducts = ref([
  {
    accountProductList: [
      {
        accountProductId: 1,
        accountProductName: "name",
        bankName: "모모",
        description: "description",
        interestRate: 1.1,
      },
    ],
    accountType: "입출금자유예금",
  },
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

const cardSelect = (id) => {
  console.log(id);
  router.push(`/bank/account-create/agree`);
};

definePageMeta({
  layout: "action",
});
</script>

<template>
  <div class="card-container">
    <div class="content">
      <p class="left">그 외</p>
      <div class="content">
        <div
          v-for="(product, index) in allAccountProducts"
          :key="index"
          @click="cardSelect(card.id)"
        >
          <div class="left">{{ product.accountType }}</div>
          <SimpleRecommend
            :id="product.accountProductList.accountProductId"
            :name="product.accountProductList.accountProductId"
            :benefits="product.accountProductList.interestRate"
            :imgUrl="imgUrl"
            :corpName="product.accountProductList.bankName"
          />
        </div>
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

  img {
    rotate: 70deg;
    width: 25vh;
    height: 15vh;
    align-self: center;
    margin: 10%;
    /* 이미지의 회전 기준점을 중심으로 설정 */
    transform-origin: center;
    /* Y축을 기준으로 회전하는 애니메이션 적용 */
    animation: rotateAroundYAxis 10s infinite linear;
  }

  .shadow {
    width: 30vh; // 그림자의 너비, 이미지 너비에 맞게 조정
    height: 15px; // 그림자의 높이
    background: rgba(0, 0, 0, 0.15); // 그림자 색상과 투명도
    border-radius: 50%; // 그림자의 끝을 둥글게
    filter: blur(3px); // 그림자의 흐림 효과
    align-self: center;
  }
}

.main-content {
  min-height: 55vh;
  margin: 5% 0;

  h1 {
    padding-bottom: 5%;
  }
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
}

@keyframes rotateAroundYAxis {
  from {
    transform: rotateX(0deg) rotateY(0deg);
  }
  to {
    transform: rotateX(360deg) rotateY(0deg);
  }
}
</style>
