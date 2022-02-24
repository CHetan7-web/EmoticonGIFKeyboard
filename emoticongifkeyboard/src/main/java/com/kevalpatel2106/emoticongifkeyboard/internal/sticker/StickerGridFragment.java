/*
 * Copyright 2017 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.kevalpatel2106.emoticongifkeyboard.internal.sticker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;

import com.kevalpatel2106.emoticongifkeyboard.R;
import com.kevalpatel2106.emoticongifkeyboard.stickers.Sticker;
import com.kevalpatel2106.emoticongifkeyboard.stickers.StickerPackLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickerGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickerGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATEGORY = "sticker_cat";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String identifier;
    private String mParam2;

    ArrayList<Sticker> stickerList;

    public StickerGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment StickerGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickerGridFragment newInstance(String category) {

        Log.d("STICKER_FRAGEMENT","Sticker grid created for "+category);
        StickerGridFragment fragment = new StickerGridFragment();

        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
//        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            identifier = getArguments().getString(CATEGORY);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sticker_grid, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewFlipper viewFlipper = view.findViewById(R.id.sticker_flipper);

        stickerList = (ArrayList<Sticker>) StickerPackLoader.fetchFromContentProviderForStickers(identifier,getContext().getContentResolver());
        Log.d("STICKER_FRAGEMENT",String.valueOf(stickerList.size()));

        if (stickerList.size() == 0)
            viewFlipper.setDisplayedChild(1);
        else {
            viewFlipper.setDisplayedChild(0);

            RecyclerView stickerRecyclerView = view.findViewById(R.id.sticker_recycler_list);

            stickerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
            StickerGridAdapter stickerGridAdapter = new StickerGridAdapter(getActivity(),stickerList,identifier);

            stickerRecyclerView.setAdapter(stickerGridAdapter);
            stickerGridAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}