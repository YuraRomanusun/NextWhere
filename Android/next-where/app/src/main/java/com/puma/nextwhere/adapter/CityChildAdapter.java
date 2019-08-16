package com.puma.nextwhere.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by rajesh on 1/6/17.
 */

public class CityChildAdapter extends RecyclerView.Adapter<CityChildAdapter.CityChildViewHolder> implements View.OnClickListener {
    private ArrayList<ExploreCityGuideApiResponse> eatModels;
    Location location;
    private Context context;


    public CityChildAdapter(Context context, ArrayList<ExploreCityGuideApiResponse> eatModels, Location location) {
        this.eatModels = eatModels;
        this.location = location;
        this.context = context;
    }

    @Override
    public CityChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_eat, parent, false));
    }

    @Override
    public void onBindViewHolder(CityChildViewHolder holder, int position) {
        ExploreCityGuideApiResponse exploreCityGuideApiResponse = eatModels.get(position);
        Glide.with(holder.itemView.getContext()).load(exploreCityGuideApiResponse.getImagen()).into(holder.productImage);
        holder.productTitle.setText(exploreCityGuideApiResponse.getNombre());
        holder.productCusine.setText(exploreCityGuideApiResponse.getCategoria());
        holder.address.setText(exploreCityGuideApiResponse.getDireccion());

        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(exploreCityGuideApiResponse.getLatitud()));
        loc1.setLongitude(Double.parseDouble(exploreCityGuideApiResponse.getLongitud()));

        Location loc2 = new Location("");
        loc2.setLatitude(location.getLatitude());
        loc2.setLongitude(location.getLongitude());


        float distanceInMeters = loc1.distanceTo(loc2);
        float distanceInMiles = distanceInMeters * 0.000621371f;

        holder.distance.setText(String.format("%.2f",distanceInMiles) + " mi");

        holder.productPrice.setText(exploreCityGuideApiResponse.getRangoPrecio().length() > 0 ? "$" + exploreCityGuideApiResponse.getRangoPrecio() : "" );

        TextView bt = (TextView) holder.itemView.findViewById(R.id.bt_show_link);
        bt.setVisibility(View.GONE);
        if( ! exploreCityGuideApiResponse.getNombreLink().equals("") && ! exploreCityGuideApiResponse.getLink().equals("")) {


            bt.setText(exploreCityGuideApiResponse.getNombreLink());
            String link = exploreCityGuideApiResponse.getLink();

            bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(exploreCityGuideApiResponse.getLink()));
                    context.startActivity (browserIntent);

                    Log.d("Fragment", "Link: " + link);
                }
            });
        }

        Log.d("From Fragment", "Log from here");
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() != R.id.bt_show_link) {
//            TextView btn = ((View)view.getParent()).findViewById(R.id.bt_show_link);
//            btn.setVisibility(View.VISIBLE);
//            Log.d("From Fragment", "Click from fragment");
//        }
    }

    @Override
    public int getItemCount() {
        return eatModels == null ? 0 : eatModels.size();
    }

    class CityChildViewHolder extends RecyclerView.ViewHolder {
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

        CityChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick({R.id.productImage, R.id.tv_title, R.id.tv_cusine, R.id.tv_local, R.id.tv_price})
        void showLocationOnMap() {
            Log.d("Frag","Log when click");
            ExploreCityGuideApiResponse exploreCityGuideApiResponse = eatModels.get(getAdapterPosition());
            if( ! exploreCityGuideApiResponse.getNombreLink().equals("") && ! exploreCityGuideApiResponse.getLink().equals("")) {
                TextView bt = (TextView) itemView.findViewById(R.id.bt_show_link);
                bt.setVisibility(View.VISIBLE);
            }
            ExploreCityChildInstanceHolder.getInstance().getCallback().setLocationOnMap(
                    Utils.parseDouble(exploreCityGuideApiResponse.getLatitud()),
                    Utils.parseDouble(exploreCityGuideApiResponse.getLongitud()),
                    exploreCityGuideApiResponse.getNombre());
        }
    }
}
