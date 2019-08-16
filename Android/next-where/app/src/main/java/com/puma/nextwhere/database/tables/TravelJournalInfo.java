package com.puma.nextwhere.database.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 18/11/17.
 */
@Entity
public class TravelJournalInfo {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int uid=1;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    public String price;

    @ColumnInfo(name = "favourite")
    @SerializedName("favourite")
    @Expose
    public String favourite;

    @ColumnInfo(name = "like")
    @SerializedName("like")
    @Expose
    public String like;

    @ColumnInfo(name = "userName")
    @SerializedName("userName")
    @Expose
    public String userName;

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    public String address;

    public TravelJournalInfo() {
    }

    public TravelJournalInfo(String editMoney, String editFavourite, String editLike,String userName,
                             String address) {
        this.price = editMoney;
        this.favourite = editFavourite;
        this.like = editLike;
        this.userName=userName;
        this.address=address;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getFavourite() {
        return favourite;
    }

    public String getLike() {
        return like;
    }
}
