<script setup>
import { ref, onMounted } from "vue";
import axios from 'axios';
import Chart from "chart.js/auto";
import { marked } from 'marked';

const goalBudget = ref(1000000);
const curBudget = ref(200000);
const curYear = ref("2024");
const showGoalBudget = ref(false);
const showSpendCategory = ref(false);
const months = ref([]);
const selectedMonth = ref("");


const reportAndBestMember = ref({
  "groupInfo": null,
  "reportYear": 2024,
  "reportMonth": 3,
  "content": "# 1. 회계\n\n- **불완전 납부**: 김철수님이 15,000원을 미납하셨습니다. 다음 회차에는 납부를 잊지 말아주세요.\n- **초과 입금**: 이영희님께서는 30,000원을 초과 납부하셨습니다. 너무 감사합니다! 모임의 발전에 큰 도움이 됩니다.\n- **완납**: 홍길동님은 물론이고 모임비를 제때 납부하셨습니다. 감사합니다!\n\n# 2. 게시판 활동\n\n- '**함께 본 시간을 달리는 소녀**': 작성자 홍길동님, 좋아요 120개와 댓글 2개라는 큰 사랑을 받았습니다. 영화에 대한 세심한 후기와 그날의 모임에 대한 이야기가 독자들의 공감을 얻었네요.\n- '**오늘의 영화 후기**': 작성자 김철수님, 좋아요 95개, 댓글 2개를 얻으셨습니다. 영화에 대한 깊이 있는 생각을 공유해주셔서 감사합니다.\n- '**카페에서의 작은 모임**': 작성자 이영희님, 좋아요 110개, 댓글 2개를 얻었습니다. 모임 전의 모습을 공유해주셔서 더욱 풍성해진 회원님들의 이야기를 만나보았습니다.\n",
  "createdAt": null,
  "bestMember": {
    "bestMember": {
      "id": 1,
      "name": "홍길동"
    },
    "reason": "홍길동님은 모임비를 제때 납부하고 게시판 활동에서 큰 사랑을 받아 베스트 멤버로 선정되었습니다."
  }
})

const reportData = ref({ recommendations: [] });

async function fetchReportData() {
  try {
    const response = await axios.get('https://j10a505.p.ssafy.io/api/community/reports/recommend-next-activity?yearMonth=2024.03');
    // API 호출 결과로 받은 데이터를 reportData의 recommendations에 할당
    reportData.value.recommendations = response.data;
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
    // 에러 핸들링: 필요에 따라 적절한 사용자 경험을 제공하기 위한 코드를 추가할 수 있습니다.
  }
}

fetchReportData();


console.log(reportAndBestMember.value.bestMember.bestMember.name)
console.log(reportAndBestMember.value.bestMember.reason)

const convertedContent = ref(marked(reportAndBestMember.value.content));


// 목표 예산
const goalBudgetReport = () => {
  showGoalBudget.value = !showGoalBudget.value;
};

onMounted(() => {
  const now = new Date();
  for (let i = 4; i >= 0; i--) { // 최근 5개월을 포함
    const month = new Date(now.getFullYear(), now.getMonth() - i, 1);
    months.value.push(`${month.getFullYear()}년 ${month.getMonth() + 1}월`);
  }
  selectedMonth.value = months.value[0]; // 기본적으로 가장 최근 달을 선택



  const data = {
    labels: [curYear.value + "년 예산 리포트"],
    datasets: [
      {
        label: "목표 예산",
        data: [goalBudget.value],
        backgroundColor: "rgba(255, 99, 132, 0.2)",
        borderColor: "rgba(255, 99, 132, 1)",
        borderWidth: 1,
      },
      {
        label: "현재 모인 예산",
        data: [curBudget.value],
        backgroundColor: "rgba(54, 162, 235, 0.2)",
        borderColor: "rgba(54, 162, 235, 1)",
        borderWidth: 1,
      },
    ],
  };
  const config = {
    type: "bar",
    data: data,
    options: {
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    },
  };

  new Chart(document.getElementById("budgetChart"), config);
});

const goalSpendCategory = () => {
  showSpendCategory.value = !showSpendCategory.value;
};

const tab = ref("statusReport");

function selectTab(selectedTab) {
  tab.value = selectedTab;

  if (tab.value === "feed") {
  } else if (tab.value === "myInfo") {
  }
}

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};


definePageMeta({
  layout: "groups",
});
</script>

