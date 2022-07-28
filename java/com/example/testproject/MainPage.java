package com.example.testproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainPage extends Activity {

    private TextView title;
    private TextView subtitle;
    private TextView upload_img_text;
    private TextView take_photo_text;
    private Button cameraBt;
    private Button photoBt;
    private String TAG = "tag";
    byte buff[] = new byte[125*250];
    Uri uri_buff;

    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_view);
        // jump to get camera permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        // set interface textview fonts
        AssetManager manager = getAssets();
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        upload_img_text = findViewById(R.id.upload_img_text);
        take_photo_text = findViewById(R.id.take_photo_text);
        title.setTypeface(Typeface.createFromAsset(manager, "TCM_____.TTF"));
        subtitle.setTypeface(Typeface.createFromAsset(manager, "TCM_____.TTF"));
        upload_img_text.setTypeface(Typeface.createFromAsset(manager, "TCM_____.TTF"));
        take_photo_text.setTypeface(Typeface.createFromAsset(manager, "TCM_____.TTF"));
        initView();
    }

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;

    private void initView() {
        cameraBt = findViewById(R.id.camera_button);
        photoBt = findViewById(R.id.image_button);
        cameraBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check camera permission
                if (verifyPermissions(MainPage.this, PERMISSIONS_STORAGE[2]) == 0) {
                    Log.i(TAG, "Ask for camera permission");
                    ActivityCompat.requestPermissions(MainPage.this, PERMISSIONS_STORAGE, 3);
                } else { // permission get
                    toCamera();  // jump to camera
                }
            }
        });
        photoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPicture();
//                Intent mIntent = new Intent(MainPage.this, ColorPixelDetectionActivity.class);
//                mIntent.putExtra("image", buff);
//                mIntent.setClass(MainPage.this, ColorPixelDetectionActivity.class);
//                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                        buff = Bitmap2Bytes(bitmap);
                        uri_buff = ImageUri;
                        Intent mIntent = new Intent();
                        mIntent.putExtra("image", uri_buff.toString());
                        mIntent.setClass(MainPage.this, ColorDetectionActivity.class);
                        startActivity(mIntent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_CAMARA:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri uri_photo = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_photo));
                        uri_buff = uri_photo;
                        Intent mIntent = new Intent();
                        mIntent.putExtra("image", uri_buff.toString());
                        mIntent.setClass(MainPage.this, ColorDetectionActivity.class);
                        startActivity(mIntent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    public int verifyPermissions(Activity activity, String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity, permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "permission consented");
            return 1;
        } else {
            Log.i(TAG, "permission rejected");
            return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "user permitted");
            toCamera();
        } else {
            Log.i(TAG, "user rejected");
        }
    }

    // jump to gallery
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_CAMARA);

        Log.i(TAG, "succeed in jumping to gallery");
    }

    // jump to camera
    private void toCamera() {
        File outputImage = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (outputImage.exists()) {
            // outputImage.delete();
        } else {
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // different imguri for different SDK version
        if (Build.VERSION.SDK_INT >= 24) {
            ImageUri = FileProvider.getUriForFile(MainPage.this, "com.example.testproject.fileprovider", outputImage);
        } else {
            ImageUri = Uri.fromFile(outputImage);
        }

        // start camera app
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void startDetection(View view){
        startActivity(new Intent(this, ColorDetectionActivity.class));
    }

    public void startFeedback(View view){
        startActivity(new Intent(this, Feedback.class));
    }

    public void startUserguide(View view){
        startActivity(new Intent(this, Userguide.class));
    }

    public void startMusicActivity(View view){
        startActivity(new Intent(this, MusicActivity.class));
    }
}
