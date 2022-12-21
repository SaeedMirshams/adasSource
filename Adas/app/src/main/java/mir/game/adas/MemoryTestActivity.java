package mir.game.adas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.Random;

public class MemoryTestActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    View memoryView;
    int score = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonPlace.memoryTestActivity = this;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Alert", "Lets See if it Works !!!" + paramThrowable.toString());
            }
        });

        try {
            setContentView(R.layout.activity_screen_slide);
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new MemoryTestPagerAdapter(getFragmentManager());

            mPager.setAdapter(mPagerAdapter);
            //getActionBar().setDisplayHomeAsUpEnabled(true);
            mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {

                }
            });

            getActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void NextTest() {
        int i = mPager.getCurrentItem() + 1;
        if (i < mPagerAdapter.getCount()) {
            mPager.setCurrentItem(i);
        } else {
            Toast.makeText(this, "امتیاز شما " + score + " از100", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    class MemoryTestPagerAdapter extends FragmentStatePagerAdapter {

        MemoryTestFragment[] tests;

        public MemoryTestPagerAdapter(FragmentManager fm) {
            super(fm);
            Random r = new Random();//System.currentTimeMillis());
            tests = new MemoryTestFragment[20];
            //boolean flag=true;
            for (int i = 0; i < tests.length; i++) {
                tests[i] = new MemoryTestFragment();
                int n = r.nextInt(CommonPlace.animals.size());
                tests[i].animalindex = n;
            }
            
            for (MemoryTestFragment test : tests) {
                test.optionsindex = new int[4];
                int tmp = r.nextInt(4);
                for (int j = 0; j < 4; j++) {
                    if (tmp == j) {
                        test.optionsindex[j] = test.animalindex;
                    } else {
                        boolean flag = true;
                        while (flag) {
                            flag = false;
                            test.optionsindex[j] = r.nextInt(CommonPlace.animals.size());
                            if (test.optionsindex[j] == test.animalindex) {
                                flag = true;
                            } else {
                                for (int k = 0; k < j; k++) {
                                    if (test.optionsindex[j] == test.optionsindex[k]) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public int getCount() {
            return tests.length;
        }

        @Override
        public Fragment getItem(int i) {
            return tests[i];
        }

    }

}
