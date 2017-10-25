package com.cybermatrixsolutions.invoicesolutions.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class NavDrawerItem {

    private boolean showNotify;
    private String title;
    private String count;
    private int imageId;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, String count) {
        this.showNotify = showNotify;
        this.title = title;
        this.count=count;
    }

    public NavDrawerItem(String title, Integer imageId) {
        this.title = title;
        this.title = title;
        this.imageId = imageId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

/*****************************************
*
* @return
*/
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
