/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.view.View;
import mir.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ir.adad.client.Banner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author 8062439
 */
class UpdateLocalDb extends LinearLayout implements Runnable, View.OnClickListener {

    Button btnDownload;
    Button btnReturn;
    Button btnReload;

    public UpdateLocalDb(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        btnDownload = new Button(context);
        btnDownload.setText(R.string.action_download);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        btnDownload.setLayoutParams(params);
        this.addView(btnDownload);

        btnReload = new Button(context);
        btnReload.setText(R.string.action_reload);
        btnReload.setLayoutParams(params);
        this.addView(btnReload);

        btnReturn = new Button(context);
        btnReturn.setText(R.string.action_back);
        btnReturn.setLayoutParams(params);
        this.addView(btnReturn);

        btnReturn.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnReload.setOnClickListener(this);

        try
        {
        Banner banner = new Banner(context);
        banner.setLayoutParams(new LayoutParams(100,100));
        banner.setBackgroundColor(Color.argb(255,200,200,255));
        TextView tv=new TextView(context);
        tv.setText("Here");
        banner.addView(tv);
        this.addView(banner);
        }catch(Exception ex)
        {
            ex.toString();
        }
    }

    MainActivity activity;

    public void run() {
        activity = (MainActivity) this.getContext();
        activity.setContentView(this);
    }

    public void onClick(View v) {
        if (v.equals(btnDownload)) {
            download();
        } else if (v.equals(btnReturn)) {
            activity.returntoMain();
        } else if (v.equals(btnReload)) {
            activity.processDataFile();
        }
    }

    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog;

    private void download() {
// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setMessage(getResources().getString(R.string.info_downloading));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(this.getContext());
        downloadTask.execute("https://github.com/SaeedMirshams/adas/archive/master.zip");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
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
                connection = (HttpURLConnection) url.openConnection();
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
                thefile = File.createTempFile("adas", ".tmp", Environment.getExternalStorageDirectory());
                output = new FileOutputStream(thefile);

                byte data[] = new byte[4096];
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
                    }
                    output.write(data, 0, count);
                }
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
                    if (thefile.renameTo(new File(activity.getDataFilePath()))) {
                        activity.processDataFile();
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
