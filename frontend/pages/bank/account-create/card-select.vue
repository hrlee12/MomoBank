<script setup>
import { ref } from "vue";
import { useRouter } from "#vue-router";
import { useBankApi } from "~/api/bank";
import SimpleRecommend from "~/components/bank/SimpleRecommend.vue";

const { getCardRecommendList } = useBankApi();

definePageMeta({
  layout: "action",
});

const cardList = ref([
  {
    id: "2446",
    card_name: "모모 익스프레스 카드",
    corp_name: "하나카드",
    benefits: [
      "국내외가맹점0.7%할인",
      "간편결제1.2%할인",
      "쿠팡/마트/베이커리2%할인",
    ],
    card_img_url:
      "https://api.card-gorilla.com:8080/storage/card/2446/card_img/27741/2446card.png",
  },
  {
    id: "2447",
    card_name: "원더카드 FREE",
    corp_name: "신한카드",
    benefits: [
      "국내외가맹점0.5%할인",
      "간편결제1.8%할인",
      "쿠팡/마트/베이커리2%할인",
    ],
    card_img_url:
      "https://api.card-gorilla.com:8080/storage/card/2446/card_img/27741/2446card.png",
  },
  {
    id: "2448",
    card_name: "원더카드",
    corp_name: "신한카드",
    benefits: [
      "국내외가맹점0.5%할인",
      "간편결제1.8%할인",
      "쿠팡/마트/베이커리2%할인",
    ],
    card_img_url:
      "https://api.card-gorilla.com:8080/storage/card/2446/card_img/27741/2446card.png",
  },
  {
    id: "2449",
    card_name: "원더카드",
    corp_name: "신한카드",
    benefits: [
      "국내외가맹점0.5%할인",
      "간편결제1.8%할인",
      "쿠팡/마트/베이커리2%할인",
    ],
    card_img_url:
      "https://api.card-gorilla.com:8080/storage/card/2446/card_img/27741/2446card.png",
  },
  {
    id: "2440",
    card_name: "원더카드",
    corp_name: "신한카드",
    benefits: [
      "국내외가맹점0.5%할인",
      "간편결제1.8%할인",
      "쿠팡/마트/베이커리2%할인",
    ],
    card_img_url:
      "https://api.card-gorilla.com:8080/storage/card/2446/card_img/27741/2446card.png",
  },
]);

const router = useRouter();
const cardSelect = (id) => {
  console.log(id);
  router.push(`/bank/account-create/card-agree`);
};

// 카드 추천 리스트 조회
const remitStore = useRemitStore();
onMounted(async () => {
  try {
    const response = await getCardRecommendList(remitStore.surveyData);
    console.log(response.data);
    cardList.value = response.data;
  } catch (error) {
    console.log("카드 조회 실패: ", error);
  }
});
</script>

<template>
  <div class="card-container">
    <div class="main-content content">
      <h1>당신을 위한 추천 카드!</h1>
      <img
        :src="`${cardList[0].card_img_url}`"
        alt=""
        @click="cardSelect(cardList[0].id)"
      />
      <div class="shadow"></div>
      <div>
        <p>{{ cardList[0].corp_name }}</p>
        <h2>{{ cardList[0].card_name }}</h2>
        <p
          v-for="(benefit, index) in cardList[0].benefits"
          :key="index"
          class="emphasize"
        >
          {{ benefit }}
        </p>
      </div>
    </div>
    <div class="content">
      <p class="left-aligin">그 외</p>
      <div class="content">
        <div
          v-for="(card, index) in cardList"
          :key="index"
          @click="cardSelect(card.id)"
        >
          <SimpleRecommend
            v-if="index !== 0"
            :id="card.id"
            :name="card.card_name"
            :benefits="card.benefits"
            :imgUrl="card.card_img_url"
            :corpName="card.corp_name"
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

.left-aligin {
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
