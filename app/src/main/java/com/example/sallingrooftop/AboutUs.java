package com.example.sallingrooftop;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AboutUs extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private ImageView mImageView;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide();

        //change title bar background color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mImageView = (ImageView) findViewById(R.id.picture);

    }

    @Override
    protected void onResume(){
        super.onResume();
        this.mSensorManager.registerListener(this, mAccelSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this);
    }

    private long lastUpdate = 0;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private static int[] imgs = {R.drawable.bmayvmggr_uhhkob3dfizyqo__y, R.drawable.cold_drinks, R.drawable.food};
    private int currPicture;
    private int lastPicture = 0;

    @Override
    public void onSensorChanged(SensorEvent event){
        long currTime = System.currentTimeMillis();
        if ((currTime - lastUpdate) > 500) {
            long diffTime = (currTime - lastUpdate);
            lastUpdate = currTime;

            mLastX = event.values[0];
            mLastY = event.values[1];
            mLastZ = event.values[2];


//            float NOISE = (float) 1.0;
//            if (mLastX < NOISE) mLastX = (float)0.0;
//            if (mLastY < NOISE) mLastY = (float)0.0;
//            if (mLastZ < NOISE) mLastZ = (float)0.0;

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (mLastX * mLastX + mLastY * mLastY + mLastZ * mLastZ));            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 2) {
                currPicture = lastPicture + 1;
                lastPicture = currPicture;
                if (currPicture > 2){lastPicture = 0; currPicture = 0;}

                mImageView.setImageResource(imgs[currPicture]);
                //Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}