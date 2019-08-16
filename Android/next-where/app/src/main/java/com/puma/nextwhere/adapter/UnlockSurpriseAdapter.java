package com.puma.nextwhere.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.puma.nextwhere.R;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.helper.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rtiwari on 10/9/2017.
 */

public class UnlockSurpriseAdapter extends RecyclerView.Adapter<UnlockSurpriseAdapter.UnlockSurpriseViewHolder> {
    private ArrayList<UnlockSurpriseData> unlockData;
    private int oldPosition = 0;
    private boolean isViewLoading;
    private HashMap<String, Integer> categoryIcon;

    public UnlockSurpriseAdapter(ArrayList<UnlockSurpriseData> unlockData) {
        this.unlockData = unlockData;
        categoryIcon = new HashMap<>();
        categoryIcon.put(AppConstants.SupriseSideIcon.PHOTOGRAPH, R.drawable.ic_camera_unlock);
        categoryIcon.put(AppConstants.SupriseSideIcon.QUESTION, R.drawable.ic_star);
        categoryIcon.put(AppConstants.SupriseSideIcon.CHECK_IN, R.drawable.ic_square);
    }

    @Override
    public UnlockSurpriseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UnlockSurpriseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unlock_suprise, parent, false));
    }

    @Override
    public void onBindViewHolder(UnlockSurpriseViewHolder holder, int position) {
        isViewLoading = true;
        UnlockSurpriseData unlockModel = unlockData.get(position);
        holder.lineTop.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.lineBottom.setVisibility(position == unlockData.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        holder.content.setText(unlockModel.getNombrePrenda());
        Context context = holder.itemView.getContext();
        if (categoryIcon.get(unlockModel.getCategoriaPrenda()) != null) {
            holder.rightImage.setImageResource(categoryIcon.get(unlockModel.getCategoriaPrenda()));
        }
        if (unlockModel.getCategoriaOtraPrenda() != null && !unlockModel.getCategoriaOtraPrenda().isEmpty()) {
            holder.status.setBackgroundResource(R.drawable.ic_checkbox);
            SpannableString spannableString = new SpannableString(holder.content.getText());
            spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.content.setText(spannableString);
        } else {
            String isValueAdded = unlockData.get(position == 0 ? 0 : position - 1).getCategoriaOtraPrenda();
            if (isValueAdded != null && !isValueAdded.isEmpty()) {
                int selectedColor = ContextCompat.getColor(context, R.color.colorPrimary);
                changeBackgroundColor(holder.selectedLayout, selectedColor);
                holder.status.setBackgroundResource(R.drawable.ic_currentsuprise);
            } else {
                int unselectedColor = ContextCompat.getColor(context, R.color.gray);
                changeBackgroundColor(holder.selectedLayout, unselectedColor);
                holder.status.setBackgroundResource(R.drawable.ic_unavailable_suprise);
            }

        }
        isViewLoading = false;
    }

    private void changeBackgroundColor(ConstraintLayout content, int colorPrimary) {
        Drawable mDrawable = content.getBackground();
        mDrawable.setColorFilter(new PorterDuffColorFilter(colorPrimary, PorterDuff.Mode.MULTIPLY));
    }

    @Override
    public int getItemCount() {
        return unlockData.size();
    }

    class UnlockSurpriseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lineTop)
        View lineTop;
        @BindView(R.id.ivStatus)
        ImageView status;
        @BindView(R.id.rightImage)
        ImageView rightImage;
        @BindView(R.id.lineBottom)
        View lineBottom;
        @BindView(R.id.tvContent)
        TextView content;
        @BindView(R.id.selectedLayout)
        ConstraintLayout selectedLayout;

        UnlockSurpriseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (position > -1) {
                    iClickCallback.onItemClick(position);
                }

                if (oldPosition != -1 && oldPosition != position) {
                    //unlockData.get(oldPosition).setViewSelected(false);
                    if (!isViewLoading) {
                        notifyItemChanged(oldPosition);
                    }
                }

                /*UnlockSurpriseData unlockModel = unlockData.get(position);
                unlockModel.setViewSelected(true);*/
                if (!isViewLoading) {
                    notifyItemChanged(position);
                }
                oldPosition = position;
            });
        }
    }

    private IClickCallback iClickCallback;

    public interface IClickCallback {
        void onItemClick(int position);
    }

    public void initializeCallback(IClickCallback iClickCallback) {
        this.iClickCallback = iClickCallback;
    }
}
