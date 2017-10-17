package com.piszoka.widecolorgamuttest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @ColorInt
    private final static int CHAPTER_COLOR_1 = 0xff90CAF9;
    @ColorInt
    private final static int CHAPTER_COLOR_2 = 0xffA5D6A7;
    @ColorInt
    private final static int CHAPTER_COLOR_3 = 0xffFFCC80;
    @ColorInt
    private final static int CHAPTER_COLOR_4 = 0xffCE93D8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    private void init() {
        final ViewGroup cradle = findViewById(R.id.cradle);

        addChapter(cradle,
                "Testcases 1",
                "* Image is loaded from res/drawable-nodpi\n* inPreferedConfig is not used",
                CHAPTER_COLOR_1);

        addImage(cradle, R.drawable.img_01_srgb, false, "1-a) sRGB", CHAPTER_COLOR_1);
        addImage(cradle, R.drawable.img_01_adobergb, false, "1-b) Adobe RGB", CHAPTER_COLOR_1);
        addImage(cradle, R.drawable.img_01_displayp3, false, "1-c) DisplayP3", CHAPTER_COLOR_1);
        addImage(cradle, R.drawable.img_01_rommrgb, false, "1-d) Romm RGB", CHAPTER_COLOR_1);
        addImage(cradle, R.drawable.img_01_prophotorgb, false, "1-e) ProPhot RGB", CHAPTER_COLOR_1);


        addChapter(cradle,
                "Testcases 2",
                "* Image is loaded from res/drawable-widecg-nodpi\n* inPreferedConfig is not used",
                CHAPTER_COLOR_2);

        addImage(cradle, R.drawable.img_02_srgb, false, "2-a) sRGB", CHAPTER_COLOR_2);
        addImage(cradle, R.drawable.img_02_adobergb, false, "2-b) Adobe RGB", CHAPTER_COLOR_2);
        addImage(cradle, R.drawable.img_02_displayp3, false, "2-c) DisplayP3", CHAPTER_COLOR_2);
        addImage(cradle, R.drawable.img_02_rommrgb, false, "2-d) Romm RGB", CHAPTER_COLOR_2);
        addImage(cradle, R.drawable.img_02_prophotorgb, false, "2-e) ProPhot RGB", CHAPTER_COLOR_2);


        addChapter(cradle,
                "Testcases 3",
                "* Image is loaded from res/drawable-nodpi\n* inPreferedConfig is set to RGBA_F16",
                CHAPTER_COLOR_3);

        addImage(cradle, R.drawable.img_01_srgb, true, "3-a) sRGB", CHAPTER_COLOR_3);
        addImage(cradle, R.drawable.img_01_adobergb, true, "3-b) Adobe RGB", CHAPTER_COLOR_3);
        addImage(cradle, R.drawable.img_01_displayp3, true, "3-c) DisplayP3", CHAPTER_COLOR_3);
        addImage(cradle, R.drawable.img_01_rommrgb, true, "3-d) Romm RGB", CHAPTER_COLOR_3);
        addImage(cradle, R.drawable.img_01_prophotorgb, true, "3-e) ProPhot RGB", CHAPTER_COLOR_3);


        addChapter(cradle,
                "Testcases 4",
                "* Image is loaded from res/drawable-widecg-nodpi\n* inPreferedConfig is set to RGBA_F16",
                CHAPTER_COLOR_4);

        addImage(cradle, R.drawable.img_02_srgb, true, "4-a) sRGB", CHAPTER_COLOR_4);
        addImage(cradle, R.drawable.img_02_adobergb, true, "4-b) Adobe RGB", CHAPTER_COLOR_4);
        addImage(cradle, R.drawable.img_02_displayp3, true, "4-c) DisplayP3", CHAPTER_COLOR_4);
        addImage(cradle, R.drawable.img_02_rommrgb, true, "4-d) Romm RGB", CHAPTER_COLOR_4);
        addImage(cradle, R.drawable.img_02_prophotorgb, true, "4-e) ProPhot RGB", CHAPTER_COLOR_4);
    }


    private void addChapter(
            @NonNull ViewGroup cradle,
            @NonNull String title,
            @NonNull String description,
            @ColorInt int titleBgColor) {

        View chapterView = getLayoutInflater().inflate(R.layout.chapter, cradle, false);
        TextView titleTv = chapterView.findViewById(R.id.title);
        TextView descrTv = chapterView.findViewById(R.id.description);

        titleTv.setText(title);
        titleTv.setBackgroundColor(titleBgColor);
        descrTv.setText(description);

        cradle.addView(chapterView);
    }


    @SuppressLint("SetTextI18n")
    private void addImage(
            @NonNull ViewGroup cradle,
            @DrawableRes int imageRes,
            boolean forceScRGB,
            String title,
            @ColorInt int titleBgColor) {

        View entryView = getLayoutInflater().inflate(R.layout.entry, cradle, false);
        TextView titleTv = entryView.findViewById(R.id.title);
        TextView text1Tv = entryView.findViewById(R.id.text1);
        TextView text2Tv = entryView.findViewById(R.id.text2);
        ImageView imageView = entryView.findViewById(R.id.image);

        titleTv.setText(title);
        titleTv.setBackgroundColor(titleBgColor);

        BitmapFactory.Options opts = new BitmapFactory.Options();

        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageRes, opts);

        text1Tv.setText("Before loading (just decode bounds)"
                + "\n* ColorSpace: "
                + opts.outColorSpace.getName()
                + "\n* Bitmap.Conf: "
                + opts.outConfig);


        opts.inJustDecodeBounds = false;
        if (forceScRGB) opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageRes, opts);
        imageView.setImageBitmap(bmp);
        cradle.addView(entryView);

        text2Tv.setText("After loading"
                + "\n* ColorSpace: "
                + bmp.getColorSpace().getName()
                + "\n* Bitmap.Conf: "
                + bmp.getConfig());
    }


}
