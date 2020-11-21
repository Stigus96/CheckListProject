package com.example.checklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

public class CreateCheckListActivity extends AppCompatActivity {

    Spinner spinner;
    SpinnerAdapter adapter;
    private EditText mTitleView;
    private CreateChecklistTask mCreateChecklistTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_check_list);

        mTitleView = findViewById(R.id.create_checklist_title);
        spinner = findViewById(R.id.spinner);

        new LoadTemplatesTask(checklists -> {
            adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, checklists);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);},this::onException).execute();

        Button createChecklistButton = findViewById(R.id.create_checklist);
        createChecklistButton.setOnClickListener(view -> attemptChecklistCreation());
    }



    private void attemptChecklistCreation(){
        String title = mTitleView.getText().toString();
        Checklist checklist = (Checklist) spinner.getSelectedItem();
        long checklistid = checklist.getChecklistid();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(title)){
            mTitleView.setError("title can't be empty");
            focusView = mTitleView;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else {
            mCreateChecklistTask = new CreateChecklistTask(title, checklistid);
            mCreateChecklistTask.execute((Void) null);
        }
    }

    public class CreateChecklistTask extends AsyncTask<Void, Void, Boolean> {
        private final String mTitle;
        private final long mChecklistid;

        CreateChecklistTask(String title, long checklistid){
            mTitle = title;
            mChecklistid = checklistid;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return Client.getSingleton().createChecklist(mTitle, mChecklistid) != null;
            } catch (IOException e) {
                System.out.println("Failed to create checklist");
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean sucess) {
            mCreateChecklistTask = null;

            if (sucess){
            setResult(RESULT_OK);
            finish();
            } else {
                mTitleView.setError("something went wrong");
                mTitleView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() { mCreateChecklistTask = null; }
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