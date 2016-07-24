/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excersize.test;

import android.widget.LinearLayout;
import mir.game.adas.MainActivity;

/**
 *
 * @author 8062439
 */
public class FormBase extends LinearLayout {

    protected MainActivity activity;

    public FormBase(MainActivity context) {
        super(context);
        activity = context;
        this.setOrientation(VERTICAL);
    }

}
