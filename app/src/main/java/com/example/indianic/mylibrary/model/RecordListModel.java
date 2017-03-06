package com.example.indianic.mylibrary.model;

/**
 * Created by indianic on 09/05/16.
 */
public class RecordListModel {


    String id;
    String Path;

    public RecordListModel() {
    }

    public RecordListModel(String id, String path) {
        this.id = id;
        Path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    @Override
    public String toString() {
        return "RecordListModel{" + "id='" + id + '\'' + ", Path='" + Path + '\'' + '}';
    }

}
