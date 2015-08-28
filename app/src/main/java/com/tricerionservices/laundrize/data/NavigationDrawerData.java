package com.tricerionservices.laundrize.data;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class NavigationDrawerData {

    int menuItemLogo;
    String menuItemName;

    public NavigationDrawerData(int menuItemLogo, String menuItemName) {
        this.menuItemLogo = menuItemLogo;
        this.menuItemName = menuItemName;
    }

    public int getMenuItemLogo() {
        return menuItemLogo;
    }

    public void setMenuItemLogo(int menuItemLogo) {
        this.menuItemLogo = menuItemLogo;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}
