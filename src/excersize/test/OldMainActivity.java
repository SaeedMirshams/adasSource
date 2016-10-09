package excersize.test;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import mir.game.adas.AnimalPager;
import mir.game.adas.MenuArrayAdapter;
import mir.game.adas.R;
import mir.game.adas.MainMenuItem;

public class OldMainActivity extends ListActivity {

    private Field[] getDrawableFields() {
        Field[] fields = R.drawable.class.getFields();
        return fields;
    }

    private void SaveFilesTolocalStorage() {
        Field[] fields = getDrawableFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), field.getInt(field));
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString() + "/Pictures/";
                File wallpaperDirectory = new File(extStorageDirectory);
                // have the object build the directory structure, if needed.
                wallpaperDirectory.mkdirs();

                File file = new File(extStorageDirectory, field.getName() + ".PNG");
                FileOutputStream outStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();

    }

    /**
     * This class describes an individual sample (the sample title, and the
     * activity class that demonstrates this sample).
     */
    /**
     * The collection of all samples in the app. This gets instantiated in {@link
     * #onCreate(android.os.Bundle)} because the {@link MainMenuItem} constructor
     * needs access to {@link
     * android.content.res.Resources}.
     */
    private static MainMenuItem[] mSamples;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SaveFilesTolocalStorage();
        //zip();
        //SaveAdasFile();
        ReadZipFile();
        // Instantiate the list of samples.
        mSamples = new MainMenuItem[]{
         //   new MainMenuItem(R.string.title_screen_animals, R.drawable.adas1, new AnimalPager(this)),
          //  new MainMenuItem(R.string.title_screen_animals, R.drawable.adas2, new AnimalPager(this)),
          // new MainMenuItem(R.string.title_screen_animals, R.drawable.adas3, new AnimalPager(this)),
          //  new MainMenuItem(R.string.title_screen_animals_exam, R.drawable.adas4, new AnimalPager(this)),
        };

        setListAdapter(new MenuArrayAdapter(this, mSamples));
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        // Launch the sample associated with this list position.
        try {
//            setContentView(mSamples[position].getView());
            //startActivity(new Intent(MainActivity.this, mSamples[position].getActivityClass()));
        } catch (Exception ex) {
            ex.toString();
        }
    }

    private static final int BUFFER = 2048;

    public void zip() {
        try {
            BufferedInputStream origin = null;
            String _zipFile = Environment.getExternalStorageDirectory().getPath() + "/Test.zip";
            FileOutputStream dest = new FileOutputStream(_zipFile);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];
            Field[] files = getDrawableFields();

            for (int i = 0; i < files.length; i++) {
                Field thisfield = files[i];
                InputStream fi = getResources().openRawResource(thisfield.getInt(thisfield));
                //FileInputStream fi = new FileInputStream(_files[i]); 
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(files[i].getName().substring(files[i].getName().lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void ReadZipFile() {

        
        InputStream is =getResources().openRawResource(R.raw.adasmaster); //new FileInputStream("/zipSample.zip");
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if(ze.isDirectory())
                    Toast.makeText(this, ze.getName(), 1000).show();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];

                int count;
                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }

                String filename = ze.getName();
                byte[] bytes = baos.toByteArray();

                // Do something with 'filename' and 'bytes' ...
                // How do I save to sdcard?
            }
            zis.close();
        } catch (Exception ex) {
        } finally {
        }
    }

    private void SaveAdasFile() {
        try {
            byte[] buffer = new byte[1024];
            String filename = Environment.getExternalStorageDirectory().getPath() + "/adas.zip";
            FileOutputStream outfile = new FileOutputStream(filename);
            InputStream fi = getResources().openRawResource(R.raw.adasmaster);
            int count = fi.read(buffer, 0, 1024);
            while (count > 0) {
                outfile.write(buffer, 0, count);
                count = fi.read(buffer, 0, 1024);
            }
            outfile.flush();
            outfile.close();
            fi.close();
        } catch (Exception ex) {

        }

    }

}
