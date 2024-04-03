<script setup>
const isEditMode = ref(false);

definePageMeta({
  layout: "groups",
});

const { notedId } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일

// notedId에 해당하는 공지사항 상세 정보 가져오기

// Define a method to dynamically require images
const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const textVal = "우리 이제 일본 여행 간다!! 돈 모았으니 출발!! ";
const fileUrl = "/path/to/example.pdf";
</script>

<template>
  <div class="h-screen bg-white">
    <div class="px-4">
      <div
        class="flex pt-10 text-2xl font-bold border-b-[1px] pb-3 mb-4 border-light-gray-color"
      >
        <div>
          [중요] 중요!! 피드 게시글에 욕설을 자제 부탁드립니다!!!!!!! 유유유
        </div>
      </div>
      <div class="flex justify-between mb-8">
        <div>작성자:김성수</div>
        <div>작성일:2024.3.12 화요일</div>
      </div>
      <div class="px-2 py-2 mb-4 border-2 rounded-xl h-72">
        <textarea
          class="w-full h-full overflow-y-auto whitespace-normal"
          :value="textVal"
          :readonly="!isEditMode"
        ></textarea>
      </div>

      <a
        :href="fileUrl"
        download
        class="flex items-center justify-between border-b-[1px] border-gray-color pb-2 pr-2 pl-2"
      >
        <div class="text-gray-color">Img.png</div>
        <div class="w-7 h-7 text-gray-color">
          <img :src="getImageUrl('download-icon.png', 0)" alt="download-icon" />
        </div>
      </a>
    </div>
    <div class="flex justify-end px-4 mt-4 space-x-4">
      <button
        class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
      >
        수정
      </button>
      <button
        class="px-4 py-2 font-bold text-white rounded-xl bg-negative-color hover:bg-red-700"
      >
        삭제
      </button>
    </div>
  </div>
</template>
