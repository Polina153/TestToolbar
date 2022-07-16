package ru.geekbrains.mytoolbar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> dataSet;

    public NotesAdapter(ArrayList<Note> dataSet) {
        this.dataSet = dataSet;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewData(ArrayList<Note> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
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
        viewHolder.getNote().setText(dataSet.get(position).note);
        viewHolder.getBody().setText(dataSet.get(position).body);
        viewHolder.getDate().setText(dataSet.get(position).date);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView note;
        private final TextView body;
        private final TextView date;

        public ViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.text_view_note_title);
            body = view.findViewById(R.id.text_view_note_body);
            date = view.findViewById(R.id.text_view_date);
        }

        public TextView getNote() {
            return note;
        }

        public TextView getBody() {
            return body;
        }

        public TextView getDate() {
            return date;
        }
    }
}
