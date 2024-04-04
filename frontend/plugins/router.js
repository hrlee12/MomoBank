export default defineNuxtPlugin((nuxtApp) => {
  const router = useRouter();
  router.beforeEach((to, from, next) => {
    // 여기에 네비게이션 가드 로직 작성

    next();
  });
});
