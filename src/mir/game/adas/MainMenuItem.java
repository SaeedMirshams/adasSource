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
    private int action;

    public MainMenuItem(int titleResId, int imageResId, int action) {
        this.title = titleResId;
        this.imageResId = imageResId;
        this.action = action;
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

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
    }

}
