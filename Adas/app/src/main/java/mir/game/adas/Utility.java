/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 *
 * @author 8062439
 */
public class Utility {

    public static void ShowMessage(MainActivity context, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.info_attention)
                .setMessage(msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTypeface(getDefaultFont());
        textView.setTextSize(getTextViewTextSize());

    }

    static void ExtractZipFile(String dataFilePath, String destinationPath) {
        try {
            ZipFile zf = new ZipFile(dataFilePath);
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry nextElement = en.nextElement();
                if (nextElement.isDirectory()) {
                    File f = new File(destinationPath + nextElement.getName());
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                } else {
                    String filename = destinationPath + nextElement.getName();
                    InputStream ins = zf.getInputStream(nextElement);
                    FileOutputStream os = new FileOutputStream(filename);
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = ins.read(buffer)) != -1) {
                        os.write(buffer, 0, count);
                    }
                    os.close();
                    ins.close();

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static AnimalList UseZipFile(String dataFilePath) {
        if (CommonPlace.zipFile != null) {
            try {
                CommonPlace.zipFile.close();
            } catch (IOException ex) {
                //throw ex;
            }
        }
        try {
            CommonPlace.zipFile = new ZipFile(dataFilePath);
            CommonPlace.animals = GenerateMetadata();
            return CommonPlace.animals;
        } catch (Exception ex) {
        }
        return null;
    }

    static AnimalList GenerateMetadata() {
        AnimalList result = null;
        InputStream is = null;
        try {

            is = InStreamforFile("adas-master/Data.xml");

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            MyXMLHandler myXMLHandler = new MyXMLHandler();
            xr.setContentHandler(myXMLHandler);
            xr.parse(new InputSource(is));

            result = MyXMLHandler.getAnimalList();
            is.close();
        } catch (Exception ex) {
            Log.e("ERROR OPENING zipfile.",ex.toString());
        }

        return result;
    }

    private static InputStream InStreamforFile(String filename) throws IOException {
        InputStream is;
        ZipEntry entry = CommonPlace.zipFile.getEntry(filename);
        is = CommonPlace.zipFile.getInputStream(entry);
        return is;
    }

    static void releaseDataFile() {
        if (CommonPlace.zipFile != null) {
            try {
                CommonPlace.zipFile.close();
            } catch (IOException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static void deleteDataFile() {
        File oldfile = new File(MainActivity.activity.getDataFilePath());
        oldfile.delete();
    }

    static int currentindex = 0;

    static AnimalLayout NextAnimal() {
        Context context = MainActivity.activity;
        currentindex++;
        if (currentindex >= CommonPlace.animals.size()) {
            currentindex = 0;
        }
        return loadAnimal(context);
    }

    static AnimalLayout PrevAnimal() {
        Context context = MainActivity.activity;
        currentindex--;
        if (currentindex < 0) {
            currentindex = CommonPlace.animals.size() - 1;
        }
        return loadAnimal(context);
    }

    static MediaPlayer mp = null;

    private static AnimalLayout loadAnimal(Context context) {
        AnimalLayout l = new AnimalLayout(context);
        try {
            l.setImage(CurrentImage());
            l.englishTextView.setText(CommonPlace.animals.get(currentindex).getName());
            l.farsiTextView.setText(CommonPlace.animals.get(currentindex).getFarsiName());
            Loadmedia();
        } catch (Exception ex) {
            ex.toString();
        }
        return l;
    }

    static byte[] CurrentImage() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        InputStream is = InStreamforFile(CommonPlace.animals.get(currentindex).getImage());
        int count;
        while ((count = is.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }

        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    static byte[] CurrentSound() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        InputStream is = InStreamforFile(CommonPlace.animals.get(currentindex).getSound());
        int count;
        while ((count = is.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }

        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    private static void Loadmedia() {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("adas", ".mp3", MainActivity.activity.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(CurrentSound());
            fos.close();
            if (mp != null) {
                mp.stop();
                mp.release();
            }
            mp = new MediaPlayer();

            // Tried passing path directly, but kept getting 
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mp.setDataSource(fis.getFD());
            mp.prepare();
            mp.start();
        } catch (Exception ex) {
        }
    }

    static void play(int position) {
        Animal mAnimal = CommonPlace.animals.get(position);
        try {
            ZipEntry ze = CommonPlace.zipFile.getEntry(mAnimal.getSound());
            File tempMp3 = File.createTempFile("adas", ".mp3", MainActivity.activity.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream baos = new FileOutputStream(tempMp3);
            InputStream is = CommonPlace.zipFile.getInputStream(ze);
            byte[] buffer = new byte[1024];

            int count;
            while ((count = is.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }

            baos.close();

            if (mp != null) {
                mp.stop();
                mp.release();
            }
            mp = new MediaPlayer();

            FileInputStream fis = new FileInputStream(tempMp3);
            mp.setDataSource(fis.getFD());
            mp.prepare();
            mp.start();
        } catch (IOException ex) {
            Logger.getLogger(AnimalSlidePageFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static Display display = null;

    public static Point DisplaySize() {
        if (display == null) {
            display = MainActivity.activity.getWindowManager().getDefaultDisplay();
        }
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    static Typeface defaultFont = null;

    static Typeface getDefaultFont() {
        if (defaultFont == null) {
            defaultFont = Typeface.createFromAsset(CommonPlace.mainActivity.getAssets(), "fonts/yekan.ttf");
        }
        return defaultFont;

    }

    public static void applyFont(View view) {
        /*try {
         Method m = view.getClass().getMethod("setTypeface", Typeface.class);
         Typeface t = getDefaultFont();
         m.invoke(view, t);
         } catch (Exception ex) {

         }*/
        if (view instanceof ViewGroup) {
            ViewGroup layout = (ViewGroup) view;
            for (int i = 0; i < layout.getChildCount(); i++) {
                View v = layout.getChildAt(i);
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setTypeface(getDefaultFont());
                    tv.setTextSize(getTextViewTextSize());
                } else if (v instanceof LinearLayout) {
                    applyFont(v);
                }
            }
        }

    }

    static float getTextViewTextSize() {
        return 20;
    }

    static float getButtonTextSize() {
        return 25;
    }

}