<template>
  <div class="h-screen bg-white">
    <!-- 모임명 -->
    <div class="flex flex-col items-center justify-center h-20">
      <div class="text-2xl font-bold">5반 5린이들</div>
    </div>

    <!-- 연간, 월별 -->
    <!-- 게시글, 정보 탭-->
    <div class="flex justify-center mt-4">
      <button class="w-1/2 pb-2 font-bold border-b-[1px] border-main-color" :class="tab === 'statusReport'
        ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
        : 'w-1/2 font-bold border-transparent'
        " @click="selectTab('statusReport')">
        상태
      </button>
      <button class="w-1/2 pb-2 font-bold border-b-[1px] border-main-color" :class="tab === 'monthReport'
        ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
        : 'w-1/2 font-bold border-transparent'
        " @click="selectTab('monthReport')">
        월간 리포트
      </button>
    </div>

    <!-- 연간 or 원별 데이터 출력-->
    <div v-if="tab === 'statusReport'" class="container px-2 pt-4 mx-auto bg-white">
      <!-- 년도 선택 드롭박스 -->
      <div class="flex justify-between">
        <div></div>
        <div class="flex items-center">
          <div class="">2024년</div>
          <div class="pr-2">
            <img class="h-5 rotate-180 opacity-50 w-7" :src="getImageUrl('arrow-icon.png', 0)" alt="arrow-icon" />
          </div>
        </div>
      </div>
      <div v-show="showGoalBudget === false">
        <!-- 예산 모집 상태 소제목 -->
        <div class="flex items-center cursor-pointer" @click="goalBudgetReport()">
          <div class="font-bold">예산 모집 상태</div>
          <div class="flex items-center rotate-180">
            <img class="h-5 opacity-50 w-7" :src="getImageUrl('arrow-icon.png', 0)" alt="arrow2-icon" />
          </div>
        </div>
      </div>
      <div v-show="showGoalBudget === true">
        <!-- 예산 모집 상태 소제목 -->
        <div class="flex items-center cursor-pointer" @click="goalBudgetReport()">
          <div class="font-bold">예산 모집 상태</div>
          <div class="flex items-center -rotate-90">
            <img class="h-5 opacity-50 w-7" :src="getImageUrl('arrow-icon.png', 0)" alt="arrow2-icon" />
          </div>
        </div>
        <!-- 그래프 보여주고(막대그래프) -->
        <div class="mt-4">
          <canvas id="budgetChart"></canvas>
        </div>
        <!-- 예산 상세 정보 -->
        <div class="text-sm font-semibold">
          <div>목표 예산: {{ goalBudget }}</div>
          <div>제출한 예산: {{ curBudget }}</div>
          <div>달성률: {{ (curBudget / goalBudget) * 100 }}%</div>
        </div>
      </div>
    </div>


    <div v-if="tab === 'monthReport'" class="container px-2 pt-4 mx-auto bg-white">
      <!-- 년도 선택 드롭박스 -->
      <div class="flex justify-between">
        <div></div>
        <div class="flex items-center mr-5">
          <select v-model="selectedMonth" class="text-black">
            <option v-for="month in months" :value="month" :key="month">{{ month }}</option>
          </select>
        </div>
      </div>

      <div style="margin-top: -40px;">
        <h1 class="stylish-title">베스트 멤버</h1>
        <p>회비 납부 및 활동 참여도를 기반으로 베스트 멤버를 선정합니다.</p>



        <div class="image-container">
          <div class="centered-text">{{ reportAndBestMember.bestMember.bestMember.name }}</div>
        </div>


        <div class="best-member">
          <img src="/images/good.png" alt="" class="icon">
          <div class="content">
            <h3>{{ reportAndBestMember.bestMember.bestMember.name }}님 축하드립니다!</h3>
            <p>{{ reportAndBestMember.bestMember.reason }}</p>
          </div>
        </div>






        <h1 class="stylish-title">이런 활동은 어떨까요?</h1>
        <p class="description">AI가 여러분의 활동을 기반으로 다음 활동을 추천해줬어요.</p>


        <div class="recommendations-container">
          <div class="recommendation-item" v-for="(recommendation, index) in reportData.recommendations" :key="index">
            <!-- 아이콘 -->

            <!-- 추천과 이유 텍스트 -->
            <div class="recommend-container">
              <!-- https://www.flaticon.com/kr/free-icon/messaging-bot_15206422?k=1711519065170&sign-up=google -->
              <img src="/icon/ai.png" class="recommend-icon" alt="Icon">
              <div class="recommend-details">
                <div class="recommend-title" style="font-size: 30px;">{{ recommendation.recommend }}</div>
                <div class="recommend-reason">{{ recommendation.reason }}</div>
              </div>
            </div>
          </div>
        </div>

      </div>

      <h1 class="stylish-title">요약</h1>

      <div v-html="convertedContent" class="makdown-crontent"></div>






    </div>
  </div>
</template>




<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap');

.recommendations-container {
  display: flex;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  padding: 5px;
  scrollbar-width: none;
}

.recommendations-container::-webkit-scrollbar {
  display: none;
}

.recommendation-item {
  display: flex;
  align-items: center;
  flex: none;
  width: 90%;
  margin-right: 20px;
  background: #333;
  color: white;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.recommendation-item:last-child {
  margin-right: 0;
}

.recommend-icon {
  width: 90px;
  height: 90px;
  margin-right: 15px;
  flex-shrink: 0;
}

.recommend-container {
  display: flex;
  align-items: center;
}

.recommend-details {
  display: flex;
  flex-direction: column;
}








.stylish-title {
  margin-top: 60px;
  margin-bottom: 20px;
  font-size: 25px;
  color: #333;
  text-align: center;
  position: relative;
  font-weight: bold;
}

.stylish-title::before,
.stylish-title::after {
  content: '';
  position: absolute;
  bottom: -10px;
  width: 50px;
  height: 4px;
  background-color: #007BFF;
}

.stylish-title::before {
  left: 50%;
  transform: translateX(-60%);
}

.stylish-title::after {
  right: 50%;
  transform: translateX(60%);
}

.description {
  text-align: center;
  color: #666;
  font-size: 18px;
  max-width: 80%;
  margin: 0 auto 30px;
  line-height: 1.6;
}






.best-member {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
}

.best-member .icon {
  width: 50px;
  margin-right: 20px;
}

.best-member .content h3 {
  margin: 0;
  color: #007bff;
}

.best-member .content p {
  margin: 5px 0 0;
  color: #333;
}

.makdown-crontent {
  padding-bottom: 200px;
  margin-left: 7.5%;
  width: 85%;
}

.image-container {
  position: relative;
  text-align: center;
  color: white;
  width: 100%;
  padding-top: 100%;
  margin: 30px 0 30px 0;
  background-image: url('/images/best-member.png');
  background-size: cover;
  background-position: center;
}



.centered-text {
  color: #000;
  position: absolute;
  top: 44%;
  left: 52%;
  font-family: 'Nanum Myeongjo', serif;
  font-weight: 900;
  transform: translate(-50%, -50%);
  font-size: 30px;
}
</style>
