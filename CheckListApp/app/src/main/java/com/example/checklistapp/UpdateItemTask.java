package com.example.checklistapp;


import android.os.AsyncTask;

import java.io.IOException;

public class UpdateItemTask extends AsyncTask<Long, Void, Void> {


    public UpdateItemTask() {
        super();
    }


    @Override
    protected Void doInBackground(Long... longs) {
        try {
            Client.getSingleton().updateItem(longs[0]);
        }catch (IOException e){
            setException(e);
        }
        return null;
    }

    private void setException(IOException e) {
    }
}
