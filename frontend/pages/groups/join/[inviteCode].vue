<script setup>
const inviteCode = useRoute().params;
const router = useRouter();

const groupStore = useGroupStore();

//TODO 해당 인원 모임원에 추가 시켜줘야함. 그건 로그인하면서 처리해줘야할듯.

if (inviteCode !== null) {
  // console.log(inviteCode.inviteCode.slice(0, 1));
  groupStore.inviteCode = inviteCode.inviteCode;
  groupStore.updateInviteGroupId(inviteCode.inviteCode.slice(0, 1));
  groupStore.updateInviteStatus(true); // 초대코드를 받은 상태임
  router.push("/user");
} else {
  alert("해당 초대 코드가 존재하지 않습니다!");
}
</script>

<template>
  <div></div>
</template>

<style></style>
