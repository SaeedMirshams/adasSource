/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mir.game.adas;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import static mir.game.adas.Utility.currentindex;
import static mir.game.adas.Utility.mp;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 *
 * <p>
 * This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class AnimalSlidePageFragment extends Fragment {

    AnimalSlidePageFragment self;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the
     * given page number.
     *
     * @param detail
     * @param pageNumber
     * @return
     */
    /*public static ScreenSlidePageFragment create(ReadingListDetail detail) {
     ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
     fragment.mReadingListDetail = detail;
     return fragment;
     }*/
    public AnimalSlidePageFragment() {
        self = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
    ViewGroup rootView = null;
    Animal mAnimal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int position = getArguments().getInt("position");
        mAnimal = CommonPlace.animals.get(position);
        rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        TextView t = (TextView) rootView.findViewById(R.id.farsi_name);
        t.setText(mAnimal.getFarsiName());

        t = (TextView) rootView.findViewById(R.id.english_name);
        t.setText(mAnimal.getName());
        ImageView im = (ImageView) rootView.findViewById(R.id.animal_image);

        Point p = Utility.DisplaySize();
        int w = p.x;
        if (p.x > p.y) {
            w = p.y;
        }
        int m = w / 6;
        w *= (2.0 / 3.0);
        im.getLayoutParams().height = w;
        im.getLayoutParams().width = w;
        //image.setLayoutParams(prm);
        im.setScaleType(ImageView.ScaleType.FIT_XY);
        im.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                play(mAnimal.getSound());
            }

        });

        ZipEntry z = CommonPlace.zipFile.getEntry(mAnimal.getImage());
        try {
            InputStream is = CommonPlace.zipFile.getInputStream(z);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 1024];

            int count;
            while ((count = is.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }

            byte[] bytes = baos.toByteArray();

            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            im.setImageBitmap(bmp);

        } catch (IOException ex) {
            Logger.getLogger(AnimalSlidePageFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utility.applyFont(rootView);
        return rootView;
    }

    private void play(String sound) {
        try {
            ZipEntry ze = CommonPlace.zipFile.getEntry(sound);
            File tempMp3 = File.createTempFile("adas", ".mp3", MainActivity.activity.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream baos = new FileOutputStream(tempMp3);
            InputStream is = CommonPlace.zipFile.getInputStream(ze);
            byte[] buffer = new byte[1024 * 1024];

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

            // Tried passing path directly, but kept getting 
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mp.setDataSource(fis.getFD());
            mp.prepare();
            mp.start();
        } catch (IOException ex) {
            Logger.getLogger(AnimalSlidePageFragment.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
