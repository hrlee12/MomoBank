<script setup>
definePageMeta({
  layout: "groups",
});

const files = ref([]);

const handleFileSelect = (event) => {
  const selectedFiles = event.target.files;
  for (let file of selectedFiles) {
    const reader = new FileReader();
    reader.onload = (e) => {
      files.value.push(e.target.result);
    };
    reader.readAsDataURL(file);
  }
};

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const selectFile = () => {
  document.getElementById("fileInput").click();
};

// TODO : 피드 게시글 작성 API 호출 코드 추가해야함.

const contents = ref("");

const adjustHeight = (event) => {
  const textarea = event.target;
  textarea.style.height = "auto"; // 높이를 초기화
  textarea.style.height = textarea.scrollHeight + "px"; // scrollHeight를 사용하여 높이 조정
};

function removeImage(fileUrl) {
  files.value = files.value.filter((item) => item !== fileUrl);
}

// TODO : 게시 버튼을 클릭했을 때 선택된 이미지가 없으면 게시가 안되어야함.
</script>

<template>
  <div class="relative">
    <div class="absolute top-[-2.35rem] right-7 text-main-color font-bold">
      게시
    </div>
  </div>
  <div class="h-screen bg-white">
    <div v-if="files.length !== 0" class="flex flex-col pt-4">
      <div class="px-4 border-b-[1px] border-light-gray-color">
        <textarea
          v-model="contents"
          @input="adjustHeight"
          name="contents"
          id="contents"
          placeholder="이 사진에 대해 이야기해주세요..."
          class="w-full overflow-hidden text-base border-none resize-none"
        ></textarea>
      </div>
      <div v-for="fileUrl in files" :key="fileUrl" class="bg-white">
        <div class="relative">
          <img
            class="absolute top-0 right-0 w-7 h-7"
            :src="getImageUrl('x-icon.png', 0)"
            alt=""
            @click="removeImage(fileUrl)"
          />
        </div>

        <img class="w-full h-auto mb-1" :src="fileUrl" alt="" />
      </div>
      <div @click="selectFile" class="flex justify-center pt-2 pb-20 bg-white">
        <img
          :src="getImageUrl('picture-plus-icon.png', 0)"
          alt="picture-plus-icon"
          class="w-8 h-8 mr-2"
        />
        <div class="text-xl font-semibold text-main-color">더 추가</div>
      </div>
    </div>
    <input
      type="file"
      hidden
      id="fileInput"
      @change="handleFileSelect"
      multiple
      accept="image/png, image/jpeg"
    />

    <div
      v-if="files.length === 0"
      @click="selectFile"
      class="h-40 mx-auto w-60"
    >
      <div>
        <img
          class="opacity-50"
          :src="getImageUrl('picture-icon.png', 0)"
          alt="picture-icon"
        />
      </div>
      <div class="text-3xl font-bold text-center text-gray-color">
        사진/동영상
      </div>
    </div>
  </div>
</template>
