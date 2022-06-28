package edu.skku.cs.pa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // reading text file
    private String readFile(String fileName) throws IOException {
        String fileContents = "";
        try {
            InputStream iStream = getAssets().open(fileName);
            if (iStream != null) {
                InputStreamReader iStreamReader = new InputStreamReader(iStream);
                BufferedReader bufferedReader = new BufferedReader(iStreamReader);
                String temp = "";
                StringBuilder sBuffer = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    sBuffer.append(temp.toUpperCase()).append(" ");
                }
                iStream.close();
                fileContents = sBuffer.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String[] wordList = readFile("wordle_words.txt").split(" "); // wordList = words in wordle_words.txt
            Random random = new Random(System.currentTimeMillis());
            int randomNumber = random.nextInt(wordList.length); // random integer for selecting random answer
            String answer = wordList[randomNumber]; // random answer
            System.out.println(wordList.length + " " + randomNumber + " " + answer);

            // User input
            EditText editText = findViewById(R.id.et_wordle);

            // wordle letter adapter for each 3 color
            WordleLetterAdapter greyWordleLetterAdapter = new WordleLetterAdapter(Color.parseColor("#787C7E"), Color.parseColor("#FFFFFF"));
            WordleLetterAdapter yellowWordleLetterAdapter = new WordleLetterAdapter(Color.parseColor("#FFE46F"), Color.parseColor("#000000"));
            WordleLetterAdapter greenWordleLetterAdapter = new WordleLetterAdapter(Color.parseColor("#99F691"), Color.parseColor("#000000"));

            WordleListAdapter wordleListAdapter = new WordleListAdapter(answer);

            RecyclerView greyRecyclerView = findViewById(R.id.recycler_view_grey);
            RecyclerView yellowRecyclerView = findViewById(R.id.recycler_view_yellow);
            RecyclerView greenRecyclerView = findViewById(R.id.recycler_view_green);

            RecyclerView wordleListRecyclerView = findViewById(R.id.recycler_view_inputs);

            greyRecyclerView.setAdapter(greyWordleLetterAdapter);
            yellowRecyclerView.setAdapter(yellowWordleLetterAdapter);
            greenRecyclerView.setAdapter(greenWordleLetterAdapter);

            wordleListRecyclerView.setAdapter(wordleListAdapter);

            findViewById(R.id.btn_input).setOnClickListener(v -> {
                String input = editText.getText().toString().toUpperCase();

                if (!Arrays.asList(wordList).contains(input)) {
                    Toast.makeText(this, "Word '" + input + "' not in directory!", Toast.LENGTH_SHORT).show();
                    return;
                }

                editText.setText("");
                wordleListAdapter.wordList.add(input);
                wordleListAdapter.notifyItemInserted(wordleListAdapter.wordList.size());

                for (int i = 0; i < 5; i++) {
                    String word = input.substring(i, i + 1);

                    if (wordleListAdapter.answer.contains(word)) { // word가 포함됨
                        if (wordleListAdapter.answer.substring(i, i + 1).equals(word)) { // 위치까지 일치
                            if (!greenWordleLetterAdapter.wordList.contains(word)) { // 중복없게 초록색에 없으면 추가
                                greenWordleLetterAdapter.wordList.add(word);
                            }
                            yellowWordleLetterAdapter.wordList.removeIf(it -> it.equals(word)); // 노란색에 있으면 없앰
                            yellowWordleLetterAdapter.notifyDataSetChanged(); // 노란색 리스트에 반영
                            greenWordleLetterAdapter.notifyItemInserted(greenWordleLetterAdapter.wordList.size()); // 초록색 리스트에 반영
                        } else { // 위치는 일치하지 않는 경우
                            if (!yellowWordleLetterAdapter.wordList.contains(word) && !greenWordleLetterAdapter.wordList.contains(word)) { // 노란색, 초록색 둘다 없는 경우
                                yellowWordleLetterAdapter.wordList.add(word);
                            }
                            yellowWordleLetterAdapter.notifyItemInserted(yellowWordleLetterAdapter.wordList.size()); // 노란색 리스트에 반영
                        }
                    } else {
                        if(!greyWordleLetterAdapter.wordList.contains(word)) {
                            greyWordleLetterAdapter.wordList.add(word);
                        }
                        greyWordleLetterAdapter.notifyItemInserted(greyWordleLetterAdapter.wordList.size());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}