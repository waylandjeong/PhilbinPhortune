package com.example.jeongw.philbinphortune;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

/**
 * Created by jeongw on 3/14/2016.
 */
public class ShakeDetector implements SensorEventListener {

    /*
     * The gForce that is necessary to register as shake.
     * Must be greater than 1G (one earth gravity unity).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     * many G's it takes to register a shake
     */

    // threshold to detect a shake
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    // low-pass filter to ensure we are not over sensitive to shake events
    private static final int SHAKE_SLOP_TIME_MS = 500;
    // ensure that we reset the shake counter after this much time without motion
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    // set onShakeListner in constructor and call onShake
    private OnShakeListener mListener;
    // record the now timestamp
    private long mShakeTimestamp;
    // keep count of shakes
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                // record current time tick
                final long now = System.currentTimeMillis();
                // ignore shake events too close together
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }
                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }
                // set new timestamp and increment count
                mShakeTimestamp = now;
                mShakeCount++;
            }

        }
    }

}


