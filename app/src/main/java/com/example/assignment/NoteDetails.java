package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NoteDetails extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText;
    private Button delete;
    private Note selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        initWidgets();
        checkForEdits();
    }

    private void initWidgets()
    {
        titleEditText = findViewById(R.id.note_title);
        descriptionEditText = findViewById(R.id.note_detail);
        delete = findViewById(R.id.delete_note);
    }

    private void checkForEdits()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.noteEdit, -1);
        selected = Note.getNoteForID(passedNoteID);

        if (selected != null)
        {
            titleEditText.setText(selected.getTitle());
            descriptionEditText.setText(selected.getDescription());
        }
        else
        {
            delete.setVisibility(View.INVISIBLE);
        }
    }

    public void saveNote(View view)
    {
        SQLiteManager sqLiteManager = SQLiteManager.database_instance(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descriptionEditText.getText());

        if (selected == null)
        {
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDB(newNote);
        }
        else
        {
            selected.setTitle(title);
            selected.setDescription(desc);
            sqLiteManager.updateNote(selected);
        }

        finish();
    }

    public void deleteNote(View view)
    {
        selected.setDeleted(new Date());
        SQLiteManager sqLiteManager  = SQLiteManager.database_instance(this);
        sqLiteManager.updateNote(selected);
        finish();
    }

}