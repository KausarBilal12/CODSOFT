package com.fyp.flashlight_app;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.flashlight_app.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;
    private TextView textView;
    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);
        textView = findViewById(R.id.status);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(v -> {
            if (hasCameraFlash) {
                if (flashOn) {
                    flashOn = false;
                    toggleButton.setImageResource(R.drawable.flash_off);
                    LightOff();
                    textView.setText("Flashlight is Off");
                } else {
                    flashOn = true;
                    toggleButton.setImageResource(R.drawable.flash_on);
                    LightOn();
                    textView.setText("Flashlight is On");

                }
            } else {
                Toast.makeText(MainActivity.this, "Flash is not available on your device", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void LightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            Log.e("Camera Error", "Unable to turn on camera flashlight");
        }
    }

    private void LightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            Log.e("Camera Error", "Unable to turn off camera flashlight");
        }

    }
}
//    public void onClick(View v) {
//        boolean isChecked = toggleButton.isChecked();
//        if (isChecked) {
//            textView.setText("Toggle Text is ON");
//        } else {
//            textView.setText("Toggle Text is OFF");
//        }
//    }

