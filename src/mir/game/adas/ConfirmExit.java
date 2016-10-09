/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author 8062439
 */
public class ConfirmExit implements OnClickListener, Runnable {

    MainActivity activity;

    public ConfirmExit(MainActivity context) {
        activity = context;
    }

    private void Exit() {
        System.exit(0);
    }

    public void run() {

        //
        LinearLayout customTitle = new LinearLayout(activity);
        customTitle.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtTitle = new TextView(activity);
        txtTitle.setText(R.string.info_attention);

        txtTitle.setWidth(Utility.DisplaySize().x);
        txtTitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        ImageView img = new ImageView(activity);
        img.setImageResource(android.R.drawable.ic_dialog_alert);
        customTitle.addView(img);
        customTitle.addView(txtTitle);

        //
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(R.string.info_attention)
                .setMessage(R.string.ask_exit)
                .setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.no, this)
                .setIcon(android.R.drawable.ic_dialog_alert).setCustomTitle(customTitle).show();

        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTypeface(activity.getDefaultFont());
        textView.setTextSize(activity.getTextViewTextSize());

        android.widget.Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnPositive.setTextSize(activity.getButtonTextSize());
        btnPositive.setTypeface(activity.getDefaultFont());

        android.widget.Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setTextSize(activity.getButtonTextSize());
        btnNegative.setTypeface(activity.getDefaultFont());

    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            activity.returntoMain();
        } else if (which == DialogInterface.BUTTON_POSITIVE) {
            Exit();
        }

    }

}
