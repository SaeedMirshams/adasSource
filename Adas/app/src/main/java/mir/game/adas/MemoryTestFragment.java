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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 *
 * <p>
 * This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class MemoryTestFragment extends Fragment {

    int animalindex;
    int[] optionsindex;

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
    public MemoryTestFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //int position = getArguments().getInt("position");
        Animal subjectAnimal = CommonPlace.animals.get(animalindex);

        rootView = (ViewGroup) inflater
                .inflate(R.layout.memory_test_fragment, container, false);
        TextView t = (TextView) rootView.findViewById(R.id.txt_name);
        ImageView[] img = new ImageView[4];
        img[0] = (ImageView) rootView.findViewById(R.id.img1);
        img[1] = (ImageView) rootView.findViewById(R.id.img2);
        img[2] = (ImageView) rootView.findViewById(R.id.img3);
        img[3] = (ImageView) rootView.findViewById(R.id.img4);

        int w = Utility.DisplaySize().x;
        for (int i = 0; i < 4; i++) {
            img[i].getLayoutParams().width = (w - 20) / 2;
            img[i].getLayoutParams().height = (w - 20) * 2 / 5;
            img[i].setScaleType(ImageView.ScaleType.FIT_XY);
            img[i].setBackgroundResource(R.drawable.round_rect);
            setimage(img[i], optionsindex[i]);
            img[i].setTag(optionsindex[i]);
            img[i].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    ShowToast(v);
                }
            });
        }

        t.setText(subjectAnimal.getFarsiName());
        t.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Utility.play(animalindex);
            }
        });
        Utility.applyFont(rootView);
        return rootView;
    }

    private void ShowToast(View v) {
        ImageView iv = (ImageView) v;
        int x = (Integer) v.getTag();
        Utility.play(x);
        if (x == animalindex) {
            CommonPlace.memoryTestActivity.score += 5;
            iv.setBackgroundColor(Color.rgb(200, 255, 200));
            Handler handler1 = new Handler();

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CommonPlace.memoryTestActivity.NextTest();
                }
            }, 120);
        } else {
            CommonPlace.memoryTestActivity.score -= 2;
            iv.setBackgroundColor(Color.rgb(255, 200, 200));
        }

    }

    private void setimage(ImageView img, int index) {
        ZipEntry z = CommonPlace.zipFile.getEntry(CommonPlace.animals.get(index).getImage());
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
            img.setImageBitmap(bmp);

        } catch (IOException ex) {
            Logger.getLogger(AnimalSlidePageFragment.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
