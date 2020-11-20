package com.example.checklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

public class CreateCheckListActivity extends AppCompatActivity {

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_check_list);

//        ArrayList<Checklist> checklist = null;
//        try {
//            checklist = new ArrayList<>(Client.getSingleton().loadTemplates());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

 //       new LoadTemplatesTask(checklists -> {ArrayAdapter<Checklist> adapter =
 //               new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, checklists);
  //          adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
  //          spinner.setAdapter(adapter);},this::onException).execute();



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
}