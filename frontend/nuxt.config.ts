// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ["@nuxtjs/tailwindcss", "nuxt-swiper", "@pinia/nuxt"],
  plugins: [
    "@/plugins/global-functions.js", // 플러그인 파일의 경로
    "~/plugins/axios.js",
  ],
  pinia: {
    storesDirs: ["./stores/**", "./custom-folder/stores/**"],
  },
  app: {
    head: {
      titleTemplate: "MOMO 뱅크",
      link: [
        // PNG 또는 SVG 아이콘을 사용하는 경우
        { rel: "icon", type: "image/png", href: "/favicon.png" },
      ],
    },
  },
});
