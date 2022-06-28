package edu.skku.cs.pa1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordleLetterAdapter extends RecyclerView.Adapter<WordleLetterAdapter.WordleLetterViewHolder> {

    public WordleLetterAdapter(int color, int textColor) {
        this.color = color;
        this.textColor = textColor;
    }

    List<String> wordList = new ArrayList<>();
    private final int color;
    private final int textColor;

    @NonNull
    @Override
    public WordleLetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wordle_letter, parent, false);

        return new WordleLetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordleLetterViewHolder holder, int position) {
        holder.textView.setText(wordList.get(position));
        holder.textView.setBackgroundColor(color);
        holder.textView.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    static class WordleLetterViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public WordleLetterViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_worlde_letter);
        }
    }
}
