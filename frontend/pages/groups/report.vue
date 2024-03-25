<script setup>
import { ref, onMounted } from "vue";
import Chart from "chart.js/auto";

const goalBudget = ref(1000000);
const curBudget = ref(200000);
const curYear = ref("2024");
const showGoalBudget = ref(false);
const showSpendCategory = ref(false);

// 목표 예산
const goalBudgetReport = () => {
  showGoalBudget.value = !showGoalBudget.value;
};

onMounted(() => {
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

const tab = ref("yearReport");

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
      <button
        class="w-1/2 pb-2 font-bold border-b-[1px] border-main-color"
        :class="
          tab === 'yearReport'
            ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
            : 'w-1/2 font-bold border-transparent'
        "
        @click="selectTab('yearReport')"
      >
        연간
      </button>
      <button
        class="w-1/2 pb-2 font-bold border-b-[1px] border-main-color"
        :class="
          tab === 'monthReport'
            ? 'w-1/2 pb-2 font-bold border-b-[1px] border-main-color'
            : 'w-1/2 font-bold border-transparent'
        "
        @click="selectTab('monthReport')"
      >
        월별
      </button>
    </div>

    <!-- 연간 or 원별 데이터 출력-->
    <div
      v-if="tab === 'yearReport'"
      class="container px-2 pt-4 mx-auto bg-white"
    >
      <!-- 년도 선택 드롭박스 -->
      <div class="flex justify-between">
        <div></div>
        <div class="flex items-center">
          <div class="">2024년</div>
          <div class="pr-2">
            <img
              class="h-5 rotate-180 opacity-50 w-7"
              :src="getImageUrl('arrow-icon.png', 0)"
              alt="arrow-icon"
            />
          </div>
        </div>
      </div>
      <div v-show="showGoalBudget === false">
        <!-- 예산 모집 상태 소제목 -->
        <div
          class="flex items-center cursor-pointer"
          @click="goalBudgetReport()"
        >
          <div class="font-bold">예산 모집 상태</div>
          <div class="flex items-center rotate-180">
            <img
              class="h-5 opacity-50 w-7"
              :src="getImageUrl('arrow-icon.png', 0)"
              alt="arrow2-icon"
            />
          </div>
        </div>
      </div>
      <div v-show="showGoalBudget === true">
        <!-- 예산 모집 상태 소제목 -->
        <div
          class="flex items-center cursor-pointer"
          @click="goalBudgetReport()"
        >
          <div class="font-bold">예산 모집 상태</div>
          <div class="flex items-center -rotate-90">
            <img
              class="h-5 opacity-50 w-7"
              :src="getImageUrl('arrow-icon.png', 0)"
              alt="arrow2-icon"
            />
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
  </div>
</template>

<style></style>
