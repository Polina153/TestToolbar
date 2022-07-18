package ru.geekbrains.mytoolbar;

import java.util.ArrayList;
import java.util.List;

public class NoteSource {
    private List<Note> dataSource;

    public NoteSource() {
        dataSource = new ArrayList<>(7);
    }

    public NoteSource init() {
        String title = String.valueOf(R.string.title);
        String note = String.valueOf(R.string.note);
        String date = String.valueOf(R.string.date);
        for (int i = 0; i < 100; i++) {
            dataSource.add(new Note(title + i, note, date));
        }
        return this;
    }

/*    public Note getCardData(int position) {
        return dataSource.get(position);
    }

    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, NoteData noteData) {
        dataSource.set(position, noteData);
    }

    @Override
    public void addCardData(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }

    NoteData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void updateCardData(int position, NoteData noteData);

    void addCardData(NoteData noteData);

    void clearCardData();*/
}
