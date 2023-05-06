package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class NoteSection extends AppCompatActivity {

    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_section);
        initWidgets();
        setNoteAdapter();
    }

    private void initWidgets()
    {
        noteListView = findViewById(R.id.NoteList);
    }

    private void setNoteAdapter()
    {
        NotesAdapter notesAdapter = new NotesAdapter(getApplicationContext(), Note.noteArrayList);
        noteListView.setAdapter(notesAdapter);
    }

    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetails.class);
        startActivity(newNoteIntent);
    }
}