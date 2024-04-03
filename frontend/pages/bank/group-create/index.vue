<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

definePageMeta({
  layout: "action",
});

const groupName = ref("");
const groupDesc = ref("");

const router = useRouter();

const groupStore = useGroupStore();

const goNext = () => {
  groupStore.createGroupName(groupName);
  groupStore.createGroupDesc(groupDesc);
  router.push(`/bank/group-create/account-select/`);
};
</script>

<template>
  <div class="input-container">
    <div class="input-content">
      <div class="input-item">
        <label for="groupName"><h1>모임명</h1></label
        ><input
          type="text"
          id="groupName"
          v-model="groupName"
          placeholder="ex) 모모"
        />
      </div>
      <div class="input-item">
        <label for="groupDesc"><h1>모임설명</h1></label
        ><input
          type="text"
          id="groupDesc"
          v-model="groupDesc"
          placeholder="한 줄 요약 설명"
        />
      </div>
    </div>
    <div class="btn-container">
      <button v-if="groupName == '' || groupDesc == ''" class="second-btn">
        다음
      </button>
      <button v-else class="prime-btn" @click="goNext()">다음</button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.prime-btn,
.second-btn {
  border-radius: 15px !important;
}

.input-container {
  height: 90%;
  width: 95%;
}
</style>
