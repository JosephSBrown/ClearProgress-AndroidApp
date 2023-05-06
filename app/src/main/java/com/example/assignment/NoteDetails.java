package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteDetails extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        initWidgets();
    }

    private void initWidgets()
    {
        titleEditText = findViewById(R.id.note_title);
        descriptionEditText = findViewById(R.id.note_detail);
    }

    public void saveNote(View view)
    {
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descriptionEditText.getText());

        int id = Note.noteArrayList.size();
        Note newNote = new Note(id, title, desc);
        Note.noteArrayList.add(newNote);
        finish();
    }
}