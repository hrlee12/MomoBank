import { defineStore } from "pinia";

export const useMenuStore = defineStore({
  id: "menuStore",
  state: () => ({
    menuTitle: "메뉴이름",
    notice: 0,
  }),
  actions: {
    updateMenuTitle(title) {
      this.menuTitle = title;
    },
  },
});
