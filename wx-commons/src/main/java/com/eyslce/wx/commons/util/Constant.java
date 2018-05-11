package com.eyslce.wx.commons.util;

public class Constant {
    public final static String firstMenuName = "生活助手";
    public final static String secondMenuName = "";
    public final static String thirdMenuName = "";


    public enum Menu {
        Delivery("delivery", "查询快递");

        private String key;
        private String name;

        Menu(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public static Menu getMenu(String key) {
            for (Menu menu : Menu.values()) {
                if (menu.key.equals(key)) {
                    return menu;
                }
            }
            return null;
        }
    }

}
