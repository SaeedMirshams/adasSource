package mir.game.adas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MemoryTestActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    View memoryView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    public void onBackPressed() {
        finish();
    }

    class MemoryTestPagerAdapter extends FragmentStatePagerAdapter {

        public MemoryTestPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Fragment getItem(int i) {
            MemoryTestFragment result = new MemoryTestFragment();
            Bundle b = new Bundle(2);
            b.putInt("position", i);
            result.setArguments(b);
            return result;
        }

    }

}
