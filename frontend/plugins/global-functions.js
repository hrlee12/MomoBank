// plugins/global-functions.js
export default defineNuxtPlugin((nuxtApp) => {
  // 이미지 동적 할당 함수를 올바르게 정의
  const getImageUrl = (imageName, idx) => {
    if (idx == 0) return "/icon/" + imageName;
    else if (idx === 1) return "/images/" + imageName;
    else console.log("Image code error");
  };

  // 다른 전역 함수를 여기에 정의할 수 있습니다.
  const anotherGlobalFunction = () => {
    // 함수 로직
    console.log("This is another global function.");
  };

  // 모든 전역 함수를 하나의 객체에 넣어 제공
  nuxtApp.provide("getImageUrl", getImageUrl);
});
