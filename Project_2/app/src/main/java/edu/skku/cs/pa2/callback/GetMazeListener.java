package edu.skku.cs.pa2.callback;

import java.util.List;

import edu.skku.cs.pa2.model.Maze;

public interface GetMazeListener {
    public void onSuccess(List<Maze> mazeList);
    public void onFailed();
}
