package com.example.jeongw.philbinphortune;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

public class ViewCookieActivity extends AppCompatActivity {

    public static String[] mQuoteArray;
    private TextView mQuoteString;
    private Button mQuoteButton;
    private ImageView mQuoteImage;
    private static final String TAG = "ViewCookieActivity";
    private Integer[] mImageIds = { R.drawable.bill1,
            R.drawable.bill2, R.drawable.bill3, R.drawable.bill4,
            R.drawable.bill5, R.drawable.bill6, R.drawable.bill7,
            R.drawable.bill8, R.drawable.bill9, R.drawable.bill10,
            R.drawable.bill11, R.drawable.bill12};
    private int mImageH;
    private int mImageW;
    private Bitmap[] mBitmapArray;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private static final int MIN_SHAKE_COUNT_EVENT = 5;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cookie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup ShakeDetector
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                // call shake event handler
                handleShakeEvent(count);
            }
        });

        // get string array from resource file
        mQuoteArray = getResources().getStringArray(R.array.Quotes);

        // get text view and set default text
        mQuoteString = (TextView) findViewById(R.id.quoteString);
        mQuoteString.setText("Philbinism");

        // get imageview
        mQuoteImage = (ImageView) findViewById(R.id.QuoteImageView);

        // setup some local vars
        final int len1 = mQuoteArray.length;
        final int len2 = mImageIds.length;

        // create random object
        final Random r = new Random();

        // setup quote button
        mQuoteButton = (Button) findViewById(R.id.quotebutton);
        mQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup random indices
                int index1 = r.nextInt(len1);
                int index2 = r.nextInt(len2);
                Log.i(TAG, "index1 = " + index1);
                Log.i(TAG, "index2 = " + index2);

                // set text for quote
                mQuoteString.setText(mQuoteArray[index1]);

                // get bitmap object from resource ID
                Bitmap bMap = BitmapFactory.decodeResource(getResources(), mImageIds[index2]);
                Log.i(TAG, "onClick: w = " + mImageW + " h = " + mImageH);

                // scale the bitmap image
                bMap.createScaledBitmap(bMap, mImageW, mImageH, false);

                // set the bitmap in the imageview
                mQuoteImage.setImageBitmap(bMap);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the session manager listener onresume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the sensor manager on pause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        ImageView img = (ImageView) findViewById(R.id.QuoteImageView);
        Log.i(TAG, "Focus width : " + img.getWidth());
        Log.i(TAG, "Focus height : " + img.getHeight());
        mImageH = img.getHeight();
        mImageW = img.getWidth();

    }

    // private helper method to handle the shake event
    private void handleShakeEvent(int count) {
        // stub
        Log.i(TAG, "Got shake count " + count);
        if (count >= MIN_SHAKE_COUNT_EVENT) {
            // do something here
            Log.i(TAG, "Got shake event " + count);
        }
    }
}
