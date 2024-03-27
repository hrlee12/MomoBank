<script setup>
definePageMeta({
  layout: "action",
});
</script>

<template>
  <div>
    <div class="agree-container">
      <div class="agree-content">
        <div class="input-content">
          <input type="checkbox" id="total" />
          <label for="total"><h2>전체 동의하기</h2></label>
        </div>
      </div>
      <div class="settings-section">
        <div class="input-content">
          <span>개인 정보 수집 약관</span>
          <span class="arrow">></span>
        </div>
        <ul class="section-list">
          <li>성명</li>
          <li>생년월일</li>
          <li>전화번호</li>
          <li>이메일</li>
          <li>주민등록증</li>
        </ul>
      </div>
      <div class="settings-section">
        <div class="input-content">
          <span>상품 플러스동의</span>
          <span class="arrow">></span>
        </div>
        <ul class="section-list">
          <li>체크카드 개인정보 약관</li>
          <li>현금카드 약관</li>
          <li>통장 금융거래 보고 및 이용에 관한 안내</li>
          <li>마이나스통장 채무 계좌 안내</li>
          <li>개인(신용)정보 수집 이용 동의 제공 동의</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
