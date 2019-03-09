package com.sangcomz.fishbun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sangcomz.fishbun.bean.Album;
import com.sangcomz.fishbun.define.Define;

import java.util.List;


public class AlbumListAdapter
        extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {

    private Fishton fishton;
    private List<Album> albumList;


    AlbumListAdapter() {
        fishton = Fishton.getInstance();
    }

    void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view, fishton.albumThumbnailSize);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imgAlbumThumb.setImageDrawable(null);
        Fishton.getInstance().imageAdapter
                .loadImage(holder.imgAlbumThumb, Uri.parse(albumList.get(position).thumbnailPath));

        holder.view.setTag(albumList.get(position));
        Album a = (Album) holder.view.getTag();
        holder.txtAlbumName.setText(albumList.get(position).bucketName);
        holder.txtAlbumCount.setText(String.valueOf(a.counter));


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album a = (Album) v.getTag();
                Context context = holder.view.getContext();
                Intent i = new Intent(context, PickerActivity.class);
                i.putExtra(Define.BUNDLE_NAME.ALBUM.name(), a);
                i.putExtra(Define.BUNDLE_NAME.POSITION.name(), position);
                ((Activity) context).startActivityForResult(i, new Define().ENTER_ALBUM_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    List<Album> getAlbumList() {
        return albumList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView imgAlbumThumb;
        private TextView txtAlbumName;
        private TextView txtAlbumCount;

        ViewHolder(View view, int albumSize) {
            super(view);
            this.view = view;
            imgAlbumThumb = view.findViewById(R.id.img_album_thumb);
            imgAlbumThumb.setLayoutParams(new LinearLayout.LayoutParams(albumSize, albumSize));

            txtAlbumName = view.findViewById(R.id.txt_album_name);
            txtAlbumCount =  view.findViewById(R.id.txt_album_count);
        }
    }
}


