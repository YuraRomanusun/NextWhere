package com.puma.nextwhere.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.LoginOtherInfo;
import com.puma.nextwhere.model.ViewMyTripDetailModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by rajesh on 1/6/17.
 */

public class ViewMyTripDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int RATE_BAR = 1, LOCATION = 2, TITLE = 3, USER = 4, DIVIDER = 5;
    private ArrayList<Object> viewMyTripDetailModels;
    private LoginApiResponse loginApiResponse;

    public ViewMyTripDetailAdapter(ArrayList<Object> viewMyTripDetailModels, LoginApiResponse loginApiResponse) {
        this.viewMyTripDetailModels = viewMyTripDetailModels;
        this.loginApiResponse = loginApiResponse;
    }

    @Override
    public int getItemViewType(int position) {
        if (viewMyTripDetailModels.get(position) instanceof ViewMyTripDetailModel) {
            return ((ViewMyTripDetailModel) viewMyTripDetailModels.get(position)).getViewType();
        } else if (viewMyTripDetailModels.get(position) instanceof HotelDetails)
            return RATE_BAR;
        else
            return USER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == RATE_BAR)
            return new RateBarViewHolder(layoutInflater.inflate(R.layout.adapter_ratebar, parent, false));
        else if (viewType == LOCATION)
            return new FlightDetailViewHolder(layoutInflater.inflate(R.layout.adapter_location, parent, false));
        else if (viewType == TITLE)
            return new RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.adapter_title, parent, false)) {
            };
        else if (viewType == USER)
            return new UserViewHolder(layoutInflater.inflate(R.layout.adapter_user, parent, false));
        else if (viewType == DIVIDER) {
            return new RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.divider, parent, false)) {
            };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == RATE_BAR) {
            RateBarViewHolder rateBarViewHolder = (RateBarViewHolder) holder;
            rateBarViewHolder.setHotelDetails(rateBarViewHolder, position);
        } else if (holder.getItemViewType() == LOCATION) {
            FlightDetailViewHolder flightDetailViewHolder = (FlightDetailViewHolder) holder;
            flightDetailViewHolder.setArraivingFlightDetails(flightDetailViewHolder);
            flightDetailViewHolder.setDepartureFlightDetails(flightDetailViewHolder);
            flightDetailViewHolder.setHeaderHeight(flightDetailViewHolder);
        } else if (holder.getItemViewType() == USER) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.setUser((UserViewHolder) holder, position);
        }
    }


    @Override
    public int getItemCount() {
        return viewMyTripDetailModels == null ? 0 : viewMyTripDetailModels.size();
    }

    class RateBarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hotelImage)
        ImageView hotelImage;

        @BindView(R.id.hotelTitle)
        TextView hotelTitle;

        @BindView(R.id.hotelRating)
        MaterialRatingBar hotelRating;

        @BindView(R.id.hotelLocation)
        TextView hotelLocation;

        @BindView(R.id.checkInTitle)
        TextView checkInTitle;

        @BindView(R.id.checkOutTitle)
        TextView checkOutTitle;


        RateBarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setHotelDetails(RateBarViewHolder holder, int position) {
            HotelDetails hotelDetails = (HotelDetails) viewMyTripDetailModels.get(position);
            holder.hotelLocation.setText(hotelDetails.getDireccion());
            holder.hotelRating.setRating((float) Utils.parseDouble(hotelDetails.getEstrellas()));
            holder.hotelTitle.setText(loginApiResponse.getHotel() == null ?
                    "No Data from API" : loginApiResponse.getHotel());
            Context context = holder.itemView.getContext();
            Glide.with(context).load(hotelDetails.getImagenHotel()).into(holder.hotelImage);
            holder.checkInTitle.setText(context.getString(R.string.checkInTitle, loginApiResponse.getFechaDesde()));
            holder.checkOutTitle.setText(context.getString(R.string.checkOutTitle, loginApiResponse.getFechaHasta()));
        }
    }


    class FlightDetailViewHolder extends RecyclerView.ViewHolder {

        TempViewHolder departureFlightDetails;
        TempViewHolder arraivingFlightDetails;

        FlightDetailViewHolder(View itemView) {
            super(itemView);
            departureFlightDetails = new TempViewHolder();
            arraivingFlightDetails = new TempViewHolder();
            ConstraintLayout departure_details = itemView.findViewById(R.id.departure_details);
            ConstraintLayout arraivingDetails = itemView.findViewById(R.id.arraivingDetail);
            ButterKnife.bind(departureFlightDetails, departure_details);
            ButterKnife.bind(arraivingFlightDetails, arraivingDetails);
        }

        private void setDepartureFlightDetails(FlightDetailViewHolder holder) {
            String flightName = loginApiResponse.getAerolineaIda() == null ? AppConstants.EMPTY_STRING : loginApiResponse.getAerolineaIda();
            holder.departureFlightDetails.flightName.setText(flightName);
            holder.departureFlightDetails.flightNumber.setText(loginApiResponse.getAeropuertoLlegadaIda());
            Context context = holder.itemView.getContext();
            holder.departureFlightDetails.leaveInTitle.setText(context.getString(R.string.leavingTitle,
                    loginApiResponse.getAeropuertoIda()));
            holder.departureFlightDetails.arriving.setText(context.getString(R.string.arrivingTitle,
                    loginApiResponse.getAeropuertoVuelta()));
            holder.departureFlightDetails.date.setText(loginApiResponse.getFechaIda());
            holder.departureFlightDetails.time.setText(loginApiResponse.getHoraIda());
            holder.departureFlightDetails.referenceNumber.setText(context.getString(R.string.ref_value,
                    loginApiResponse.getAerolineaReferenceIda()));

        }

        private void setArraivingFlightDetails(FlightDetailViewHolder holder) {
            String flightName = loginApiResponse.getAerolineaReferenceVuelta() == null ?
                    AppConstants.EMPTY_STRING : loginApiResponse.getAerolineaVuelta();
            holder.arraivingFlightDetails.flightName.setText(flightName);
            holder.arraivingFlightDetails.flightNumber.setText(loginApiResponse.getAeropuertoLlegadaVuelta());
            Context context = holder.itemView.getContext();
            holder.arraivingFlightDetails.leaveInTitle.setText(context.getString(R.string.leavingTitle,
                    loginApiResponse.getAeropuertoVuelta()));
            holder.arraivingFlightDetails.arriving.setText(context.getString(R.string.arrivingTitle,
                    loginApiResponse.getAeropuertoVuelta()));
            holder.arraivingFlightDetails.date.setText(loginApiResponse.getFechaVuelta());
            holder.arraivingFlightDetails.time.setText(loginApiResponse.getHoraVuelta());
            holder.arraivingFlightDetails.referenceNumber.setText(context.getString(R.string.ref_value,
                    loginApiResponse.getAerolineaReferenceVuelta()));
        }

        private void setHeaderHeight(FlightDetailViewHolder holder) {
            holder.departureFlightDetails.headerLayout.post(() -> {
                if (holder.departureFlightDetails.headerLayout.getHeight() >
                        holder.arraivingFlightDetails.headerLayout.getHeight()) {
                    holder.arraivingFlightDetails.headerLayout.getLayoutParams().height =
                            holder.departureFlightDetails.headerLayout.getHeight();
                    holder.arraivingFlightDetails.headerLayout.requestLayout();
                } else {
                    holder.departureFlightDetails.headerLayout.getLayoutParams().height =
                            holder.arraivingFlightDetails.headerLayout.getHeight();
                    holder.departureFlightDetails.headerLayout.requestLayout();

                }
            });

        }
    }

    class TempViewHolder {
        @BindView(R.id.departing_flightName)
        TextView flightName;
        @BindView(R.id.departing_flightNumber)
        TextView flightNumber;
        @BindView(R.id.departing_referenceNumber)
        TextView referenceNumber;
        @BindView(R.id.departing_date)
        TextView date;
        @BindView(R.id.departing_time)
        TextView time;
        @BindView(R.id.departing_leaveInTitle)
        TextView leaveInTitle;
        @BindView(R.id.departing_arriving)
        TextView arriving;
        @BindView(R.id.header)
        ConstraintLayout headerLayout;
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.userPassport)
        TextView userPassport;
        @BindView(R.id.more)
        ImageButton more;


        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setUser(UserViewHolder holder, int position) {
            LoginOtherInfo loginOtherInfo = (LoginOtherInfo) viewMyTripDetailModels.get(position);
            if (loginOtherInfo.getGenero().equalsIgnoreCase(AppConstants.FEMALE)) {
                holder.userImage.setImageResource(R.drawable.lady_balloon);
            } else {
                holder.userImage.setImageResource(R.drawable.man_ballon);
            }
            holder.userName.setText(loginOtherInfo.getNombre().concat(" ").concat(loginOtherInfo.getApellido()));
            holder.userPassport.setText(loginOtherInfo.getNacionalidad());
        }

    }


    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
