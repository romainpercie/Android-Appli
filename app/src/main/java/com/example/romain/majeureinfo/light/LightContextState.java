package com.example.romain.majeureinfo.light;

public class LightContextState {

    private String id;
    private String status;
    private String level;

    public LightContextState(String id, String status, String level){
        super();
        this.id = id;
        this.status = status;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getStatus() {
        return status;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }
}
