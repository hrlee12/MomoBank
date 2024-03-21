import { defineStore } from "pinia";

export const useMenuStore = defineStore({
  id: "menuStore",
  state: () => ({
    menuTitle: "메뉴이름",
    notice: 0,
    sideMenuActive: true,
  }),
  actions: {
    updateMenuTitle(title) {
      this.menuTitle = title;
    },

    updateSideMenuActive(active) {
      this.sideMenuActive = active;
    },
  },
});
