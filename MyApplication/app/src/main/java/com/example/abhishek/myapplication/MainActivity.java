package com.example.abhishek.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.abhishek.myapplication.model.TodoData;

import java.util.ArrayList;

import static com.example.abhishek.myapplication.TodoListAdapter.CHECKBOX_TYPE;
import static com.example.abhishek.myapplication.TodoListAdapter.EDIT_TYPE;
import static com.example.abhishek.myapplication.TodoListAdapter.TEXT_TYPE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<TodoData> todoList;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<>();
        adapter = new TodoListAdapter(todoList, new TodoRowClickListener() {
            @Override
            public void onClick(int position, String type) {
                switch (type) {
                    case TEXT_TYPE:
                        //TODO expand row show description
                        //TODO collapse row
                        break;
                    case EDIT_TYPE:
                        //todo open edit activity
                        break;
                    case CHECKBOX_TYPE:
                        //todo set done true / false
                        //todo show/hide horizontal line
                        break;
                }
            }
        });
    }

    public void onFabClick(View view) {
    }
}
