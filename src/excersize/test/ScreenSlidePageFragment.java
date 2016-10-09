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
package excersize.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import static android.content.Context.MODE_WORLD_READABLE;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;
import mir.game.adas.R;
import mir.game.adas.R;
import mir.game.adas.R.id;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 *
 * <p>
 * This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment implements View.OnClickListener {

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the
     * given page number.
     *
     * @param pageNumber
     * @return
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        // Set the title view to show the page number.
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText("AAA " + mPageNumber);
//        ((ImageView) rootView.findViewById(R.id.myImage)).setImageResource(R.drawable.adas1);
//        ((TextView) rootView.findViewById(R.id.myText)).setText("BBB " + mPageNumber);
//        LinearLayout lt = ((LinearLayout) rootView.findViewById(R.id.mainlayout));
//        Button btnSave = new Button(this.getContext());
//        btnSave.setText("Save File");
//        lt.addView(btnSave);
//        btnSave.setOnClickListener(this);
////
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    public void onClick(View v) {
        Toast.makeText(this.getContext(), "Test", Toast.LENGTH_SHORT).show();
    }
}
