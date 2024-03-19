<script setup></script>

<template>
  <div class="account-content">
    <div class="account-item">
      <div class="account-info">
        <h2>저축예금</h2>
        <div class="account-no">
          <p>입출금 xxx-xxxxx-xxxxx</p>
          <p>복사</p>
        </div>
      </div>
      <!-- <KebabMenu /> -->
      <KebabMenu />
    </div>
    <div class="money-content">
      <h1>1,000,000원</h1>

      <button><p>숨김</p></button>
    </div>

    <div class="link-content">
      <NuxtLink to="/bank/history">거래내역</NuxtLink>
      <NuxtLink to="/bank/remit">송금하기</NuxtLink>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.account-container {
  overflow-x: auto; /* 수평 스크롤 활성화 */
  scroll-snap-type: x mandatory; /* X축으로 스냅 활성화 */
  display: flex; /* 슬라이드 항목들을 가로로 배치 */
  -webkit-overflow-scrolling: touch; /* iOS에서 부드러운 스크롤 */
  gap: 20px; /* 슬라이드 사이 간격 추가 */

  .account-content {
    scroll-snap-align: start; /* 스크롤 시 슬라이드 시작점에서 스냅 */
    min-width: 80%; /* 슬라이드 최소 너비 설정, 화면 크기에 따라 조정 가능 */
    min-height: 200px;
    padding: 2vh;

    border: 1px solid #a3a3a3;
    border-radius: 20px;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    transition: transform 0.5s ease-in-out;

    .account-item {
      display: flex;
      justify-content: space-between;
      width: 100%;
      padding: 0 5% 0 5%;
      text-align: left;

      .account-no {
        display: flex;
      }
    }

    .money-content {
      display: flex;
      justify-content: center;

      h1 {
        margin-right: 2%;
      }

      button {
        background-color: $light-gray-color;
        border-radius: 20px;
        min-width: 40px;
        color: $gray-color;
      }
    }

    .link-content {
      display: flex;
      justify-content: space-around;
      border-top: 1px solid $light-gray-color;
      width: 90%;
      align-self: center;
      padding-top: 2vh;

      a {
        color: $primary-color;
        font-weight: bold;
      }
    }
  }
}
</style>
