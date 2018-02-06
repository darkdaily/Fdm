package com.example.admin.fdm.mvp.module;

import java.io.Serializable;

/**
 * Created by test on 2018/1/18.
 */

public class StateSerializable implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String cityName;

    private boolean State;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean getState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }
}
