package com.example.indianic.mylibrary.model;

/**
 * Created by indianic on 11/05/16.
 */
public class RecordedVideoListModel {

    String id;
    String pathe;



    public RecordedVideoListModel() {
    }

    public RecordedVideoListModel(String id, String pathe) {
        this.id = id;
        this.pathe = pathe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathe() {
        return pathe;
    }

    public void setPathe(String pathe) {
        this.pathe = pathe;
    }

    @Override
    public String toString() {
        return "RecordedVideoListModel{" + "id='" + id + '\'' + ", pathe='" + pathe + '\'' + '}';
    }
}
