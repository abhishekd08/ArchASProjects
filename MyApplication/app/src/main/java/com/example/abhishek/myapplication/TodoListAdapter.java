package com.example.abhishek.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.abhishek.myapplication.model.TodoData;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private ArrayList<TodoData> todoList;
    private TodoRowClickListener clickListener;
    public static final String TEXT_TYPE = "text";
    public static final String EDIT_TYPE = "edit";
    public static final String CHECKBOX_TYPE = "checkbox";

    public TodoListAdapter(ArrayList<TodoData> todoList, TodoRowClickListener clickListener) {
        this.todoList = todoList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_list_row, viewGroup, false);
        TodoViewHolder viewHolder = new TodoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {
        todoViewHolder.titleTextview.setText(todoList.get(i).getTitle());
        if (todoList.get(i).getDone()) {
            todoViewHolder.editButton.setVisibility(View.GONE);
            todoViewHolder.horizontalLine.setVisibility(View.VISIBLE);
        } else {
            todoViewHolder.editButton.setVisibility(View.VISIBLE);
            todoViewHolder.horizontalLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextview;
        ImageButton checkBoxButton;
        ImageButton editButton;
        View horizontalLine;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextview = itemView.findViewById(R.id.todo_row_textview);
            horizontalLine = itemView.findViewById(R.id.todo_row_line);
            checkBoxButton = itemView.findViewById(R.id.todo_row_checkbox);
            editButton = itemView.findViewById(R.id.todo_row_editbutton);

            checkBoxButton.setOnClickListener(this);
            titleTextview.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.todo_row_textview:
                    clickListener.onClick(getAdapterPosition(), TEXT_TYPE);
                    break;
                case R.id.todo_row_editbutton:
                    clickListener.onClick(getAdapterPosition(), EDIT_TYPE);
                    break;
                case R.id.todo_row_checkbox:
                    clickListener.onClick(getAdapterPosition(), CHECKBOX_TYPE);
                    break;
            }
        }
    }
}
