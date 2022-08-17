package ru.geekbrains.mytoolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> dataSet;
    private final OnMyItemClickListener clickListener;

    public NotesAdapter(ArrayList<Note> dataSet, OnMyItemClickListener clickListener) {
        this.dataSet = dataSet;
        this.clickListener = clickListener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setNewData(ArrayList<Note> userNotes) {
        dataSet = userNotes;
        notifyItemInserted(userNotes.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTextView;
        private final TextView body;
        private final TextView date;
        private final CheckBox isImportant;

        public ViewHolder(View view) {
            super(view);
            noteTextView = view.findViewById(R.id.text_view_note_title);
            body = view.findViewById(R.id.text_view_note_body);
            date = view.findViewById(R.id.text_view_date);
            isImportant = view.findViewById(R.id.is_important_checkbox);
        }

        void bind(Note note) {
            noteTextView.setText(note.getTitle());
            body.setText(note.getBody());
            date.setText(note.getDate());
            isImportant.setChecked(note.getIsImportant());
            String titleText = noteTextView.getText().toString();
            String bodyText = body.getText().toString();
            String dateText = date.getText().toString();
            itemView.setOnClickListener(view -> clickListener.onListItemClick(
                    titleText,
                    bodyText,
                    dateText,
                    isImportant.isChecked()));
        }
    }

    interface OnMyItemClickListener {
        void onListItemClick(String title, String noteTextView, String date, boolean isImportant);
    }
}
