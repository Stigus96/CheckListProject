package com.example.checklistapp;

import android.os.AsyncTask;


/**
 * Created by mikael on 29.09.17.
 */
public abstract class AbstractAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static OnException DEFAULT_EXCEPTION_HANDLER = throwable -> {};

    OnPostExecute<Result> onPostExecute;
    OnException onException;

    Throwable exception;

    public AbstractAsyncTask(OnPostExecute<Result> onPostExecute) {
        this.onPostExecute = onPostExecute;
    }

    public AbstractAsyncTask(OnPostExecute<Result> onPostExecute, OnException onException) {
        this.onPostExecute = onPostExecute;
        this.onException = onException;
    }

    @Override
    protected void onPostExecute(Result result) {
        if(getException() != null && getOnException() != null) {
            getOnException().onException(getException());
        } else if(result != null && onPostExecute != null) {
            onPostExecute.onPostExecute(result);
        }
    }

    public OnException getOnException() {
        return onException != null ? onException : DEFAULT_EXCEPTION_HANDLER;
    }
    public void setOnException(OnException onException) {
        this.onException = onException;
    }

    protected void setException(Throwable exception) {
        this.exception = exception;
    }
    public Throwable getException() {
        return exception;
    }

    public OnPostExecute getOnPostExecute() {
        return onPostExecute;
    }

    public void setOnPostExecute(OnPostExecute onPostExecute) {
        this.onPostExecute = onPostExecute;
    }

    public static OnException getDefaultExceptionHandler() {
        return DEFAULT_EXCEPTION_HANDLER;
    }

    public static void setDefaultExceptionHandler(OnException exception) {
        DEFAULT_EXCEPTION_HANDLER = exception;
    }

    public interface OnBackground<Params,Result> {
        Result doInBackground(Params... params) throws Exception;
    }

    public interface OnPostExecute<Result> {
        void onPostExecute(Result result);
    }

    public interface OnException {
        void onException(Throwable exception);
    }
}
