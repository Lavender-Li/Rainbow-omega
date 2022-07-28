package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
//import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

//import android.widget.Adapter;
//import android.widget.ArrayAdapter;

import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    private Button but_1;

    private int id=R.drawable.color_image_0;

    private int textID=0;
    private TextView colorName;
    private TextView colorEmotion1;
    private TextView colorEmotion2;

    private Spinner colorsSpinner;

    private Button playBtn1;
    private Button playBtn2;
    private int chordID=0;
    private SoundPool soundPool1;//声明一个SoundPool
    private int soundID1;//创建某个声音对应的音频ID
    private SoundPool soundPool2;//声明一个SoundPool
    private int soundID2;//创建某个声音对应的音频ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        playBtn1 = (Button) findViewById(R.id.playBtn1);
        playBtn1.setOnClickListener(this);
        playBtn2 = (Button) findViewById(R.id.playBtn2);
        playBtn2.setOnClickListener(this);

        colorName = findViewById(R.id.colorName);
        colorEmotion1 = findViewById(R.id.colorEmotion1);
        colorEmotion2 = findViewById(R.id.colorEmotion2);

        but_1=(Button) findViewById(R.id.but1);

        but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==R.drawable.color_image_0){
                    id=R.drawable.color_image_1;
                    textID = 1;
                    chordID= 1;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_1){
                    id=R.drawable.color_image_2;
                    textID = 2;
                    chordID= 2;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_2){
                    id=R.drawable.color_image_3;
                    textID = 3;
                    chordID= 3;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_3){
                    id=R.drawable.color_image_4;
                    textID = 4;
                    chordID= 4;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_4){
                    id=R.drawable.color_image_5;
                    textID = 5;
                    chordID= 5;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_5){
                    id=R.drawable.color_image_6;
                    textID = 6;
                    chordID= 6;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_6){
                    id=R.drawable.color_image_7;
                    textID = 7;
                    chordID= 7;
                    initSound();
                    initText();
                }
                else if(id==R.drawable.color_image_7) {
                    id = R.drawable.color_image_1;
                    textID = 1;
                    chordID = 1;
                    initSound();
                    initText();
                }
                else {
                    id=R.drawable.color_image_0;
                    textID = 0;
                    chordID= 0;
                    initSound();
                    initText();
                }
                but_1.setBackgroundResource(id);
            }
        });

