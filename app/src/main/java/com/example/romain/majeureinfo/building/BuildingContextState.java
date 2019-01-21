package com.example.romain.majeureinfo.building;

import android.os.Parcel;
import android.os.Parcelable;

public class BuildingContextState {

    private String name;
    private int id;

    public BuildingContextState(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
