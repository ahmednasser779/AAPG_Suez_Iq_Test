package com.example.aapgiqtest;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TestObject>> {

    public static final String LOG_TAG = TestActivity.class.getName();
    private static final String TEST_REQUEST_CALL ="http://aapgsuez.net/iq-practice/Newone/iq-practice.json";
    private static final int TEST_LOADER_ID = 1;

    private TestAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private SwipeRefreshLayout pullToRefresh;

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimeRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        pullToRefresh = (SwipeRefreshLayout)findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeResources(R.color.colorPrimary);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
        recreate();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pullToRefresh.isRefreshing()) {
                 pullToRefresh.setRefreshing(false);
                    }
                }
            }, 1000);
         }
       }
     );
        mTextViewCountDown = (TextView) findViewById(R.id.timer_countdown);

        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data and start count down
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(TEST_LOADER_ID, null, this);

            //Start CountDown Timer
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis , 1000) {
                @Override
                public void onTick(long l) {
                    mTimeLeftInMillis = l;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {

                    mTimeRunning = false;
                }
            }.start();
            mTimeRunning = true;

        }else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

            //pause CountDown Timer
            mTimeRunning = false;
        }

        // Find a reference to the {@link ListView} in the layout
        ListView testListView = (ListView)findViewById(R.id.test);

        // Create a new adapter that takes an empty list of test as input
        mAdapter = new TestAdapter(this, new ArrayList<TestObject>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        testListView.setAdapter(mAdapter);
        testListView.setEmptyView(mEmptyStateTextView);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDownTimer.cancel();
        mTimeRunning = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mCountDownTimer.start();
        mTimeRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault() ,"%02d:%02d" , minutes , seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }


    @NonNull
    @Override
    public Loader<List<TestObject>> onCreateLoader(int id, @Nullable Bundle args) {
        return new TestLoader(this , TEST_REQUEST_CALL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<TestObject>> loader, List<TestObject> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_test);
        // Clear the adapter of previous test data
        mAdapter.clear();

        // If there is a valid list of tests, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<TestObject>> loader) {

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
