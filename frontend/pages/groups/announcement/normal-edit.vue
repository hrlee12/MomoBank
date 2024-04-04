<script setup>
import CompleteModal from "~/components/layout/CompleteModal.vue";

// TODO: 특정 id에 해당하는 글을 수정해야한다. 지금은 일단 디자인만.
definePageMeta({
  layout: "groups",
});

const completeWrite = ref(false);


const goBack = () => {
  window.history.back();
};

const announcement = {
  id: 1,
  title: "5반 5린이들 공지사항 ",
  contents: "내용 내용 내용",
  image: [
    {
      id: 23,
      name: "imgName1",
      url: "asdasd",
    },
    {
      id: 24,
      name: "imgName2",
      url: "zxczxc",
    },
  ],
};

const urlList = ref(announcement.image);

const files = ref([]);

const handleFileSelect = (event) => {
  files.value.push(event.target.files[0]);
};

// TODO : 공지사항 수정 API 요청
function completeEdit() {}

function removeImage(itemId) {
  urlList.value = urlList.value.filter((item) => item.id !== itemId);
}

function removeFileImage(itemName) {
  files.value = files.value.filter((item) => item.name !== itemName);
}

const selectFile = () => {
  document.getElementById("fileInput").click();
};

function completeWriteEdit() {
  completeWrite.value = true;
}
function okButton(changeComplte) {
  completeWrite.value = changeComplte;
}
</script>

<template>
  <div class="h-screen px-6 py-6 bg-white">
    <div class="mb-5 border-b-[1px] border-gray-color h-8">
      <input
        class="w-full"
        v-model="announcement.title"
        type="text"
        placeholder="제목 입력"
      />
    </div>
    <div class="mb-5 border-2 rounded-xl h-72">
      <textarea
        class="w-full h-full overflow-y-auto whitespace-normal"
        v-model="announcement.contents"
      ></textarea>
    </div>
    <div
      v-for="item in urlList"
      :key="item.name"
      class="flex justify-between items-center h-10 border-b-[1px] border-light-gray-color mt-2"
    >
      <div class="text-lg font-semibold text-gray-color">
        {{ item.name }}
      </div>

      <div
        @click="removeImage(item.id)"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>-</div>
      </div>
    </div>
    <div
      v-for="item in files"
      :key="item.A"
      class="flex justify-between items-center h-10 border-b-[1px] border-light-gray-color mt-2"
    >
      <div class="text-lg font-semibold text-gray-color">
        {{ item.name }}
      </div>

      <div
        v-if="files.length === 0"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>+</div>
      </div>
      <div
        v-if="files.length !== 0"
        @click="removeFileImage(item.name)"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>-</div>
      </div>
    </div>
    <div
      class="flex justify-between items-center h-10 border-b-[1px] border-light-gray-color mt-2"
    >
      <div class="text-lg font-semibold text-gray-color">
        새로운 이미지 추가
      </div>
      <input
        type="file"
        hidden
        id="fileInput"
        @change="handleFileSelect"
        accept="image/png, image/jpeg"
      />
      <div
        @click="selectFile"
        class="w-[30px] h-[30px] flex justify-center items-center border-2 border-light-gray-color"
      >
        <div>+</div>
      </div>
    </div>
    <div class="flex justify-between px-6 py-6 pb-16 bg-white">
      <div>
        <button
          @click="goBack"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-negative-color hover:bg-red-700"
        >
          취소
        </button>
      </div>
      <div>
        <button
          @click="completeWriteEdit"
          class="px-4 py-2 font-bold text-white w- rounded-xl bg-main-color hover:bg-blue-700"
        >
          완료
        </button>
      </div>
      <CompleteModal
        v-if="completeWrite === true"
        @ok-button="okButton"
      ></CompleteModal>
    </div>
  </div>
</template>
