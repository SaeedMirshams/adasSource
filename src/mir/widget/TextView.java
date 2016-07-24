/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import mir.game.adas.MainActivity;
import mir.game.adas.R;

/**
 *
 * @author 8062439
 */
public class TextView extends android.widget.TextView {

    public TextView(Context context) {
        super(context);
        MainActivity a = (MainActivity) context;
        this.setTypeface(a.getDefaultFont());
        this.setTextSize(a.getTextViewTextSize());

    }

}