//        ArrayList<String> list = new ArrayList<String>();
//        list.add("...");
//        list.add("Red");
//        list.add("Orange");
//        list.add("Yellow");
//        list.add("Green");
//        list.add("Blue");
//        list.add("Purple");
//        list.add("Pink");

        colorsSpinner = (Spinner) this.findViewById(R.id.colorsSpinner);

        colorsSpinner.setSelection(0);
        colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long long_id) {
                // TODO
                if (pos==1){
                    long_id=R.drawable.color_image_1;
                    textID = 1;
                    chordID= 1;
                    initSound();
                    initText();
                }
                else if(pos==2){
                    long_id=R.drawable.color_image_2;
                    textID = 2;
                    chordID= 2;
                    initSound();
                    initText();
                }
                else if(pos==3){
                    long_id=R.drawable.color_image_3;
                    textID = 3;
                    chordID= 3;
                    initSound();
                    initText();
                }
                else if(pos==4){
                    long_id=R.drawable.color_image_4;
                    textID = 4;
                    chordID= 4;
                    initSound();
                    initText();
                }
                else if(pos==5){
                    long_id=R.drawable.color_image_5;
                    textID = 5;
                    chordID= 5;
                    initSound();
                    initText();
                }
                else if(pos==6){
                    long_id=R.drawable.color_image_6;
                    textID = 6;
                    chordID= 6;
                    initSound();
                    initText();
                }
                else if(pos==7){
                    long_id=R.drawable.color_image_7;
                    textID = 7;
                    chordID= 7;
                    initSound();
                    initText();
                }
                else{
                    long_id=R.drawable.color_image_0;
                    textID = 0;
                    chordID= 0;
                    initSound();
                    initText();
                }
                id = (int) long_id;
                but_1.setBackgroundResource(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });


    }

    @SuppressLint("NewApi")
    private void initSound() {
        soundPool1 = new SoundPool.Builder().build();
        switch (chordID){
            case 1:
                soundID1 = soundPool1.load(this, R.raw.playa_1, 1);
                break;
            case 2:
                soundID1 = soundPool1.load(this, R.raw.playb_1, 1);
                break;
            case 3:
                soundID1 = soundPool1.load(this, R.raw.playc_1, 1);
                break;
            case 4:
                soundID1 = soundPool1.load(this, R.raw.playd_1, 1);
                break;
            case 5:
                soundID1 = soundPool1.load(this, R.raw.playe_1, 1);
                break;
            case 6:
                soundID1 = soundPool1.load(this, R.raw.playf_1, 1);
                break;
            case 7:
                soundID1 = soundPool1.load(this, R.raw.playg_1, 1);
                break;
            default:
                soundID1 = soundPool1.load(this, R.raw.play_0, 1);
        }

        soundPool2 = new SoundPool.Builder().build();
        switch (chordID){
            case 1:
                soundID2 = soundPool2.load(this, R.raw.playa_2, 1);
                break;
            case 2:
                soundID2 = soundPool2.load(this, R.raw.play_0, 1);
                break;
            case 3:
                soundID2 = soundPool2.load(this, R.raw.play_0, 1);
                break;
            case 4:
                soundID2 = soundPool2.load(this, R.raw.playd_2, 1);
                break;
            case 5:
                soundID2 = soundPool2.load(this, R.raw.playe_2, 1);
                break;
            case 6:
                soundID2 = soundPool2.load(this, R.raw.play_0, 1);
                break;
            case 7:
                soundID2 = soundPool2.load(this, R.raw.play_0, 1);
                break;
            default:
                soundID2 = soundPool2.load(this, R.raw.play_0, 1);
        }
    }

    @SuppressLint("NewApi")
    private void initText() {
        switch (textID){
            case 1:
                colorName.setText("Red");
                colorEmotion1.setText("Red: The color of passion and energy. Usually, red draws attention like no other color and radiates a strong and powerful energy that motivates us to take action.");
                colorEmotion2.setText("Red can also mean danger or anger. Red is ubiquitously used to warn and signal caution and danger.");
                break;
            case 2:
                colorName.setText("Orange");
                colorEmotion1.setText("Orange: The color of enthusiasm and emotion. Orange exudes warmth and joy and is considered a fun color that provides emotional strength. It is optimistic and upliftning. It is a youthful and energetic color.");
                colorEmotion2.setText("...");
                break;
            case 3:
                colorName.setText("Yellow");
                colorEmotion1.setText("Yellow: The color of happiness and optimism. Yellow is a cheerful and energetic color that brings fun and joy to the world. It inspires thought and curiosity and boosts enthusiasm and confidence.");
                colorEmotion2.setText("...");
                break;
            case 4:
                colorName.setText("Green");
                colorEmotion1.setText("Green: The color of harmony and health. Green is a generous, relaxing color that revitalizes our body and mind. It leaves us feeling safe and secure. It promises growth and prosperity, and it provides luck.");
                colorEmotion2.setText("Occasionally green can be disgusting. And some believe that green is a symbol of greed.");
                break;
            case 5:
                colorName.setText("Blue");
                colorEmotion1.setText("Blue: The color of trust and loyalty. Blue has a calming and relaxing effect on our psyche, that gives us peace and makes us feel confident and secure. It is an honest, reliable and responsible color.");
                colorEmotion2.setText("However, blue sometimes is considered a symbol of melancholy and depression.");
                break;
            case 6:
                colorName.setText("Purple");
                colorEmotion1.setText("Purple: The color of spirituality and imagination. Purple inspires us to divulge our innermost thoughts. It is often associated with royalty and luxury, and its mystery and magic sparks creative fantasies.");
                colorEmotion2.setText("...");
                break;
            case 7:
                colorName.setText("Pink");
                colorEmotion1.setText("Pink: The color of love and compassion. Pink is kind and comforting, full of sympathy and compassion, and makes us feel accepted. Its friendly, playful spirit calms and nurtures us. Pink is  bursting with pure romance.");
                colorEmotion2.setText("...");
                break;
            default:
                colorName.setText("...");
                colorEmotion1.setText("...");
                colorEmotion2.setText("...");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playBtn1:
                playSound1();
                break;
            case R.id.playBtn2:
                playSound2();
                break;
        }
    }
    private void playSound1() {
        soundPool1.play(
                soundID1,
                0.5f, //左耳道音量【0~1】
                0.5f, //右耳道音量【0~1】
                0, //播放优先级【0表示最低优先级】
                0, //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1 //播放速度【1是正常，范围从0~2】
        );
    }
    private void playSound2() {
        soundPool2.play(
                soundID2,
                0.5f, //左耳道音量【0~1】
                0.5f, //右耳道音量【0~1】
                0, //播放优先级【0表示最低优先级】
                0, //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1 //播放速度【1是正常，范围从0~2】
        );
    }

    public void startMain(View view){
        startActivity(new Intent(this, MainPage.class));
    }

    public void startFeedback(View view){
        startActivity(new Intent(this, Feedback.class));
    }
}