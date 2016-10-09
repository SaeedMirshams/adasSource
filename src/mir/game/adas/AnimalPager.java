/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 *
 * @author 8062439
 */
public class AnimalPager extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Alert", "Lets See if it Works !!!" + paramThrowable.toString());
            }
        });

        setContentView(R.layout.activity_screen_slide);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        /*Menu optionsMenu = menu;
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.main_activity_actions, optionsMenu);

         // menu.getItem(0).setVisible(false);*/
        return super.onCreateOptionsMenu(menu);
    }

    ViewFlipper flipper;
    AnimalLayout pics;

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

    public void run() {
        PlayNext();
        PlayPrev();
        finish();

    }

    class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AnimalSlidePageFragment f = new AnimalSlidePageFragment();
            Bundle bundle = new Bundle(2);
            bundle.putInt("position", position);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return CommonPlace.animals.size();
        }
    }

}
