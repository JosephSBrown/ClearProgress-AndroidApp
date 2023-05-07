package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NoteSection extends AppCompatActivity {

    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_section);
        initWidgets();
        loadFromDB();
        setNoteAdapter();
        setOnClickListener();
    }

    private void setOnClickListener()
    {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Note selectedNote = (Note) noteListView.getItemAtPosition(position);
                Intent edit = new Intent(getApplicationContext(), NoteDetails.class);
                edit.putExtra(Note.noteEdit, selectedNote.getId());
                startActivity(edit);

            }
        });
        };

    private void initWidgets()
    {
        noteListView = findViewById(R.id.NoteList);
    }

    private void loadFromDB()
    {
        SQLiteManager sqLiteManager = SQLiteManager.database_instance(this);
        sqLiteManager.populateNoteArray();
    }

    private void setNoteAdapter()
    {
        NotesAdapter notesAdapter = new NotesAdapter(getApplicationContext(), Note.nonDeleted());
        noteListView.setAdapter(notesAdapter);
    }

    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetails.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}