package com.example.fabien.myactivities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {

    public static final String KEY_DRAWABLE = "keyDrawable";

    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int progressStatus;
    private static int staticStatus;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        staticStatus = 0;

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus = doSomeWork();
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
                if (progressStatus == 100)
                {
                    handler.post(new Runnable()
                    {
                        public void run() {

                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            if (MainActivity.make == "Volvo")
                            {
                                imageView.setImageResource(R.drawable.voiture);
                            }

                            if (MainActivity.make == "Mini")
                            {
                                imageView.setImageResource(R.drawable.mini);
                            }
                            if (MainActivity.make == "Volskswagen")
                            {
                                imageView.setImageResource(R.drawable.volskswagen);
                            }
                        }
                    }
                    );
            }}

            private int doSomeWork()
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return ++staticStatus;
            }
        }).start();
    }

    public void onReturnClick(View v) {
        Intent intent = new Intent();
        if(MainActivity.make =="Volvo") {
            intent.putExtra(KEY_DRAWABLE, R.drawable.voiture);
        }
        if(MainActivity.make =="Volskswagen") {
            intent.putExtra(KEY_DRAWABLE, R.drawable.volskswagen);
        }
        else if (MainActivity.make =="Mini")
        {
            intent.putExtra(KEY_DRAWABLE, R.drawable.mini);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
