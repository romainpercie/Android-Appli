package com.example.romain.majeureinfo.room;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomContextState {

    private String name;
    private int floor;
    private int id;

    public RoomContextState(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}