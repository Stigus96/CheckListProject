package com.example.checklistapp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;




public class ChecklistLoader extends AbstractAsyncTask<Void, Void, List<Checklist>> {
    public ChecklistLoader(OnPostExecute<List<Checklist>> onPostExecute, OnException onException) {
        super(onPostExecute, onException);
    }

    @Override
    protected List<Checklist> doInBackground(Void... nothingHereMoveAlong) {
        try {
            return Client.getSingleton().loadMyChecklists();
        } catch (IOException e) {
            setException(e);
        }

        return Collections.emptyList();
    }
}


