package com.example.checklistapp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;




public class LoadTemplatesTask extends AbstractAsyncTask<Void, Void, List<Checklist>> {


    public LoadTemplatesTask(OnPostExecute<List<Checklist>> onPostExecute) {
        super(onPostExecute);
    }

    @Override
    public OnPostExecute getOnPostExecute() {
        return super.getOnPostExecute();
    }

    @Override
    protected List<Checklist> doInBackground(Void... nothingHereMoveAlong) {
        try {
            return Client.getSingleton().loadTemplates();
        } catch (IOException e) {
            setException(e);
        }

        return Collections.emptyList();
    }
}
