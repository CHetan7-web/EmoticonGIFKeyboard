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
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kevalpatel2106.emoticongifkeyboard.R;
import com.kevalpatel2106.emoticongifkeyboard.stickers.Sticker;
import com.kevalpatel2106.emoticongifkeyboard.stickers.StickerPackLoader;

import java.util.ArrayList;


public class StickerGridAdapter extends RecyclerView.Adapter<StickerGridAdapter.StickerViewHolder> {

    private final String identifier;
    Context mContext;
    ArrayList<Sticker> stickerList;

    public StickerGridAdapter(Context context, ArrayList<Sticker> stickers, String identifier) {

        mContext = context;
        stickerList = stickers;
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sticker_pack_item, viewGroup, false);

        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder stickerViewHolder, int i) {

        Sticker sticker = stickerList.get(i);

        Log.d("STICKER_FRAGEMENT",sticker.getImageFileName()+" : "+sticker.getSize());

        if (sticker.getImageFileName() != null && !sticker.getImageFileName().isEmpty()) {

            final Uri stickerAssetUri = StickerPackLoader.getStickerAssetUri(identifier, sticker.getImageFileName());

            stickerViewHolder.stickerView.setImageURI(stickerAssetUri);

//            final SimpleDraweeView stickerImage = (SimpleDraweeView) LayoutInflater.from(mContext).inflate(R.layout.sticker_item, stickerViewHolder.stickerRow, false);

//            SimpleDraweeView stickerImage = new SimpleDraweeView(mContext);

//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(stickerAssetUri)
//                    .setAutoPlayAnimations(true)
//                    .build();

//            stickerImage.setController(controller);

//            stickerImage.setImageURI(stickerAssetUri);
//            stickerViewHolder.stickerRow.addView(stickerImage);

            Log.d("STICKER_FRAGEMENT", "Sticker View Added for "+stickerAssetUri.toString());
            //             stickerViewHolder.stickerView.animat
        }

    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerView;

        LinearLayout stickerRow;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);

            stickerRow = itemView.findViewById(R.id.sticker_row);
            stickerView =  itemView.findViewById(R.id.sticker_item_simple);
        }
    }
}
