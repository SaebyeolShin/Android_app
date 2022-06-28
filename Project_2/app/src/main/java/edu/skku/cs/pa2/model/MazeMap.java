package edu.skku.cs.pa2.model;

import com.google.gson.annotations.SerializedName;

public class MazeMap {
    @SerializedName("maze")
    private String maze;

    public String getMaze() {
        return maze;
    }

}
