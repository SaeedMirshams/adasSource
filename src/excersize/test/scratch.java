/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excersize.test;

import android.media.MediaPlayer;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author 8062439
 */
public class scratch {
   /* void LoadimgFromZip_delete() {
        InputStream is = null;
        try {
            is = new FileInputStream("");
        } catch (FileNotFoundException ex) {
            is = getResources().openRawResource(R.raw.adasmaster);
        }
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];

                int count;
                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }

                String filename = ze.getName();
                byte[] bytes = baos.toByteArray();

                if (filename.endsWith("rooster.png")) {
                    pics[0].setImage(bytes);
                } else if (filename.endsWith("pig.png")) {
                    pics[1].setImage(bytes);
                } else if (filename.endsWith("cat.png")) {
                    pics[2].setImage(bytes);
                } else if (filename.endsWith("raccoon.mp3")) {
                    playfile(bytes);
                }

            }
            zis.close();
        } catch (Exception ex) {
        } finally {
        }

    }

    void playfile_delete(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("adas", ".mp3", getContext().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // Tried reusing instance of media player
            // but that resulted in system crashes...  
            MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting 
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }

    void readZipEntry_delete(String fileName) {
        try {
            ZipFile zip = new ZipFile("someZipFile.zip");
            ZipEntry entry = zip.getEntry(fileName);
            if (entry != null) {
                InputStream in = zip.getInputStream(entry);
                // see Note #3.
                File tempFile = File.createTempFile("_AUDIO_", ".wav");
                FileOutputStream out = new FileOutputStream(tempFile);
                //IOUtils.copy(in, out);
                // do something with tempFile (like play it)
            } else {
                // no such entry in the zip
            }
        } catch (IOException e) {
            // handle your exception cases...
            e.printStackTrace();
        }
    }
*/    
}
