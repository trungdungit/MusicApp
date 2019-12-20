package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView txtviewTimeSong, txtviewTimeTotal, txtviewTitle;
    SeekBar skSong;
    ImageView imgDisc;
    ImageButton btnPrev, btnPlay, btnNext, btnStop;
    ArrayList<Song> arraySong;
    int position=0;
    MediaPlayer mediaPlayer;
    Animation animation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddSong();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        KhoiTaoMediaPlayer();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size()-1){
                    position=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position <0){
                    position = arraySong.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                UpdateTimeSong();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                setTimeTotal();
                UpdateTimeSong();
                imgDisc.startAnimation(animation);
            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });

    }
    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdangGio = new SimpleDateFormat("mm:ss");
                txtviewTimeSong.setText(dinhdangGio.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size()-1){
                            position=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        setTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }
    private void setTimeTotal(){
        SimpleDateFormat dinhdangGio = new SimpleDateFormat("mm:ss");
        txtviewTimeTotal.setText(dinhdangGio.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile() );
        txtviewTitle .setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("demo",R.raw.demo));
        arraySong.add(new Song("exp",R.raw.exp));
        arraySong.add(new Song("house",R.raw.house));
        arraySong.add(new Song("house_rental",R.raw.house_rental));
        arraySong.add(new Song("test",R.raw.test));
        arraySong.add(new Song("text",R.raw.text));
    }

    private void AnhXa() {
        txtviewTimeSong = (TextView) findViewById(R.id.textviewTimeSong);
        txtviewTimeTotal =(TextView) findViewById(R.id.textviewTimeTotal);
        txtviewTitle = (TextView) findViewById(R.id.textviewTitle);
        skSong =(SeekBar) findViewById(R.id.seekBar);
        btnNext =(ImageButton) findViewById(R.id.imageButtonNext);
        btnPlay =(ImageButton) findViewById(R.id.imageButtonPlay);
        btnPrev =(ImageButton) findViewById(R.id.imageButtonPrev);
        btnStop =(ImageButton) findViewById(R.id.imageButtonStop);
        imgDisc = (ImageView) findViewById(R.id.imageViewDisc);
    }
}
