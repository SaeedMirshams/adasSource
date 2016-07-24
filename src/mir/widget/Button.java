/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.widget;

import android.content.Context;
import mir.game.adas.MainActivity;

/**
 *
 * @author 8062439
 */
public class Button extends android.widget.Button{

    public Button(Context context) {
        super(context);
        MainActivity a=(MainActivity)context;
        this.setTypeface(a.getDefaultFont());
        this.setTextSize(a.getButtonTextSize());
    }
    
}
