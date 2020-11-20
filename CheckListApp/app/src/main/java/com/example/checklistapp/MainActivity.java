package com.example.checklistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST = 1;
    boolean isLoggedIn = false;
    private FloatingActionButton createNewChecklist;
    private RecyclerView recyclerView;
    ChecklistAdapter adapter = new ChecklistAdapter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewChecklist = findViewById(R.id.fab);

        createNewChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openCreateChecklistActivity();}
            });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //open checklist when clicked
        adapter.setOnClickListener(position -> {
            Intent intent = new Intent(this, ChecklistActivity.class);
            Checklist checklist = adapter.getChecklists().get(position);
            Client.getSingleton().setSelectedChecklist(checklist);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.checklist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST);

        Client.getSingleton().setChecklistsLoadedListener(this::onChecklistLoaded);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new ChecklistLoader(checklists -> {},this::onException).execute();
    }

    protected void onChecklistLoaded(List<Checklist> checklists){
        recyclerView.post(() -> adapter.setChecklists(checklists));
    }

    /**
     * Display errormessage to user
     *
     * @param throwable
     */
    protected void onException(Throwable throwable) {
        Snackbar.make(findViewById(android.R.id.content),
                "Failed to communicate with server", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.login:
                openLoginActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == LOGIN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                new ChecklistLoader(checklists -> {},this::onException).execute();
            }
        }
    }

    private void openCreateChecklistActivity(){
        Intent intent = new Intent(this, CreateCheckListActivity.class);
        startActivity(intent);
    }


}