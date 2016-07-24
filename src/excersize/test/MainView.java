/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excersize.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.Gravity;
import android.view.View;
import mir.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import mir.game.adas.R;

/**
 *
 * @author 8062439
 */
public class MainView extends LinearLayout implements View.OnClickListener, OnCompletionListener {

    public MainView(Context context) {
        super(context);
        InitializeFrames();
      //  PlayNext();
    }
    MediaPlayer mp;
    Button play;
    Button pause;
    Button previous;
    Button next;
    Button exit;
    TextView tv;
    TextView ftv;
    ImageView img;
    int currentindex = 0;

    private void InitializeFrames() {

        this.setOrientation(LinearLayout.VERTICAL);

        previous = new Button(getContext());
        previous.setText("previous");
        this.addView(previous);
        previous.setOnClickListener(this);

        /*pause = new Button(getContext());
         pause.setText("pause");
         this.addView(pause);
         pause.setOnClickListener(this);

         play = new Button(getContext());
         play.setText("play");
         play.setOnClickListener(this);
         this.addView(play);*/
        next = new Button(getContext());
        next.setText("next");
        next.setOnClickListener(this);
        this.addView(next);

        exit = new Button(getContext());
        exit.setText("exit");
        exit.setOnClickListener(this);

        img = new ImageView(getContext());
        img.requestLayout();
        //img.getLayoutParams().height=200;
        this.addView(img);

        tv = new TextView(getContext());
        tv.setText("FileName");
        tv.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        this.addView(tv);

        ftv = new TextView(getContext());
        ftv.setText("نام");
        ftv.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        this.addView(ftv);

        this.addView(exit);

    }

    public void onClick(View v) {
        try {
            if (v == next) {
              //  PlayNext();
            } else if (v == previous) {
               // PlayPrev();
            } else if (v == play) {
                Play();
            } else if (v == pause) {
                Pause();
            } else if (v == exit) {
                Exit();

            }
        } catch (IllegalStateException ex) {
            AlertDialog.Builder a = new AlertDialog.Builder(getContext());
            a.setMessage(ex.getMessage());
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog  
                }
            });
            a.create().show();
        }

    }

    private void Pause() throws IllegalStateException {
        try {
            if (mp != null) {
                mp.pause();
            }
        } catch (IllegalStateException ex) {
            mp.stop();
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    private void Play() {
        if (mp != null) {
            mp.start();
        }

    }

    public Field getField() {
        Field[] fields = R.raw.class.getFields();
        if (currentindex >= fields.length) {
            currentindex = 0;
        } else if (currentindex < 0) {
            currentindex = fields.length - 1;
        }
        return fields[currentindex];
    }

    public Field getimageField() {
        Field field;
        try {
            field = R.drawable.class.getField(getField().getName());
        } catch (NoSuchFieldException ex) {
            field = null;
        }

        return field;
    }

    public Field getTextField() {
        Field field;
        try {
            field = R.string.class.getField(getField().getName());
        } catch (NoSuchFieldException ex) {
            field = null;
        }

        return field;
    }

    private void Exit() {
        System.exit(0);
    }

    public void onCompletion(MediaPlayer mp) {
        //PlayNext();
    }

}
