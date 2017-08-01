package webtek.cse.uem.biswajit.com.requestresuereduce;

/**
 * Created by Biswajit Paul on 01-04-2017.
 */
public class DrawerItems {
    private boolean showNotify;
    private String title;
    private int imageView;


    public DrawerItems() {
    }

    public DrawerItems(boolean showNotify, String title) {
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

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
