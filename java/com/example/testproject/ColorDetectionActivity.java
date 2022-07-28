package com.example.testproject;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ToggleButton;
import android.widget.CompoundButton;

import androidx.constraintlayout.widget.ConstraintSet;

import java.io.FileNotFoundException;
import java.util.List;

public class ColorDetectionActivity extends CameraActivity implements View.OnTouchListener {
    private static final String  TAG            = "ColorDetect::Activity";

    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;

    //    private TextView             color_detection_title;
    private ToggleButton         mode_switch_button;
    private ToggleButton         library_switch_button;
    private Mat                  sample_img;
    private MaskImageView        sample_img_view;
    private android.widget.TextView RTextView, GTextView, BTextView, ColorNameTextView;
    private Uri                  uri_buff;
    private boolean              detect_mode;
    private boolean              library_mode;
    private Bitmap               img_bitmap;
    private Intent               cIntent;
    private Intent               mIntent;
     private String               colorname="";
    private int[]               detectedColor = {0, 0, 0};

    /** Initialize OpenCV */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @SuppressLint({"ClickableViewAccessibility"})
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("State", "OpenCV loaded successfully");
                    Bitmap bitmap = null;
                    try { // load image source for detection by uri
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_buff));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    sample_img = new Mat();
                    Utils.bitmapToMat(bitmap, sample_img);
                } break;
                default: {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.color_detection_surface_view);

        RTextView = findViewById(R.id.pixel_r_value);
        GTextView = findViewById(R.id.pixel_g_value);
        BTextView = findViewById(R.id.pixel_b_value);
        ColorNameTextView = findViewById(R.id.pixel_color_name);

        sample_img_view = findViewById(R.id.sample_img_view);
        sample_img_view.setOnTouchListener(ColorDetectionActivity.this);

//        color_detection_title = findViewById(R.id.color_detection_title);
//        color_detection_title.setTypeface(Typeface.createFromAsset(getAssets(), "TCM_____.TTF"));
        mode_switch_button = findViewById(R.id.mode_switch);
        library_switch_button = findViewById(R.id.library_switch);
        mode_switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    detect_mode = true;
                } else {
                    detect_mode = false;
                }
            }
        });
        library_switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    library_mode = true;
                } else {
                    library_mode = false;
                }
            }
        });

        Intent mIntent = getIntent();
        uri_buff = Uri.parse(mIntent.getStringExtra("image"));
        try {
            //display image view
            img_bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_buff));
            sample_img_view.setImageBitmap(img_bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("Success", "onCreate launched");
    }

    /** Check whether OpenCV is successfully loaded. */
    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, getApplicationContext(), mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch (View v, MotionEvent event) {
        // reset image view to clear previous color blob detection result
        sample_img_view.setImageBitmap(img_bitmap);
        // get touch coordinates
        int cols = sample_img.cols(); // original image width
        int rows = sample_img.rows(); // original image height
        int view_cols = sample_img_view.getMeasuredWidth();
        int view_rows = sample_img_view.getMeasuredHeight();
        int rawX = (int)event.getX();
        int rawY = (int)event.getY();
        int x = 0; int y = 0;
        // coordinate transformation
        if ((double)cols / rows > (double)view_cols / view_rows) {
            // image wider than ImageView
            x = rawX * cols / view_cols;
            int yOffset = (view_rows - rows * view_cols / cols) / 2;
            y =  (rawY - yOffset) * rows / (view_rows - 2 * yOffset);
        } else {
            // image higher than ImageView / same proportion as ImageView
            y = rawY * rows / view_rows;
            int xOffset = (view_cols - cols * view_rows / rows) / 2;
            x = (rawX - xOffset) * cols / (view_cols - 2 * xOffset);
        }
        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");
        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;
        boolean markerColor = true;
        if (!detect_mode) {
            markerColor = pixelMode(x, y);
        } else {
            markerColor = blobMode(x, y, cols, rows);
        }
        sample_img_view.setMarker(view_cols, view_rows, markerColor);
        return false; // don't need subsequent touch events
    }

    private boolean pixelMode (int x, int y) {
        Log.i("Current detect mode","pixel");
        sample_img_view.setMaskType(0);
        // get pixel point BGR (byte)
        byte[] TouchedPixel = new byte[sample_img.channels()];
        sample_img.get(y, x, TouchedPixel);
        // transform BGR(byte) to BGR(int)
        int PixelR = TouchedPixel[0]&0xff;
        int PixelG = TouchedPixel[1]&0xff;
        int PixelB = TouchedPixel[2]&0xff;
        // show pixel RGB on the screen after clicking
        RTextView.setText("R = " + String.valueOf(PixelR));
        GTextView.setText( "G = " + String.valueOf(PixelG));
        BTextView.setText( "B = " + String.valueOf(PixelB));
        colorname = GetColorName.getColorFromRGB(PixelR, PixelG, PixelB, library_mode);
        ColorNameTextView.setText(colorname);
        detectedColor[0] = PixelR;
        detectedColor[1] = PixelG;
        detectedColor[2] = PixelB;
        cIntent = new Intent();
        cIntent.putExtra("detectedColor", detectedColor);
        cIntent.setClass(ColorDetectionActivity.this, DetailActivity.class);
//            startActivity(cIntent);
        return getLightness(PixelR, PixelG, PixelB);
    }

    private boolean blobMode (int x, int y, int cols, int rows) {
        Log.i("Current detect mode","blob");
        // a small square area, with the touched point as the center
        Rect touchedRect = new Rect();
        touchedRect.x = (x>4) ? x-4 : 0;
        touchedRect.y = (y>4) ? y-4 : 0;
        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);

        Mat touchedRegionRgba = sample_img.submat(touchedRect);
        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region (result in rgba form)
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width*touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;
        mBlobColorRgba = convertScalarHsv2Rgba(mBlobColorHsv);

        // display color in text view
        detectedColor[0] = (int) mBlobColorRgba.val[0];
        detectedColor[1] = (int) mBlobColorRgba.val[1];
        detectedColor[2] = (int) mBlobColorRgba.val[2];
        RTextView.setText("R = " + String.valueOf(detectedColor[0]));
        GTextView.setText( "G = " + String.valueOf(detectedColor[1]));
        BTextView.setText( "B = " + String.valueOf(detectedColor[2]));
        colorname = GetColorName.getColorFromRGB(detectedColor[0], detectedColor[1],
                detectedColor[2], library_mode);
        ColorNameTextView.setText(colorname);
        cIntent = new Intent();
        cIntent.putExtra("detectedColor", detectedColor);
        cIntent.setClass(ColorDetectionActivity.this, DetailActivity.class);
