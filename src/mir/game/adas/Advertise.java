/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.widget.RelativeLayout;
import android.widget.Toast;
import ir.adad.client.Adad;
import ir.adad.client.InterstitialAdListener;
import org.json.JSONObject;

/**
 *
 * @author 8062439
 */
public class Advertise extends RelativeLayout implements Runnable {

    private InterstitialAdListener mAdListener = new InterstitialAdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(activity.getApplicationContext(), "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad() {
            Toast.makeText(activity.getApplicationContext(), "Interstitial Ad failed to load", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageReceive(JSONObject message) {
        }

        @Override
        public void onRemoveAdsRequested() {
            Toast.makeText(activity.getApplicationContext(), "User requested to remove interstitial ads from app", Toast.LENGTH_SHORT).show();
            //Move your user to shopping center of your app
        }

        @Override
        public void onInterstitialAdDisplayed() {

        }

        @Override
        public void onInterstitialClosed() {
            Toast.makeText(activity.getApplicationContext(), "Interstitial Ad closed", Toast.LENGTH_SHORT).show();
        }
    };

    MainActivity activity;

    public Advertise(MainActivity context) {
        super(context);
        activity = context;
        //Adad.prepareInterstitialAd();

    }

    public void run() {
        // activity.setContentView(this);
        Adad.prepareInterstitialAd(mAdListener);
        Adad.showInterstitialAd(activity);

    }

}
