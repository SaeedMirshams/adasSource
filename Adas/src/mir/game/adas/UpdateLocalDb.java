/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;
import ir.adad.client.AdView;
import ir.adad.client.Adad;
import ir.adad.client.AdadActivity;
import ir.adad.client.Banner;
import ir.adad.client.InterstitialAdListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import static mir.game.adas.MainActivity.activity;
import org.json.JSONObject;

/**
 *
 * @author 8062439
 */
public class UpdateLocalDb extends Activity implements View.OnClickListener {

    Button btnDownload;
    Button btnReturn;
    Button btnReload;
    private InterstitialAdListener mAdListener = new InterstitialAdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(getApplicationContext(), "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
            //showAdad();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //To change body of generated methods, choose Tools | Templates.
        LayoutInflater infl = getLayoutInflater();
        ViewGroup l = (ViewGroup) infl.inflate(R.layout.activity_update_db, null);

        setContentView(l);

        btnDownload = (Button) findViewById(R.id.btnDownload);

        btnReload = (Button) findViewById(R.id.btnReload);

        btnReturn = (Button) findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnReload.setOnClickListener(this);

        try {

            Adad.initialize(activity);
            Banner banner = new Banner(this);
            banner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 150));
            //banner.setBackgroundColor(Color.rgb(255, 200, 255));
            l.addView(banner);
            banner.setAdListener(mAdListener);

        } catch (Exception ex) {
            ex.toString();
        }
        Utility.applyFont(l);

        //setContentView(v);
    }

    public void onClick(View v) {
        if (v.equals(btnDownload)) {
            download();
        } else if (v.equals(btnReload)) {
            CommonPlace.mainActivity.processDataFile();
        } else if (v.equals(btnReturn)) {
            finish();
        }
    }

    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog;

    private void download() {
// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.info_downloading));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute("http://adas.co.nf/download.php?id=5");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });

        DownloadTask tsk = new DownloadTask(this);
        //tsk.execute("https://raw.githubusercontent.com/SaeedMirshams/adas/master/image/bull.png");
        //tsk.execute("http://www.google.com/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png");

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File thefile = null;
            try {
                URL url = new URL(sUrl[0]);
                // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.20.43.46", 4805));
                //connection = (HttpURLConnection) url.openConnection(proxy);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String fileName = "";
                String disposition = connection.getHeaderField("Content-Disposition");
                String contentType = connection.getContentType();
                int contentLength = connection.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 9,
                                disposition.length());
                    }
                } else {
                    // extracts file name from URL
                    fileName = sUrl[0].substring(sUrl[0].lastIndexOf("/") + 1,
                            sUrl[0].length());
                }
                //File outputDir = context.getCacheDir();
                File outputDir = context.getExternalCacheDir();

                thefile = File.createTempFile("adas", ".tmp", outputDir);
                output = new FileOutputStream(thefile);

                byte data[] = new byte[2048];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                    {
                        publishProgress((int) (total * 100 / fileLength));
                    } else {
                        publishProgress((int) total);
                        fileLength = connection.getContentLength();
                    }
                    output.write(data, 0, count);
                }
                output.close();
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ignored) {
                }
                if (connection != null) {
                    connection.disconnect();
                }
                if (thefile != null && thefile.exists()) {
                    try {
                        Utility.releaseDataFile();
                        Utility.deleteDataFile();
                    } catch (Exception ex) {
                    }
                    if (thefile.renameTo(new File(CommonPlace.mainActivity.getDataFilePath()))) {
                        CommonPlace.mainActivity.processDataFile();
                    }
                } else {
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user 
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            int progres = progress[0];
            if (progres > 100) {
                if (progres > 1024) {
                    mProgressDialog.setProgressNumberFormat("%1d/%2d Kb");
                    progres = progres / 1024;
                } else if (progres > 1024 * 1024) {
                    mProgressDialog.setProgressNumberFormat("%1d/%2d Mb");
                    progres = progres / 1024 / 1024;
                }
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(progres);
                mProgressDialog.setProgress(progres);

            } else {
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgress(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
