package mir.game.adas;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
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

    private Typeface defaultFont;

    private InterstitialAdListener mAdListener = new InterstitialAdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad failed to load", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageReceive(JSONObject message) {
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
        Toast.makeText(this, "Start Running", Toast.LENGTH_LONG).show();
        try {
            //Adad.initialize(getApplicationContext());
            //Adad.prepareInterstitialAd(mAdListener);
            //Adad.showInterstitialAd(this);

            defaultFont = Typeface.createFromAsset(getAssets(), "fonts/yekan.ttf");
            activity = this;
            LayoutInflater mInflater;
            mInflater = LayoutInflater.from(this);

            mainView = mInflater.inflate(R.layout.activity_main, null);
            setContentView(mainView);

            try {
                mSamples = new MainMenuItem[]{
                    new MainMenuItem(R.string.title_screen_animals, R.drawable.adas1, new AnimalPager(this)),
                    //         new Sample(R.string.title_screen_animals_exam, R.drawable.adas2, new AnimalPager(this)),
                    //         new Sample(R.string.title_update_local, R.drawable.adas3, new AnimalPager(this)),
                    new MainMenuItem(R.string.title_update_local, R.drawable.adas3, new UpdateLocalDb(this)),
                    new MainMenuItem(R.string.title_screen_animals_exam, R.drawable.ic_launcher, new Advertise(this)),
                    new MainMenuItem(R.string.action_finish, R.drawable.adas3, new ConfirmExit(this)),};
                setListAdapter(new MenuArrayAdapter(this, mSamples));
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            }


            processDataFile();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "End RUN", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        try {
            mSamples[position].run();
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
        return Environment.getExternalStorageDirectory().toString() + "/adas.zip";
    }

    void processDataFile() {
        File f = new File(MainActivity.activity.getDataFilePath());
        if (!f.exists()) {
            InputStream source = getResources().openRawResource(R.raw.adasmaster);
            int count;
            byte[] buffer = new byte[1024];
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

    public Typeface getDefaultFont() {
        return defaultFont;
    }

    public float getButtonTextSize() {
        return 25;
    }

    public float getTextViewTextSize() {
        return 20;
    }

}
