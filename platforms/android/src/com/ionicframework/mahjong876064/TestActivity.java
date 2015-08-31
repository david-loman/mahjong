package com.ionicframework.mahjong876064;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linxiangpeng.mahjong.Mahjong;
import com.linxiangpeng.mahjong.MahjongRule;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class TestActivity extends Activity {

    private ImageView mImageView01, mImageView02, mImageView03, mImageView04, mImageView05, mImageView06, mImageView07, mImageView08,
            mImageView09, mImageView10, mImageView11, mImageView12, mImageView13, mImageView14;
    private TextView mResultTextview;
    private Button mButton;
    private int mCount =0;
    private ArrayList<Mahjong> mMahjongs;
    private List<ImageView> mImageViewList = new LinkedList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mResultTextview = (TextView) findViewById(R.id.reslut);
        mButton = (Button) findViewById(R.id.next);
        mImageView01 = (ImageView) findViewById(R.id.img_01);
        mImageViewList.add(mImageView01);
        mImageView02 = (ImageView) findViewById(R.id.img_02);
        mImageViewList.add(mImageView02);
        mImageView03 = (ImageView) findViewById(R.id.img_03);
        mImageViewList.add(mImageView03);
        mImageView04 = (ImageView) findViewById(R.id.img_04);
        mImageViewList.add(mImageView04);
        mImageView05 = (ImageView) findViewById(R.id.img_05);
        mImageViewList.add(mImageView05);
        mImageView06 = (ImageView) findViewById(R.id.img_06);
        mImageViewList.add(mImageView06);
        mImageView07 = (ImageView) findViewById(R.id.img_07);
        mImageViewList.add(mImageView07);
        mImageView08 = (ImageView) findViewById(R.id.img_08);
        mImageViewList.add(mImageView08);
        mImageView09 = (ImageView) findViewById(R.id.img_09);
        mImageViewList.add(mImageView09);
        mImageView10 = (ImageView) findViewById(R.id.img_10);
        mImageViewList.add(mImageView10);
        mImageView11 = (ImageView) findViewById(R.id.img_11);
        mImageViewList.add(mImageView11);
        mImageView12 = (ImageView) findViewById(R.id.img_12);
        mImageViewList.add(mImageView12);
        mImageView13 = (ImageView) findViewById(R.id.img_13);
        mImageViewList.add(mImageView13);
        mImageView14 = (ImageView) findViewById(R.id.img_14);
        mImageViewList.add(mImageView14);
    }

    @Override
    protected void onResume() {
        restart(mCount++);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(mCount++);
            }
        });
        super.onResume();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mImageViewList.get(msg.what).setImageBitmap((Bitmap) msg.obj);
            super.handleMessage(msg);
        }
    };

    public void restart(int count) {
        int [][] useful = {{1,1,1,2,2,2,5,6,7,9,9,14,15,13},
                {20,20,5,2,6,7,2,2,18,19,17,22,23,24},
                {1,1,1,1,2,3,6,9,7,3,3,15,11,6,16},
                {21,21,23,3,15,10,5,6,8,23,17,1,29,8},
                {1,2,3,4,5,6,7,8,9,1,1,2,2,3},
                {21,22,22,21,21,22,5,7,18,15,15,16,3,5,16,1},
                {10,10,10,20,20,20,30,30,30,12,14,13,5,9},
                {15,10,1,13,16,23,25,17,18,23,24,10,4,5},
                {12,13,25,15,6,2,6,2,1,15,15,16,17,17}};
        mMahjongs = new ArrayList<Mahjong>();
        if (count == 0) {
            for (int i = 0; i < 14; i++) {
                final int n = new Random().nextInt(28) + 1;
                Mahjong m = new Mahjong(n);
                mMahjongs.add(m);
                final String urlString = m.imgURL;
                final int number = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlString);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setConnectTimeout(2000);
                            httpURLConnection.setReadTimeout(2000);
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.connect();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            inputStream.close();
                            Message message = Message.obtain();
                            message.obj = bitmap;
                            message.what = number;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            Log.e("MAIN", "NUMBER :" + String.valueOf(n));
                            e.printStackTrace();
                        }
                    }
                }).start();
                MahjongRule mMahjongRule = new MahjongRule(mMahjongs);
                String reslut = new String("");
                if (mMahjongRule.checkHu()) {
                    reslut = "胡了";
                } else {
                    reslut = "很遗憾";
                }
                if (mMahjongRule.checkTing()){
                    reslut += "  : 听";
                }else{
                    reslut += " : 无结果";
                }
                mResultTextview.setText(reslut);
            }
        } else {
            for (int i = 0; i < 14; i++) {
                final int n = useful[count-1][i];
                Mahjong m = new Mahjong(n);
                mMahjongs.add(m);
                final String urlString = m.imgURL;
                final int number = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlString);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setConnectTimeout(2000);
                            httpURLConnection.setReadTimeout(2000);
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.connect();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            inputStream.close();
                            Message message = Message.obtain();
                            message.obj = bitmap;
                            message.what = number;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            Log.e("MAIN", "NUMBER :" + String.valueOf(n));
                            e.printStackTrace();
                        }
                    }
                }).start();
                MahjongRule mMahjongRule = new MahjongRule(mMahjongs);
                String reslut = new String("");
                if (mMahjongRule.checkHu()) {
                    reslut = "胡了";
                } else {
                    reslut = "很遗憾";
                }
                if (mMahjongRule.checkTing()){
                    reslut += "  : 听";
                }else{
                    reslut += " : 无结果";
                }
                mResultTextview.setText(reslut);
                Log.e("HAH","HA");
            }
        }
    }
}
