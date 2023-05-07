package com.example.assignment;

import java.util.ArrayList;
import java.util.Date;

public class Note {

    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    public static String noteEdit = "noteEdit";

    private int id;
    private String title;
    private String description;
    private Date deleted;

    public Note(int id, String title, String description, Date deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    public static Note getNoteForID(int  passedNoteID)
    {
        for (Note note: noteArrayList)
        {
            if(note.getId() == passedNoteID)
            {
                return note;
            }
        }
        return null;
    }

    public static ArrayList<Note> nonDeleted()
    {
        ArrayList<Note> notDeleted = new ArrayList<>();
        for(Note note: noteArrayList)
        {
            if(note.deleted == null)
            {
                notDeleted.add(note);
            }
        }
        return notDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
