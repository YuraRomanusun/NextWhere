package com.puma.nextwhere.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.model.UserImageModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rajesh on 2/6/17.
 */

public class UserImageAdapter extends RecyclerView.Adapter<UserImageAdapter.UserImageViewHolder> {
    private ArrayList<UserImageModel> userImages;

    public UserImageAdapter(ArrayList<UserImageModel> userImages) {
        this.userImages = userImages;
    }

    @Override
    public UserImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_images, parent, false));
    }

    @Override
    public void onBindViewHolder(UserImageViewHolder holder, int position) {
        UserImageModel userImageModel = userImages.get(position);
        if (position == userImages.size() - 1) {
            if (userImageModel.isFile()) {
                holder.cancel.setVisibility(View.VISIBLE);
            } else {
                holder.cancel.setVisibility(View.GONE);
            }
        } else
            holder.cancel.setVisibility(View.GONE);
        if (userImageModel.isFile()) {
            Glide.with(holder.productImage).load(userImageModel.getImagePath()).into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.ic_add_a_photo);
        }
    }

    @Override
    public int getItemCount() {
        return userImages == null ? 0 : userImages.size();
    }

    class UserImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cancel)
        ImageButton cancel;
        @BindView(R.id.productImage)
        ImageView productImage;

        UserImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.productImage)
        void onImageClick() {
            if (userImages.size() > 0 && !userImages.get(0).isFile()) {
                takePictureCallback.takePicture();
            }
        }

        @OnClick(R.id.cancel)
        void onCancelClick() {
            int position = getAdapterPosition();
            if (position > -1) {
                takePictureCallback.removePicture(position);
            }
        }
    }

    public interface TakePictureCallback {
        void takePicture();

        void removePicture(int position);
    }

    public void initializeCallback(TakePictureCallback takePictureCallback) {
        this.takePictureCallback = takePictureCallback;
    }

    private TakePictureCallback takePictureCallback;
}
