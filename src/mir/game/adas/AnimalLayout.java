/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import mir.widget.TextView;

/**
 *
 * @author 8062439
 */
public class AnimalLayout extends LinearLayout implements View.OnClickListener {

    TextView englishTextView;
    TextView farsiTextView;
    ImageView image;

    public void setImage(int resId) {
        image.setImageResource(resId);
    }

    public void setImage(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }

    public void setImage(byte[] bytes) {
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        image.setImageBitmap(bmp);

    }

    public AnimalLayout(Context ctx) {
        super(ctx);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams prm = new ActionMenuView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(prm);

        image = new ImageView(getContext());
        image.requestLayout();
        image.setImageResource(R.drawable.adas1);
        image.setOnClickListener(this);

        Point p = Utility.DisplaySize();
        int w = p.x;
        if (p.x > p.y) {
            w = p.y;
        }
        int m=w/6;
        w *= (2.0 / 3.0);
        prm = new ActionMenuView.LayoutParams(w, w);
        prm.setMargins(m,0,0,0);
        image.setLayoutParams(prm);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(image);

        englishTextView = new TextView(getContext());
        englishTextView.setText("FileName");
        englishTextView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        this.addView(englishTextView);

        farsiTextView = new TextView(getContext());
        farsiTextView.setText("نام");
        farsiTextView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        this.addView(farsiTextView);

    }

    public void onClick(View v) {
        if (v == image) {
            Utility.play();
        }
    }

}
