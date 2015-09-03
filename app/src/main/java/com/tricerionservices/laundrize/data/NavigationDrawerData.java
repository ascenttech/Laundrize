package com.tricerionservices.laundrize.data;

import android.graphics.Bitmap;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class NavigationDrawerData {

    Bitmap menuItemLogo;
//    int menuItemLogo;
    String menuItemName;

    public NavigationDrawerData(Bitmap menuItemLogo, String menuItemName) {
        this.menuItemLogo = menuItemLogo;
        this.menuItemName = menuItemName;
    }

    //    public NavigationDrawerData(int menuItemLogo, String menuItemName) {
//        this.menuItemLogo = menuItemLogo;
//        this.menuItemName = menuItemName;
//    }

    public Bitmap getMenuItemLogo() {
        return menuItemLogo;
    }

    public void setMenuItemLogo(Bitmap menuItemLogo) {
        this.menuItemLogo = menuItemLogo;
    }

    //    public int getMenuItemLogo() {
//        return menuItemLogo;
//    }

//    public void setMenuItemLogo(int menuItemLogo) {
//        this.menuItemLogo = menuItemLogo;
//    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}
