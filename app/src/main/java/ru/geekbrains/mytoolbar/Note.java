package ru.geekbrains.mytoolbar;

import android.os.Parcel;
import android.os.Parcelable;

class Note implements Parcelable {

    private final String title;
    private final String body;
    private final String date;

    Note(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    String getTitle() {
        return title;
    }

    String getBody() {
        return body;
    }

    String getDate() {
        return date;
    }

    Note(Parcel in) {
        title = in.readString();
        body = in.readString();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(date);
    }

    static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
