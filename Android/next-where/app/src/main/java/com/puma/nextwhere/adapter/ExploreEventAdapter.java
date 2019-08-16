package com.puma.nextwhere.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.ExploreCityChildInstanceHolder;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.ExploreCityGuideApiResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rajesh on 24/12/17.
 */

public class ExploreEventAdapter extends RecyclerView.Adapter<ExploreEventAdapter.ExploreEventViewHolder> {
    private ArrayList<ExploreCityGuideApiResponse> eatModels;

    public ExploreEventAdapter(ArrayList<ExploreCityGuideApiResponse> eatModels) {
        this.eatModels = eatModels;
    }

    @Override
    public ExploreEventAdapter.ExploreEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExploreEventAdapter.ExploreEventViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_eat, parent, false));
    }

    @Override
    public void onBindViewHolder(ExploreEventAdapter.ExploreEventViewHolder holder, int position) {
        ExploreCityGuideApiResponse exploreCityGuideApiResponse = eatModels.get(position);
        Glide.with(holder.itemView.getContext()).load(exploreCityGuideApiResponse.getImagen()).into(holder.productImage);
        holder.productTitle.setText(exploreCityGuideApiResponse.getNombre());
        holder.productCusine.setText(exploreCityGuideApiResponse.getCategoria());
        holder.address.setText(exploreCityGuideApiResponse.getDireccion());
        holder.distance.setText(exploreCityGuideApiResponse.getDistance());
        holder.productPrice.setText(exploreCityGuideApiResponse.getRangoPrecio());
        if (exploreCityGuideApiResponse.getFecha() != null && exploreCityGuideApiResponse.getFecha().length() > 2) {
            String[] date = exploreCityGuideApiResponse.getFecha().split(" ");
            if (date.length >= 2) {
                holder.dateLayout.setVisibility(View.VISIBLE);
                holder.textDate.setText(date[0]);
                holder.textTime.setText(date[1]);
            }
        } else {
            holder.dateLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return eatModels == null ? 0 : eatModels.size();
    }

    class ExploreEventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productImage)
        ImageView productImage;
        @BindView(R.id.tv_title)
        TextView productTitle;
        @BindView(R.id.tv_cusine)
        TextView productCusine;
        @BindView(R.id.tv_local)
        TextView address;
        @BindView(R.id.tv_price)
        TextView productPrice;
        @BindView(R.id.tv_distance)
        TextView distance;
        @BindView(R.id.text_date)
        TextView textDate;
        @BindView(R.id.text_time)
        TextView textTime;
        @BindView(R.id.dateLayout)
        ConstraintLayout dateLayout;

        ExploreEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.productImage, R.id.tv_title, R.id.tv_cusine, R.id.tv_local, R.id.tv_price})
        void showLocationOnMap() {
            ExploreCityGuideApiResponse exploreCityGuideApiResponse = eatModels.get(getAdapterPosition());
            ExploreCityChildInstanceHolder.getInstance().getCallback().setLocationOnMap(
                    Utils.parseDouble(exploreCityGuideApiResponse.getLatitud()),
                    Utils.parseDouble(exploreCityGuideApiResponse.getLongitud()),
                    exploreCityGuideApiResponse.getNombreDestino());
        }
    }
}
