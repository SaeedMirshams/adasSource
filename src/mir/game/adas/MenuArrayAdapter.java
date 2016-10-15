/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 *
 * @author 8062439
 */
public class MenuArrayAdapter extends ArrayAdapter<MainMenuItem> {

    private final MainActivity context;
    private final MainMenuItem[] values;

    public MenuArrayAdapter(Context context, MainMenuItem[] values) {
        super(context, R.layout.activity_main, values);
        this.context = (MainActivity) context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LinearLayout result = new LinearLayout(context);
            result.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            result.setPadding(5, 5, 5, 5);
            result.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(context);
            int iconw = Utility.DisplaySize().x / 10;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(iconw, iconw);
            lp.setMargins(5, 3, 20, 2);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(values[position].getImageResId());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            result.addView(imageView);

            TextView textView = new TextView(context);
            textView.setText(values[position].getTitle());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            result.addView(textView);
            Utility.applyFont(result);
            return result;
        } catch (Exception ex) {
            ex.toString();
        }
        return null;
    }
}
