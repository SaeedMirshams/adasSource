/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excersize.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;
import java.util.logging.Level;
import java.util.logging.Logger;
import mir.game.adas.MainActivity;

/**
 *
 * @author 8062439
 */
public class DatafileLoader extends AsyncTask<String, Integer, String> {

    MainActivity context;
    ProgressDialog mProgressDialog;
    private PowerManager.WakeLock mWakeLock;

    public DatafileLoader(MainActivity context) {
        this.context = context;
    }

    public void LoadDatafile() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Downloading Database File...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

        final DatafileLoader downloadTask = this;
        downloadTask.execute("1");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < 1000; i++) {
            publishProgress(i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatafileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return "";
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
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(1000);
        mProgressDialog.setProgress(progres);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        Toast.makeText(context, "Files loaded", Toast.LENGTH_SHORT).show();
    }

}
