/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

/**
 *
 * @author 8062439
 */
public class MainMenuItem {

    private int title;
    private int imageResId;
    private Runnable view;

    public MainMenuItem(int titleResId, int imageResId, Runnable r) {
        this.view = r;
        this.title = titleResId;
        this.imageResId = imageResId;
    }

    /**
     * @return the title
     */
    public int getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(int title) {
        this.title = title;
    }

    /**
     * @return the imageResId
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * @param imageResId the imageResId to set
     */
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void run() {
        view.run();
    }
}
