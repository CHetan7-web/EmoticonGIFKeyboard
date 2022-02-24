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

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.facebook.drawee.view.SimpleDraweeView;
import com.kevalpatel2106.emoticongifkeyboard.R;
import com.kevalpatel2106.emoticongifkeyboard.internal.EmoticonGifStickerImageView;
import com.kevalpatel2106.emoticongifkeyboard.stickers.StickerPack;
import com.kevalpatel2106.emoticongifkeyboard.stickers.StickerPackLoader;
//import com.kevalpatel2106.emoticongifkeyboard.stickers.StickerPack;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    List<StickerPack> stickerPacks;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout stickerTabsLinearLayout;
//    SimpleDraweeView stickerTabItem;
    ViewPager mViewPager;

    private LoadStickerListAsyncTask loadStickerListAsyncTask;
    private ArrayList<StickerPack> stickerPacksList;
    private ArrayList<EmoticonGifStickerImageView> stickerPackTabs;

    public StickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 2.
     * //     * @param param2 Parameter Stickers.
     *
     * @return A new instance of fragment sticker.
     */
    // TODO: Rename and change types and number of parameters
    public static StickerFragment newInstance() {//String param1, String param2) {
        StickerFragment fragment = new StickerFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadStickerListAsyncTask = new LoadStickerListAsyncTask(this);
        loadStickerListAsyncTask.execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (loadStickerListAsyncTask != null && !loadStickerListAsyncTask.isCancelled()) {
            loadStickerListAsyncTask.cancel(true);
        }
    }

    private void showStickerPack(ArrayList<StickerPack> stickerPackList) {


        stickerPackTabs = new ArrayList();
        stickerPacksList = stickerPackList;

        stickerPackTabs.clear();
        stickerTabsLinearLayout.removeAllViews();

        for (StickerPack stickerPack : stickerPackList) {

            EmoticonGifStickerImageView iv = new EmoticonGifStickerImageView(getContext());
            iv.setImageURI(StickerPackLoader.getStickerAssetUri(stickerPack.identifier, stickerPack.trayImageFile));

            iv.setClickable(true);

            stickerPackTabs.add(iv);
            stickerTabsLinearLayout.addView(iv);

        }

        mViewPager.setAdapter(new StickerCategoryViewPagerAdapter(getChildFragmentManager()));

        for (int i = 0 ; i < stickerPackTabs.size();i++) {

            final int finalI = i;
            stickerPackTabs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (ImageView imgV : stickerPackTabs){
                        imgV.setSelected(false);
                        imgV.setBackgroundColor(Color.parseColor("#E3E7E8"));
                    }
                    stickerPackTabs.get(finalI).setSelected(true);
                    stickerPackTabs.get(finalI).setBackgroundColor(Color.parseColor("#BBBCBD"));

                    mViewPager.setCurrentItem(finalI);
                }
            });
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (ImageView iv : stickerPackTabs){
                    iv.setSelected(false);
                    iv.setBackgroundColor(Color.parseColor("#E3E7E8"));
                }
                stickerPackTabs.get(i).setSelected(true);
                stickerPackTabs.get(i).setBackgroundColor(Color.parseColor("#BBBCBD"));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        if (stickerPackTabs.size() > 0) {
            stickerPackTabs.get(0).setSelected(true);
            stickerPackTabs.get(0).setBackgroundColor(Color.parseColor("#BBBCBD"));

            mViewPager.setCurrentItem(0);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sticker, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stickerTabsLinearLayout = view.findViewById(R.id.sticker_tabs);

        stickerPacksList = new ArrayList<>();
        stickerPackTabs = new ArrayList<>();

        mViewPager = view.findViewById(R.id.sticker_category_view_pager);


    }

    static class LoadStickerListAsyncTask extends AsyncTask<Void, Void, Pair<String, ArrayList<StickerPack>>> {
        private final WeakReference<StickerFragment> contextWeakReference;

        LoadStickerListAsyncTask(StickerFragment activity) {
            this.contextWeakReference = new WeakReference<>(activity);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Pair<String, ArrayList<StickerPack>> doInBackground(Void... voids) {
            ArrayList<StickerPack> stickerPackList;
            try {
                final Context context = contextWeakReference.get().getContext();
                if (context != null) {
                    stickerPackList = StickerPackLoader.fetchStickerPacks(context);
                    if (stickerPackList.size() == 0) {
                        return new Pair<>("could not find any packs", null);
                    }
//                    for (StickerPack stickerPack : stickerPackList) {
//                        StickerPackValidator.verifyStickerPackValidity(context, stickerPack);
//                    }
                    return new Pair<>(null, stickerPackList);
                } else {
                    return new Pair<>("could not fetch sticker packs", null);
                }
            } catch (Exception e) {
                Log.e("EntryActivity", "error fetching sticker packs", e);
                return new Pair<>(e.getMessage(), null);
            }
        }

        @Override
        protected void onPostExecute(Pair<String, ArrayList<StickerPack>> stringListPair) {

            final StickerFragment stickerFragment = contextWeakReference.get();
            if (stickerFragment != null) {
                if (stringListPair.first != null) {
                    // stickerFragment.showErrorMessage(stringListPair.first);
                } else {
                    stickerFragment.showStickerPack(stringListPair.second);
                }
            }
        }
    }

    private class StickerCategoryViewPagerAdapter extends FragmentStatePagerAdapter {

        public StickerCategoryViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return StickerGridFragment.newInstance(stickerPacksList.get(i).identifier);
        }

        @Override
        public int getCount() {
            return stickerPacksList.size();
        }
    }
}