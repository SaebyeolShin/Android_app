package edu.skku.cs.pa2.model;

import com.google.gson.annotations.SerializedName;

public class Maze {
    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private int size;

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

}
