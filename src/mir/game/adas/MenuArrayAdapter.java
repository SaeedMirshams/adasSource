/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            LayoutInflater infl = context.getLayoutInflater();
            LinearLayout result = (LinearLayout) infl.inflate(R.layout.menu_item, null);

            ImageView imageView = (ImageView) result.findViewById(R.id.imageView);
            imageView.setImageResource(values[position].getImageResId());

            TextView textView = (TextView) result.findViewById(R.id.textView);
            textView.setText(values[position].getTitle());

            Utility.applyFont(result);
            return result;
        } catch (Exception ex) {
            ex.toString();
        }
        return null;
    }
}
