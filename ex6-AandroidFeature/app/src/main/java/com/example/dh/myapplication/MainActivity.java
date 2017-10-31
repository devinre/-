package com.example.dh.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_compass;
    private SensorManager manager;
    private float startDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_compass = findViewById(R.id.iv_compass);
        // 获得传感器管理器
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为方向传感器注册监听器
        manager.registerListener(listener,manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_UI);
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                // 获取当前传感器获取到的角度
                float degree = -event.values[0];
                // 通过补间动画旋转角度，从上次的角度开始旋转
                RotateAnimation ra = new RotateAnimation(startDegree,degree,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                ra.setDuration(200);
                iv_compass.startAnimation(ra);
                // 记录当前旋转后的角度
                startDegree = degree;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onStop() {
        // 为传感器注销监听器
        manager.unregisterListener(listener);
        super.onStop();
    }
}
