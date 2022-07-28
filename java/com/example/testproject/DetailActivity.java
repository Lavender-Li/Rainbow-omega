package com.example.testproject;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

// import androidx.navigation.ui.AppBarConfiguration;

import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    // private AppBarConfiguration appBarConfiguration;
    private String colorname;
    private int[] detectedColor = {0, 0, 0};
    // Color Wheel part
    private MaskImageView ColorWheel;
    private android.widget.TextView DetectedColorName;
    private ImageView LightnessBarView;
    private MaskImageView CursorView;
    private android.widget.TextView ColorRGBText;
    private android.widget.TextView ColorHSLText;
    private android.widget.TextView ColorHexText;
    // Color Matching part
    // Theory 1: Complementary
    private MaskImageView ColorWheelComplementary;
    private ImageView ComplementaryOriginalColorView;
    private ImageView ComplementaryColor1View;
    private android.widget.TextView ComplementaryOriginalColorName;
    private android.widget.TextView ComplementaryOriginalColorValue;
    private android.widget.TextView ComplementaryColor1Name;
    private android.widget.TextView ComplementaryColor1Value;
    // Theory 2: Monochromatic
    private MaskImageView ColorWheelMonochromatic;
    private ImageView MonochromaticOriginalColorView;
    private ImageView MonochromaticColor1View;
    private android.widget.TextView MonochromaticOriginalColorName;
    private android.widget.TextView MonochromaticOriginalColorValue;
    private android.widget.TextView MonochromaticColor1Name;
    private android.widget.TextView MonochromaticColor1Value;
    // Theory 3: Analogous
    private MaskImageView ColorWheelAnalogous;
    private ImageView AnalogousOriginalColorView;
    private ImageView AnalogousColor1View;
    private ImageView AnalogousColor2View;
    private android.widget.TextView AnalogousOriginalColorName;
    private android.widget.TextView AnalogousOriginalColorValue;
    private android.widget.TextView AnalogousColor1Name;
    private android.widget.TextView AnalogousColor1Value;
    private android.widget.TextView AnalogousColor2Name;
    private android.widget.TextView AnalogousColor2Value;
    // Theory 4: Triadic
    private MaskImageView ColorWheelTriadic;
    private ImageView TriadicOriginalColorView;
    private ImageView TriadicColor1View;
    private ImageView TriadicColor2View;
    private android.widget.TextView TriadicOriginalColorName;
    private android.widget.TextView TriadicOriginalColorValue;
    private android.widget.TextView TriadicColor1Name;
    private android.widget.TextView TriadicColor1Value;
    private android.widget.TextView TriadicColor2Name;
    private android.widget.TextView TriadicColor2Value;

    private double temp_H, temp_S, temp_L, temp_H1, temp_H2;
    private double temp_R, temp_G, temp_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        Intent cIntent = getIntent();

        detectedColor = cIntent.getIntArrayExtra("detectedColor");
        ColorWheel = findViewById(R.id.color_wheel);
        LightnessBarView = findViewById(R.id.lightness_bar);
        CursorView = findViewById(R.id.lightness_bar_cursor);
        DetectedColorName = findViewById(R.id.detected_color_name);
        ColorRGBText = findViewById(R.id.detected_color_rgb);
        ColorHSLText = findViewById(R.id.detected_color_hsl);
        ColorHexText = findViewById(R.id.detected_color_hex);

        ColorWheelComplementary = findViewById(R.id.color_wheel_complementary);
        ComplementaryOriginalColorView = findViewById(R.id.complementary_color0_display);
        ComplementaryColor1View = findViewById(R.id.complementary_color1_display);
        ComplementaryOriginalColorName = findViewById(R.id.complementary_color0_name);
        ComplementaryOriginalColorValue = findViewById(R.id.complementary_color0_value);
        ComplementaryColor1Name = findViewById(R.id.complementary_color1_name);
        ComplementaryColor1Value = findViewById(R.id.complementary_color1_value);

        ColorWheelMonochromatic = findViewById(R.id.color_wheel_monochromatic);
        MonochromaticOriginalColorView = findViewById(R.id.monochromatic_color0_display);
        MonochromaticColor1View = findViewById(R.id.monochromatic_color1_display);
        MonochromaticOriginalColorName = findViewById(R.id.monochromatic_color0_name);
        MonochromaticOriginalColorValue = findViewById(R.id.monochromatic_color0_value);
        MonochromaticColor1Name = findViewById(R.id.monochromatic_color1_name);
        MonochromaticColor1Value = findViewById(R.id.monochromatic_color1_value);

        ColorWheelAnalogous = findViewById(R.id.color_wheel_analogous);
        AnalogousOriginalColorView = findViewById(R.id.analogous_color0_display);
        AnalogousColor1View = findViewById(R.id.analogous_color1_display);
        AnalogousColor2View = findViewById(R.id.analogous_color2_display);
        AnalogousOriginalColorName = findViewById(R.id.analogous_color0_name);
        AnalogousOriginalColorValue = findViewById(R.id.analogous_color0_value);
        AnalogousColor1Name = findViewById(R.id.analogous_color1_name);
        AnalogousColor1Value = findViewById(R.id.analogous_color1_value);
        AnalogousColor2Name = findViewById(R.id.analogous_color2_name);
        AnalogousColor2Value = findViewById(R.id.analogous_color2_value);

        ColorWheelTriadic = findViewById(R.id.color_wheel_triadic);
        TriadicOriginalColorView = findViewById(R.id.triadic_color0_display);
        TriadicColor1View = findViewById(R.id.triadic_color1_display);
        TriadicColor2View = findViewById(R.id.triadic_color2_display);
        TriadicOriginalColorName = findViewById(R.id.triadic_color0_name);
        TriadicOriginalColorValue = findViewById(R.id.triadic_color0_value);
        TriadicColor1Name = findViewById(R.id.triadic_color1_name);
        TriadicColor1Value = findViewById(R.id.triadic_color1_value);
        TriadicColor2Name = findViewById(R.id.triadic_color2_name);
        TriadicColor2Value = findViewById(R.id.triadic_color2_value);

        initView();
    }

    private void initView() {
        initLightnessBar();
        initLightnessBarCursor();
        initColorWheel(); // initialize top color wheel
        initTheoryComplementary(); // initialize color match 1
        initTheoryMonochromatic(); // initialize color match 2
        initTheoryAnalogous(); // initialize color match 3
        initTheoryTriadic(); // initialize color match 4
    }

    public void startDetection(View view){
        startActivity(new Intent(this, ColorDetectionActivity.class));
    }

    private void initColorWheel() { // initialize big color wheel on the top
        // reset temp
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0;
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        Point coordinate = coordinateTransform(HSLtoRelativeCoordinate(temp_H, temp_S,
                true), true);
        ColorWheel.setColor(coordinate.x, coordinate.y, 0);
        ColorWheel.setMaskType(1);
        colorname = GetColorName.getColorFromRGB(detectedColor[0], detectedColor[1],
                detectedColor[2], false);
        DetectedColorName.setText("Color: " + colorname);
        ColorRGBText.setText("RGB: (" + String.valueOf(detectedColor[0]) + ", "
                + String.valueOf(detectedColor[1]) + ", " +String.valueOf(detectedColor[2]) + ")");
        ColorHSLText.setText("HSL: (" + String.valueOf((int)(temp_H*360)) + "\u00B0" + ", "
                + String.valueOf((int)(100*temp_S)) + ", " + String.valueOf((int)(100*temp_L)) + ")");
        ColorHexText.setText("Hex: #" + convertRGBtoHEX(detectedColor[0], detectedColor[1],
                detectedColor[2]).toUpperCase(Locale.ROOT));
    }

    public static int DpToPixel(Context context, int DP) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources().getDisplayMetrics());
        return (int) px;
    }

    private void initLightnessBar() {
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        Log.i("HSL", String.valueOf(temp_H) + " "
                + String.valueOf(temp_S) + " " + String.valueOf(temp_L));
        // HSL stored in temp
        convertHSLtoRGB(temp_H, temp_S, 0.5);
        Log.i("50% lightness RGB", String.valueOf(temp_R) + " "
                + String.valueOf(temp_G) + " " + String.valueOf(temp_B));
        // RGB for lightness bar middle point stored in temp
        int middleColor = Integer.valueOf(convertRGBtoHEX(255 - (int)temp_R,
                255 - (int)temp_G, 255 - (int)temp_B), 16);
        Log.i("Middle color", String.valueOf(middleColor));
        GradientDrawable lightnessBar = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] {Color.BLACK, - middleColor - 1, Color.WHITE});
        lightnessBar.setCornerRadius(0f);
        LightnessBarView.setBackground(lightnessBar);
    }

    private void initLightnessBarCursor() {
        int view_height = DpToPixel(DetailActivity.this, 30);
        int view_width = DpToPixel(DetailActivity.this, 350);
        CursorView.setCursor(temp_L, view_height, view_width);
        CursorView.setMaskType(2);
    }

    private void initTheoryComplementary() {
        // Complementary: two colors, sum of RGB = (255, 255, 255).
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0;
        int matchColor1_r = 255 - detectedColor[0];
        int matchColor1_g = 255 - detectedColor[1];
        int matchColor1_b = 255 - detectedColor[2];
        // set text view
        ComplementaryOriginalColorName.setText(colorname);
        ComplementaryOriginalColorValue.setText("(" + String.valueOf(detectedColor[0]) + ", "
                + String.valueOf(detectedColor[1]) + ", " +String.valueOf(detectedColor[2]) + ")");
        ComplementaryColor1Name.setText(GetColorName.getColorFromRGB(matchColor1_r,
                matchColor1_g, matchColor1_b, false));
        ComplementaryColor1Value.setText("(" + String.valueOf(matchColor1_r) + ", "
                + String.valueOf(matchColor1_g) + ", " +String.valueOf(matchColor1_b) + ")");
        // set sample block
        ComplementaryOriginalColorView.setBackgroundColor(Color.rgb(detectedColor[0],
                detectedColor[1], detectedColor[2]));
        ComplementaryColor1View.setBackgroundColor(Color.rgb(matchColor1_r, matchColor1_g, matchColor1_b));
        // set color wheel
        ColorWheelComplementary.setTheoryNum(1);
        initSmallColorWheel(1, matchColor1_r, matchColor1_g, matchColor1_b, -1, -1, -1);
        ColorWheelComplementary.setMaskType(3);
    }

    private void initTheoryMonochromatic() {
        // Monochromatic: two colors, HSL space, L+-0.1 on origin.
        // if L > 0.5, L - 0.1.   L <= 0.5, L + 0.1.
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0;
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        if (temp_L > 0.5) {
            convertHSLtoRGB(temp_H, temp_S, temp_L - 0.1);
        } else {
            convertHSLtoRGB(temp_H, temp_S, temp_L + 0.1);
        } // Matching color1 stored in temp_RGB
        // set text view
        MonochromaticOriginalColorName.setText(colorname);
        MonochromaticOriginalColorValue.setText("(" + String.valueOf(detectedColor[0]) + ", "
                + String.valueOf(detectedColor[1]) + ", " +String.valueOf(detectedColor[2]) + ")");
        MonochromaticColor1Name.setText(GetColorName.getColorFromRGB((int)temp_R, (int)temp_G,
                (int)temp_B, false));
        MonochromaticColor1Value.setText("(" + String.valueOf((int)temp_R) + ", "
                + String.valueOf((int)temp_G) + ", " +String.valueOf((int)temp_B) + ")");
        // set sample block
        MonochromaticOriginalColorView.setBackgroundColor(Color.rgb(detectedColor[0],
                detectedColor[1], detectedColor[2]));
        MonochromaticColor1View.setBackgroundColor(Color.rgb((int)temp_R, (int)temp_G, (int)temp_B));
        // set color wheel
        ColorWheelMonochromatic.setTheoryNum(2);
        initSmallColorWheel(2, (int)temp_R, (int)temp_G, (int)temp_B, -1, -1, -1);
        ColorWheelMonochromatic.setMaskType(3);
    }

    private void initTheoryAnalogous() {
        // Analogous: three colors, HSL space, H+-1/12
        // if H < 0, +1; H > 1, -1
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0; temp_H1 = 0; temp_H2 = 0;
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        temp_H1 = temp_H - 1.0 / 12;
        temp_H2 = temp_H + 1.0 / 12;
        if (temp_H1 < 0) temp_H1 ++;
        else if (temp_H1 > 1) temp_H1 --;
        if (temp_H2 < 0) temp_H2 ++;
        else if (temp_H2 > 1) temp_H2 --;
        convertHSLtoRGB(temp_H1, temp_S, temp_L);
        double temp_R1 = temp_R; double temp_G1 = temp_G; double temp_B1 = temp_B;
        convertHSLtoRGB(temp_H2, temp_S, temp_L);
        double temp_R2 = temp_R; double temp_G2 = temp_G; double temp_B2 = temp_B;
        // set text view
        AnalogousOriginalColorName.setText(colorname);
        AnalogousOriginalColorValue.setText("(" + String.valueOf(detectedColor[0]) + ", "
                + String.valueOf(detectedColor[1]) + ", " +String.valueOf(detectedColor[2]) + ")");
        AnalogousColor1Name.setText(GetColorName.getColorFromRGB((int)temp_R1, (int)temp_G1,
                (int)temp_B1, false));
        AnalogousColor1Value.setText("(" + String.valueOf((int)temp_R1) + ", "
                + String.valueOf((int)temp_G1) + ", " + String.valueOf((int)temp_B1) + ")");
        AnalogousColor2Name.setText(GetColorName.getColorFromRGB((int)temp_R2, (int)temp_G2,
                (int)temp_B2, false));
        AnalogousColor2Value.setText("(" + String.valueOf((int)temp_R2) + ", "
                + String.valueOf((int)temp_G2) + ", " + String.valueOf((int)temp_B2) + ")");
        // set sample block
        AnalogousOriginalColorView.setBackgroundColor(Color.rgb(detectedColor[0],
                detectedColor[1], detectedColor[2]));
        AnalogousColor1View.setBackgroundColor(Color.rgb((int)temp_R1, (int)temp_G1, (int)temp_B1));
        AnalogousColor2View.setBackgroundColor(Color.rgb((int)temp_R2, (int)temp_G2, (int)temp_B2));
        // set color wheel
        ColorWheelAnalogous.setTheoryNum(3);
        initSmallColorWheel(3, (int)temp_R1, (int)temp_G1, (int)temp_B1,
                (int)temp_R2, (int)temp_G2, (int)temp_B2);
        ColorWheelAnalogous.setMaskType(3);
    }

    private void initTheoryTriadic() {
        // Triadic: three colors, HSL color space, H + 1/3 + 1/3
        // H > 1, H--
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0; temp_H1 = 0; temp_H2 = 0;
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        temp_H1 = temp_H + 1.0 / 3;
        temp_H2 = temp_H + 2.0 / 3;
        if (temp_H1 > 1) temp_H1 --;
        if (temp_H2 > 1) temp_H2 --;
        convertHSLtoRGB(temp_H1, temp_S, temp_L);
        double temp_R1 = temp_R; double temp_G1 = temp_G; double temp_B1 = temp_B;
        convertHSLtoRGB(temp_H2, temp_S, temp_L);
        double temp_R2 = temp_R; double temp_G2 = temp_G; double temp_B2 = temp_B;
        // set text view
        TriadicOriginalColorName.setText(colorname);
        TriadicOriginalColorValue.setText("(" + String.valueOf(detectedColor[0]) + ", "
                + String.valueOf(detectedColor[1]) + ", " +String.valueOf(detectedColor[2]) + ")");
        TriadicColor1Name.setText(GetColorName.getColorFromRGB((int)temp_R1, (int)temp_G1,
                (int)temp_B1, false));
        TriadicColor1Value.setText("(" + String.valueOf((int)temp_R1) + ", "
                + String.valueOf((int)temp_G1) + ", " + String.valueOf((int)temp_B1) + ")");
        TriadicColor2Name.setText(GetColorName.getColorFromRGB((int)temp_R2, (int)temp_G2,
                (int)temp_B2, false));
        TriadicColor2Value.setText("(" + String.valueOf((int)temp_R2) + ", "
                + String.valueOf((int)temp_G2) + ", " + String.valueOf((int)temp_B2) + ")");
        // set sample block
        TriadicOriginalColorView.setBackgroundColor(Color.rgb(detectedColor[0],
                detectedColor[1], detectedColor[2]));
        TriadicColor1View.setBackgroundColor(Color.rgb((int)temp_R1, (int)temp_G1, (int)temp_B1));
        TriadicColor2View.setBackgroundColor(Color.rgb((int)temp_R2, (int)temp_G2, (int)temp_B2));
        // set color wheel
        ColorWheelTriadic.setTheoryNum(4);
        initSmallColorWheel(4, (int)temp_R1, (int)temp_G1, (int)temp_B1,
                (int)temp_R2, (int)temp_G2, (int)temp_B2);
        ColorWheelTriadic.setMaskType(3);
    }

    private void initSmallColorWheel (int theoryNum, int r1, int g1, int b1, int r2, int g2, int b2) {
        // reset temp
        temp_R = 0; temp_G = 0; temp_B = 0;
        temp_H = 0; temp_S = 0; temp_L = 0;
        // set coordinate of original color
        convertRGBtoHSL(detectedColor[0], detectedColor[1], detectedColor[2]);
        Point original = coordinateTransform(HSLtoRelativeCoordinate(temp_H, temp_S,
                false), false);
        // set coordinate of matching color
        if (theoryNum == 1) { // theory 1: complementary
            ColorWheelComplementary.setColor(original.x, original.y, 0);
            convertRGBtoHSL(r1, g1, b1);
            Point matchColor1 = coordinateTransform(HSLtoRelativeCoordinate(temp_H, temp_S,
                    false), false);
            ColorWheelComplementary.setColor(matchColor1.x, matchColor1.y, 1);
        } else if (theoryNum == 2) { // theory 2: complementary
            ColorWheelMonochromatic.setColor(original.x, original.y, 0);
            convertRGBtoHSL(r1, g1, b1);
            Point matchColor1 = coordinateTransform(HSLtoRelativeCoordinate(temp_H, temp_S,
                    false), false);
            ColorWheelMonochromatic.setColor(matchColor1.x, matchColor1.y, 1);
        } else if (theoryNum == 3) { // theory 3: analogous
            ColorWheelAnalogous.setColor(original.x, original.y, 0);
            convertRGBtoHSL(r1, g1, b1);
            Point matchColor1 = coordinateTransform(HSLtoRelativeCoordinate(temp_H1, temp_S,
                    false), false);
            ColorWheelAnalogous.setColor(matchColor1.x, matchColor1.y, 1);
            convertRGBtoHSL(r2, g2, b2);
            Point matchColor2 = coordinateTransform(HSLtoRelativeCoordinate(temp_H2, temp_S,
                    false), false);
            ColorWheelAnalogous.setColor(matchColor2.x, matchColor2.y, 2);
        } else if (theoryNum == 4) { // theory 3: triadic
            ColorWheelTriadic.setColor(original.x, original.y, 0);
            convertRGBtoHSL(r1, g1, b1);
            Point matchColor1 = coordinateTransform(HSLtoRelativeCoordinate(temp_H1, temp_S,
                    false), false);
            ColorWheelTriadic.setColor(matchColor1.x, matchColor1.y, 1);
            convertRGBtoHSL(r2, g2, b2);
            Point matchColor2 = coordinateTransform(HSLtoRelativeCoordinate(temp_H2, temp_S,
                    false), false);
            ColorWheelTriadic.setColor(matchColor2.x, matchColor2.y, 2);
        }
    }

    private Point HSLtoRelativeCoordinate (double H, double S, boolean ColorWheelType) {
        Point relativeCoordinate = new Point(-1, -1);
        int view_rows = 0;
        if (ColorWheelType) { // top color wheel, height DP = 330
            view_rows =  DpToPixel(DetailActivity.this, 330);
        } else { // bottom color wheel, width DP = 180
            view_rows =  DpToPixel(DetailActivity.this, 180);
        }
        Log.i("view_rows", String.valueOf(view_rows));
        relativeCoordinate.x = (int)(289.0 / 640 * view_rows * S * cos((0.5 - 2 * H) * PI));
        relativeCoordinate.y = (int)(289.0 / 640 * view_rows * S * sin((0.5 - 2 * H) * PI));
        Log.i("Relative Coordinate",String.valueOf(relativeCoordinate.x) + ", "
                + String.valueOf(relativeCoordinate.y));

        return relativeCoordinate;
    }

    private Point coordinateTransform (Point relativeCoordinate, boolean ColorWheelType) {
        int view_cols, view_rows = 0;
        if (ColorWheelType) {
            view_cols = DpToPixel(DetailActivity.this, 380);
            view_rows = DpToPixel(DetailActivity.this, 330);
        } else {
            view_cols = DpToPixel(DetailActivity.this, 180);
            view_rows = DpToPixel(DetailActivity.this, 180);
        }
        Point realCoordinate = new Point(-1, -1);
        realCoordinate.x = relativeCoordinate.x + view_cols / 2;
        realCoordinate.y = view_rows / 2 - relativeCoordinate.y;
        Log.i("Real Coordinate",String.valueOf(realCoordinate.x) + ", "
                + String.valueOf(realCoordinate.y));
        return realCoordinate;
    }

    private void convertRGBtoHSL (double R, double G, double B) {
        double var_R = R / 255;
        double var_G = G / 255;
        double var_B = B / 255;
        double var_Min = var_R;    //Min. value of RGB
        if (var_G < var_Min) var_Min = var_G;
        if (var_B < var_Min) var_Min = var_B;
        double var_Max = var_R;    //Max. value of RGB
        if (var_G > var_Max) var_Max = var_G;
        if (var_B > var_Max) var_Max = var_B;
        double del_Max = var_Max - var_Min;             //Delta RGB value
        temp_L = (var_Max + var_Min)/ 2;
        temp_H = 0; temp_S = 0;
        if (del_Max == 0) {   //This is a gray, no chroma...
            temp_H = 0;
            temp_S = 0;
        } else {           //Chromatic data...
            if (temp_L < 0.5) temp_S = del_Max / (var_Max + var_Min);
            else           temp_S = del_Max / (2 - var_Max - var_Min);
            double del_R = (((var_Max - var_R) / 6) + (del_Max / 2)) / del_Max;
            double del_G = (((var_Max - var_G) / 6) + (del_Max / 2)) / del_Max;
            double del_B = (((var_Max - var_B) / 6) + (del_Max / 2)) / del_Max;
            if      (var_R == var_Max) temp_H = del_B - del_G;
            else if (var_G == var_Max) temp_H = ( (double) 1 / 3 ) + del_R - del_B;
            else if (var_B == var_Max) temp_H = ( (double) 2 / 3 ) + del_G - del_R;
            if (temp_H < 0) temp_H += 1;
            if (temp_H > 1) temp_H -= 1;
        }
    }

    private void convertHSLtoRGB (double H, double S, double L) {
        // L = 0.5
        if (S == 0){
            temp_R = L * 255;
            temp_G = L * 255;
            temp_B = L * 255;
        } else {
            double var_1, var_2;
            if (L < 0.5) var_2 = L * (1 + S);
            else var_2 = (L + S) - S * L;
            var_1 = 2 * L - var_2;
            temp_R = 255 * Hue_2_RGB(var_1, var_2, H + 1.0 / 3);
            temp_G = 255 * Hue_2_RGB(var_1, var_2, H);
            temp_B = 255 * Hue_2_RGB(var_1, var_2, H - 1.0 / 3);
        }
    }

    private double Hue_2_RGB(double v1, double v2, double vH) {
        if (vH < 0) vH += 1;
        if (vH > 1) vH -= 1;
        if (6 * vH < 1) return (v1 + (v2 - v1) * 6 * vH);
        if (2 * vH < 1) return v2;
        if (3 * vH < 2) return (v1 + (v2 - v1) * (2.0 / 3 - vH) * 6);
        return v1;
    }

    private String convertRGBtoHEX(int R, int G, int B) {
        String r_str = Integer.toHexString(R);
        String g_str = Integer.toHexString(G);
        String b_str = Integer.toHexString(B);
        // Change "0""1"..."e""f" to "00""01"..."0e""0f"
        if (R < 16) r_str = "0" + r_str;
        if (G < 16) g_str = "0" + g_str;
        if (B < 16) b_str = "0" + b_str;
        Log.i("Hex",r_str + g_str + b_str);
        return r_str + g_str + b_str;
    }

    public void startMain(View view){
        startActivity(new Intent(this, MainPage.class));
    }

    public void startFeedback(View view){
        startActivity(new Intent(this, Feedback.class));
    }

}