package edu.skku.cs.pa1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordleListAdapter extends RecyclerView.Adapter<WordleListAdapter.WordleListViewHolder> {

    List<String> wordList = new ArrayList<>();
    String answer;

    public WordleListAdapter(String answer) {
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @NonNull
    @Override
    public WordleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wordle_list, parent, false);
        return new WordleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordleListViewHolder holder, int position) {
        for (int i = 0; i < 5; i++) {
            String word = wordList.get(position).substring(i, i + 1);
            holder.textViewList.get(i).setText(word);

            if (answer.contains(word)) {
                holder.textViewList.get(i).setBackgroundColor(
                        answer.substring(i, i + 1).equals(word) ? Color.parseColor("#99F691") : Color.parseColor("#FFE46F")
                );
                holder.textViewList.get(i).setTextColor(Color.parseColor("#000000"));
            } else {
                holder.textViewList.get(i).setBackgroundColor(Color.parseColor("#787C7E"));
                holder.textViewList.get(i).setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    static class WordleListViewHolder extends RecyclerView.ViewHolder {

        List<TextView> textViewList = new ArrayList<>();

        public WordleListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewList.add(itemView.findViewById(R.id.tv_first_wordle));
            textViewList.add(itemView.findViewById(R.id.tv_second_wordle));
            textViewList.add(itemView.findViewById(R.id.tv_third_wordle));
            textViewList.add(itemView.findViewById(R.id.tv_fourth_wordle));
            textViewList.add(itemView.findViewById(R.id.tv_five_wordle));
        }
    }

}

