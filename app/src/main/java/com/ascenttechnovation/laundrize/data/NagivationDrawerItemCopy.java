package com.ascenttechnovation.laundrize.data;

/**
 * Created by ADMIN on 06-08-2015.
 */
public class NagivationDrawerItemCopy {

    private boolean showNotify;
    private String title;

        public NagivationDrawerItemCopy() {
        }

        public NagivationDrawerItemCopy(boolean showNotify, String title) {
            this.showNotify = showNotify;
            this.title = title;
        }

        public boolean isShowNotify() {
            return showNotify;
        }

        public void setShowNotify(boolean showNotify) {
            this.showNotify = showNotify;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
}

