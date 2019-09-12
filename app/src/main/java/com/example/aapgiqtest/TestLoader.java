package com.example.aapgiqtest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class TestLoader extends AsyncTaskLoader<List<TestObject>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = TestLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new Test Loader.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public TestLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<TestObject> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of tests.
        List<TestObject> tests = Test_Query.fetchTestData(mUrl);
        return tests;
    }
}

