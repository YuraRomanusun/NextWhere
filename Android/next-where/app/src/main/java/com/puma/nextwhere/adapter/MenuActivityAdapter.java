package com.puma.nextwhere.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.BuildConfig;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.HomeVideoModel;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.MenuActivity_Model;
import com.puma.nextwhere.preference.Preference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by rajesh on 31/5/17.
 */

public class MenuActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LoginApiResponse loginApiResponse;
    private ArrayList<Object> menuData;
    private MenuActivityClickCallback menuActivityClickCallback;
    public static final int MENU_ITEM = 2, HOME_VIDEO = 4, SIGN_OUT = 5;
    int height;

    public MenuActivityAdapter(ArrayList<Object> menuData, int height) {
        this.menuData = menuData;
        this.height = height;
        loginApiResponse = Preference.getInstance().getUserInfo();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == MENU_ITEM) {
            view = layoutInflater.inflate(R.layout.adapter_menuactivity, parent, false);
            viewHolder = new MenuActivityViewHolder(view);
        } else if (viewType == HOME_VIDEO) {
            viewHolder = new HomeVideo(layoutInflater.inflate(R.layout.adapter_home_video, parent, false));
        } else if (viewType == SIGN_OUT) {
            viewHolder = new LogOut(layoutInflater.inflate(R.layout.adapter_logout, parent, false));
        }

        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height / 4;
            view.requestLayout();
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (menuData.get(position) instanceof MenuActivity_Model) {
            return MENU_ITEM;
        } else if (menuData.get(position) instanceof String) {
            return Utils.parseInt((String) menuData.get(position));
        } else if (menuData.get(position) instanceof HomeVideoModel) {
            return HOME_VIDEO;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == MENU_ITEM) {
            setMenuData((MenuActivityViewHolder) holder, position);
        } else if (holder.getItemViewType() == HOME_VIDEO) {
            setHomeVideoData((HomeVideo) holder, position);
        } else if (holder.getItemViewType() == SIGN_OUT) {
            onSignOutClick((LogOut) holder, position);
        }
    }

    private void onSignOutClick(LogOut holder, int position) {
        holder.txvLogOut.setOnClickListener(v -> menuActivityClickCallback.logOut());
    }

    private void setHomeVideoData(HomeVideo holder, int position) {
        Glide.with(holder.itemView.getContext()).load(BuildConfig.FILEURL.concat(
                loginApiResponse.getRutaImagenDestino())).into(holder.imgDestination);
        holder.txvCity.setText(loginApiResponse.getHastaAsignado());
        holder.txvVideoAgain.setOnClickListener(v -> menuActivityClickCallback.showVideo());
    }

    private void setMenuData(MenuActivityViewHolder holder, int position) {
        MenuActivity_Model menuActivity_model = (MenuActivity_Model) menuData.get(position);
        holder.txtMenuText.setText(menuActivity_model.getTitle());
        Bitmap icon = BitmapFactory.decodeResource(holder.itemView.getContext().getResources(),
                menuActivity_model.getDrawableIcon());
        holder.imgMenuImage.setImageBitmap(icon);
        holder.textView.setText(menuActivity_model.getTitle());
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, menuActivity_model.getDrawableIcon(), 0, 0);
        if (menuActivity_model.isSelected()) {
//            changeColor(menuActivity_model.getDrawableSelectedIcon(), holder, R.color.colorPrimary1);
            changeColor2(menuActivity_model.getDrawableSelectedIcon(), holder, R.color.colorPrimary1);
        }
        else {
//            changeColor(menuActivity_model.getDrawableIcon(), holder, R.color.colorPrimary);
            changeColor2(menuActivity_model.getDrawableIcon(), holder, R.color.colorPrimary);
        }
    }


    private void changeColor(int icon, MenuActivityViewHolder holder, int color) {
        TextView textView = holder.textView;
        int v_color = ContextCompat.getColor(holder.itemView.getContext(), color);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        textView.setTextColor(v_color);
    }

    private void changeColor2(int icon, MenuActivityViewHolder holder, int color) {
        TextView textView = holder.txtMenuText;
        int v_color = ContextCompat.getColor(holder.itemView.getContext(), color);
        //textView.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        Bitmap iconSelected = BitmapFactory.decodeResource(holder.itemView.getContext().getResources(), icon);
        holder.imgMenuImage.setImageBitmap(iconSelected);
        textView.setTextColor(v_color);
    }

    @Override
    public int getItemCount() {
        return menuData == null ? 0 : menuData.size();
    }

    public void initializeClickCallBack(MenuActivityClickCallback menuActivityClickCallback) {
        this.menuActivityClickCallback = menuActivityClickCallback;
    }

    class MenuActivityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMenuText)
        TextView txtMenuText;
        @BindView(R.id.imgMenuImage)
        ImageView imgMenuImage;
        @BindView(R.id.txt_view)
        TextView textView;
        @BindView(R.id.parentLayout)
        LinearLayout parentLayout;

        MenuActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

//        @OnTouch(R.id.txt_view)
//        boolean actionOnTouch(View v, MotionEvent event) {
//            int adapterPosition = getAdapterPosition();
//            if (adapterPosition < 0)
//                return false;
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                refreshAllViewExceptThis(adapterPosition, true);
//                menuActivityClickCallback.onClick(getExactPosition(adapterPosition));
//
//                return true;
//            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                refreshAllViewExceptThis(adapterPosition, false);
//            }
//            return false;
//        }
        @OnTouch(R.id.parentLayout)
        boolean actionOnTouch(View v, MotionEvent event) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition < 0)
                return false;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                refreshAllViewExceptThis(adapterPosition, true);
                menuActivityClickCallback.onClick(getExactPosition(adapterPosition));

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                refreshAllViewExceptThis(adapterPosition, false);
            }
            return false;
        }

    }

    private void refreshAllViewExceptThis(int adapterPosition, boolean b) {
        for (int i = 0; i < menuData.size(); i++) {
            if (menuData.get(i) instanceof MenuActivity_Model) {
                MenuActivity_Model menuActivity_model = (MenuActivity_Model) menuData.get(i);
                if (adapterPosition == i)
                    menuActivity_model.setSelected(b);
                else
                    menuActivity_model.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    // Reduce LoginOtherInfo and String from position
    private int getExactPosition(int adapterPosition) {
        int exactPosition = adapterPosition;
        for (int i = 0; i < adapterPosition; i++) {
            Object value = menuData.get(i);
            if (value instanceof String ||
                    value instanceof HomeVideoModel)
                exactPosition--;
        }
        return exactPosition;
    }


    class HomeVideo extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDestination)
        ImageView imgDestination;
        @BindView(R.id.txvCity)
        TextView txvCity;
        @BindView(R.id.txvVideoAgain)
        TextView txvVideoAgain;

        HomeVideo(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }
    }

    class LogOut extends RecyclerView.ViewHolder {
        @BindView(R.id.txvLogOut)
        TextView txvLogOut;

        LogOut(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }
    }

    public interface MenuActivityClickCallback {
        void onClick(int position);

        void showVideo();

        void logOut();
    }
}
