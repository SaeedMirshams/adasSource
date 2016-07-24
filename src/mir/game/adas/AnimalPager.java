/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ActionMenuView;
import mir.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 *
 * @author 8062439
 */
public class AnimalPager extends LinearLayout implements View.OnClickListener, Runnable {

    MainActivity activity;

    public AnimalPager(Context context) {
        super(context);
        activity = (MainActivity) context;
        InitializeFrames();
    }
    Button btnPrevious;
    Button btnNext;
    Button btnBack;

    ViewFlipper flipper;
    AnimalLayout pics;

    private void InitializeFrames() {
        this.setOrientation(LinearLayout.VERTICAL);

        btnPrevious = new Button(getContext());
        btnPrevious.setText(R.string.action_previous);
        this.addView(btnPrevious);
        btnPrevious.setOnClickListener(this);

        btnNext = new Button(getContext());
        btnNext.setText(R.string.action_next);
        btnNext.setOnClickListener(this);

        this.addView(btnNext);

        flipper = new ViewFlipper(this.getContext());
        pics = new AnimalLayout(activity);
        flipper.addView(pics);
        pics.setImage(R.drawable.adas1);

        flipper.setOnClickListener(this);
        this.addView(flipper);

        btnBack = new Button(getContext());
        btnBack.setText(R.string.action_back);
        btnBack.setOnClickListener(this);
        this.addView(btnBack);

        LayoutParams prm = new ActionMenuView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        btnNext.setLayoutParams(prm);
        btnPrevious.setLayoutParams(prm);
        btnBack.setLayoutParams(prm);

    }

    public void onClick(View v) {
        if (v == btnNext) {
            PlayNext();
        } else if (v == btnPrevious) {
            PlayPrev();
        } else if (v == btnBack) {
            returntoMain();
        }
    }

    private void PlayNext() throws IllegalStateException {
        flipper.removeAllViews();
        flipper.addView(Utility.NextAnimal());
    }

    private void PlayPrev() {
        flipper.removeAllViews();
        flipper.addView(Utility.PrevAnimal());
    }

    private void Play() {
        Utility.play();
    }

    private void returntoMain() {
        MainActivity.activity.returntoMain();
    }

    public void run() {
        PlayNext();
        PlayPrev();
        activity.setContentView(this);

    }

}
