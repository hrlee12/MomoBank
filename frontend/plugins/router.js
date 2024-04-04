import { useRemitStore } from "~/stores/remitStore";

export default defineNuxtPlugin((nuxtApp) => {
  const router = useRouter();
  const remitStore = useRemitStore();
  // router.beforeEach((to, from, next) => {
  //   const currentPath = to.path;
  //   if (currentPath.startsWith("/groups/join")) {
  //     next();
  //   } else {
  //     // 인증되지 않은 사용자가 인증이 필요한 페이지로 접근 시 로그인 페이지로 리다이렉트
  //     if (remitStore.memberId == -1) {
  //       remitStore.memberId = 0;
  //       next("/"); // isAuthenticated()는 사용자 인증 상태를 확인하는 함수
  //     } else next();
  //   }
  // });
});
