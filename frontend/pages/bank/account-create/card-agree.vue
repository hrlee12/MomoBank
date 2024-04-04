<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

definePageMeta({
  layout: "action",
});

const card = {
  id: "2446",
  card_name: "모모 익스프레스 카드",
  corp_name: "하나카드",
};

const agreeContents = ref([
  {
    id: "personalInfoAgree",
    title: "개인 정보 수집 약관",
    contents: ["성명", "생년월일", "전화번호", "이메일", "주민등록증"],
    isOpen: false,
    isChecked: false,
  },
  {
    id: "productPlusAgree",
    title: "상품 플러스 동의",
    contents: [
      "체크카드 개인회원 약관",
      "현금카드 약관",
      "특정 금융거래 보고 및 이용에 관한 안내",
      "마이너스 통장 결제 계좌 안내",
    ],
    isOpen: false,
    isChecked: false,
  },
]);

const toggleMenu = (index) => {
  agreeContents.value[index].isOpen = !agreeContents.value[index].isOpen; // 특정 메뉴 상태 토글
};

const allAgreed = ref(false); // 전체 동의 상태를 관리하는 반응형 데이터
// 전체 동의 체크박스 상태를 토글하는 함수
const toggleAllAgree = () => {
  allAgreed.value ? true : false;
  console.log(allAgreed.value);
  agreeContents.value.forEach((content) => {
    content.isChecked = allAgreed.value;
  });
};

// 개별 체크박스의 상태를 업데이트하는 함수
const updateIndividualAgreement = () => {
  const allChecked = agreeContents.value.every((content) => {
    return content.isChecked; // 첫 번째는 제외하고 나머지 체크
  });

  allAgreed.value = allChecked;
};

const goNext = () => {
  alert("카드가 성공적으로 생성되었습니다.");
  router.push("/bank"); // 로딩 완료 후 card-select 페이지로 이동
};
</script>

<template>
  <div class="agree-container">
    <div class="agreement">
      <div class="content title">
        <h1>{{ card.card_name }} 신청을 위해 약관에 동의해주세요</h1>
      </div>

      <div class="agree-content">
        <div class="content">
          <input
            type="checkbox"
            id="agreeAll"
            v-model="allAgreed"
            @change="toggleAllAgree"
          />
          <h2>전체 동의하기</h2>
        </div>
      </div>
      <div
        v-for="(content, index) in agreeContents"
        :key="index"
        class="agree-content"
      >
        <div class="input-content">
          <div class="content">
            <input
              type="checkbox"
              :id="content.id"
              v-model="content.isChecked"
              @change="updateIndividualAgreement"
            />
            <h2>{{ content.title }}</h2>
          </div>

          <div class="arrow" @click="toggleMenu(index)">
            <img src="/icon/arrow-icon.png" alt="" />
          </div>
        </div>
        <ul v-show="content.isOpen">
          <li v-for="(item, index) in content.contents" :key="index">
            {{ item }}
          </li>
        </ul>
      </div>
    </div>

    <button v-if="!allAgreed" class="second-btn">다음</button>
    <button v-else class="prime-btn" @click="goNext()">다음</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.title {
  padding: 10% 0;
}

.agree-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
}

.agree-content {
  padding: 5% 0;
}

.agree-content + .agree-content {
  border-top: 1px solid $light-gray-color;
}

.input-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding-bottom: 2%;
}

.agreement {
  width: 90%;
  align-self: center;
}

.content {
  display: flex;
  width: 90%;
  min-height: 5vh;
}

.arrow {
  align-items: center;
  width: 10%;
  img {
    rotate: 270deg;
    width: 5vh;
  }
}

input {
  width: 10%;
  margin-right: 5%;
  cursor: pointer;
}

ul {
  list-style-type: disc;
  position: relative;
  padding-inline-start: 1.5em;
}

li,
ul {
  line-height: 2;
}

li {
  color: $gray-color;
}

button {
  margin-bottom: 20px;
  border-radius: 15px;
}
</style>
