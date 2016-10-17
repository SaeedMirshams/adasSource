package mir.game.adas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import ir.adad.client.Adad;
import ir.adad.client.InterstitialAdListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.json.JSONObject;

public class MainActivity extends ListActivity {

    private static MainMenuItem[] mSamples;
    static MainActivity activity;

    private InterstitialAdListener mAdListener = new InterstitialAdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
            showAdad();
        }

        @Override
        public void onAdFailedToLoad() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad failed to load", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageReceive(JSONObject message) {
            Toast.makeText(activity, message.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRemoveAdsRequested() {
            Toast.makeText(getApplicationContext(), "User requested to remove interstitial ads from app", Toast.LENGTH_SHORT).show();
            //Move your user to shopping center of your app
        }

        @Override
        public void onInterstitialAdDisplayed() {

        }

        @Override
        public void onInterstitialClosed() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad closed", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adad.initialize(getApplicationContext());
        Adad.prepareInterstitialAd(mAdListener);
        CommonPlace.mainActivity = this;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Alert", "Lets See if it Works !!!" + paramThrowable.toString());
            }
        });

        try {

            activity = this;
            LayoutInflater mInflater;
            mInflater = LayoutInflater.from(this);

            mainView = mInflater.inflate(R.layout.activity_main, null);
            setContentView(mainView);

            try {
                mSamples = new MainMenuItem[]{
                    new MainMenuItem(R.string.title_screen_animals, R.drawable.adas1, R.id.action_animal_pager),
                    //         new Sample(R.string.title_screen_animals_exam, R.drawable.adas2, new AnimalPager(this)),
                    //         new Sample(R.string.title_update_local, R.drawable.adas3, new AnimalPager(this)),
                    new MainMenuItem(R.string.title_update_local, R.drawable.adas3, R.id.action_update_database),
                    new MainMenuItem(R.string.title_screen_animals_exam, R.drawable.ic_launcher, R.id.action_memory_test),
                    new MainMenuItem(R.string.action_next, R.drawable.ic_launcher, R.id.action_next),
                    new MainMenuItem(R.string.action_finish, R.drawable.adas3, R.id.action_exit),};
                setListAdapter(new MenuArrayAdapter(this, mSamples));
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }

            processDataFile();
            Utility.applyFont(mainView);
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        returntoMain();
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        //if(mSamples[position])
        try {
            int action = mSamples[position].getAction();
            switch (action) {
                case R.id.action_animal_pager:
                    Intent animalpager = new Intent(this, AnimalPager.class);
                    startActivity(animalpager);
                    break;
                case R.id.action_memory_test:
                    Intent memoryTestActivity = new Intent(this, MemoryTestActivity.class);
                    startActivity(memoryTestActivity);
                    break;
                case R.id.action_next:
                    Intent advertise = new Intent(this, Advertise.class);
                    startActivity(advertise);
                    break;
                case R.id.action_update_database:
                    Intent updateLocalDb = new Intent(this, UpdateLocalDb.class);
                    startActivity(updateLocalDb);
                    break;
                case R.id.action_exit:
                    new AlertDialog.Builder(this)
                            .setTitle("خروج").setMessage("برای خروج مطمئنید؟").setPositiveButton("بله", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    System.exit(0);
                                }
                            }).setNegativeButton("خیر", null).create().show();

                    break;
            }
        } catch (Exception ex) {
            ex.toString();
        }
    }

    static View mainView = null;

    public void returntoMain() {
        setContentView(mainView);
    }

    public void invalidateDataFile() {

    }

    String getDataFilePath() {
        return getCacheDir() + "/adas.zip";
        //return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/adas.zip";
    }

    void processDataFile() {
        File f = new File(MainActivity.activity.getDataFilePath());
        if (!f.exists()) {
            InputStream source = getResources().openRawResource(R.raw.adasmaster);
            int count;
            byte[] buffer = new byte[1024 * 1024];
            try {
                FileOutputStream os = new FileOutputStream(getDataFilePath());
                while ((count = source.read(buffer)) > 0) {
                    os.write(buffer, 0, count);
                }
                os.close();
                source.close();
            } catch (Exception ex) {

            }
        }

        Utility.UseZipFile(getDataFilePath());
    }

    void showAdad() {
        Adad.showInterstitialAd(this);
    }

}
