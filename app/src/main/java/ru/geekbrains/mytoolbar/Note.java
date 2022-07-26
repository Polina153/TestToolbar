package ru.geekbrains.mytoolbar;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Note implements Parcelable {

    private final String title;
    private final String body;
    //private final String date;
    private final boolean isImportant;

    Note(String title, String body,/* String date,*/ Boolean isImportant) {
        this.title = title;
        this.body = body;
        //this.date = date;
        this.isImportant = isImportant;
    }

    String getTitle() {
        return title;
    }

    String getBody() {
        return body;
    }

    String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());/*Calendar.getInstance().getTime().toString()*/
    }

    boolean getIsImportant() {
        return isImportant;
    }

    Note(Parcel in) {
        title = in.readString();
        body = in.readString();
        //date = in.readString();
        isImportant = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        //parcel.writeString(date);
        parcel.writeByte((byte) (isImportant ? 1 : 0));
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
