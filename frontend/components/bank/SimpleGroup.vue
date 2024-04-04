<script setup>
defineProps({
  groupId: Number,
  groupName: String,
  groupMoney: Number,
  state: Boolean,
  delayDate: Number,
  groupDescription: String,
  groupMemberCount: Number,
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <nuxtLink :to="`/groups/${groupId}`" class="content">
    <div class="item">
      <h2>{{ groupName }}</h2>
      <h2>{{ groupMoney.toLocaleString("ko-KR") }}원</h2>
    </div>
    <div class="item right">
      <p v-if="state" class="ok bold">납부 완료</p>
      <p v-else class="not-ok bold">납부 요망</p>
    </div>
    <div class="item">
      <p class="desc">{{ groupDescription }}</p>
      <div class="icon-item">
        <img :src="getImageUrl('user-icon-1.png', 0)" alt="" />
        <p>{{ groupMemberCount }}</p>
      </div>
    </div>
  </nuxtLink>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";

.content {
  padding: 4% 8%;

  .item {
    display: flex;
    justify-content: space-between;
    margin: 2% 0;

    .desc {
      width: 70%;
      max-width: 70vw;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      text-align: left;
    }
  }
}

.icon-item {
  display: flex;
}

.bold {
  font-weight: bold;
}

.ok {
  color: $positive-color;
}

.not-ok {
  color: $negative-color;
}

img {
  height: 3vh;
}

.right {
  justify-content: right !important;
}
</style>