//            startActivity(cIntent);

        // display color blob contour
        Log.i("Camera", "begin setting detector");
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        SPECTRUM_SIZE = new Size(sample_img_view.getWidth(), sample_img_view.getHeight());
        CONTOUR_COLOR = getContourColor(mBlobColorHsv);
        // set target color for detector
        Log.i("Camera", "mLowerBound: " + mBlobColorHsv.val[0] + " " + mBlobColorHsv.val[1]
                + " " + mBlobColorHsv.val[2] + " " + mBlobColorHsv.val[3]);
        mDetector.setHsvColor(mBlobColorHsv);
        // use deep copy, otherwise contours will be drawn on original image
        Mat mRgba = sample_img.clone();
        mDetector.process(mRgba);
        List<MatOfPoint> contours = mDetector.getContours();
        Log.i("Camera", "Contours count: " + contours.size());
        Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR,
                (int) getContourWidth(cols, rows));

        Mat colorLabel = mRgba.submat(4, 68, 4, 68);
        colorLabel.setTo(mBlobColorRgba);
        Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
        mSpectrum.copyTo(spectrumLabel);

        Bitmap bitmap = Bitmap.createBitmap(sample_img.width(),sample_img.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mRgba, bitmap);
        sample_img_view.setImageBitmap(bitmap);

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE, 0, 0, Imgproc.INTER_LINEAR_EXACT);

        touchedRegionRgba.release();
        touchedRegionHsv.release();

        return getLightness((int)mBlobColorRgba.val[0], (int)mBlobColorRgba.val[1],
                (int)mBlobColorRgba.val[2]);
    }

    private boolean getLightness (int r, int g, int b) {
        int min = r; int max = r;
//        if (g < min) min = g;
//        if (b < min) min = b;
//        if (g > max) max = g;
//        if (b > max) max = b;
        double lightness =  r*0.299 + g*0.587 + b*0.114;
        Log.i("Lightness",String.valueOf(lightness));
        if (lightness > 127.5) { // detected color is light
            return true; // use dark cross
        } else { // detected color is dark
            return false; // use light cross
        }
    }

    /** Adjust contour line width regarding image size. */
    private double getContourWidth (int cols, int rows) {
        double width = 0;
        int view_cols = sample_img_view.getMeasuredWidth();
        int view_rows = sample_img_view.getMeasuredHeight();
        if ((double)cols / rows > (double)view_cols / view_rows) {
            width = 10 * (double) cols / view_cols;
        } else {
            width = 10 * (double) rows / view_rows;
        }
        return width;
    }

    private Scalar convertScalarHsv2Rgba (Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);
        return new Scalar(pointMatRgba.get(0, 0));
    }

    private Scalar getContourColor (Scalar BlobColorHsv) {
        Scalar ReverseHsv = new Scalar(255);
        ReverseHsv.val[0] = (BlobColorHsv.val[0] + 180) % 360;
        ReverseHsv.val[1] = 255;
        ReverseHsv.val[2] = 255;
        Scalar ReverseRgba = convertScalarHsv2Rgba(ReverseHsv);
        Scalar ContourColor = new Scalar((int)ReverseRgba.val[0], (int)ReverseRgba.val[1],
                (int)ReverseRgba.val[2], 255);
        return ContourColor;
    }

    public void startDetail(View view){
        if (colorname!="") {
            startActivity(cIntent);
        } else {
            startActivity(new Intent(this, EmptyActivity.class));
        }
    }

    public void startMain(View view){
        startActivity(new Intent(this, MainPage.class));
    }

    public void startFeedback(View view){
        startActivity(new Intent(this, Feedback.class));
    }
}