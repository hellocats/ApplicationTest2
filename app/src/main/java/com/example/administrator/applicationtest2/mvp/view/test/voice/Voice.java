package com.example.administrator.applicationtest2.mvp.view.test.voice;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.common.voice.AudioRecorderButton;
import com.example.administrator.applicationtest2.common.voice.MediaManager;
import com.example.administrator.applicationtest2.common.voice.Recorder;
import com.example.administrator.applicationtest2.common.voice.RecorderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hepeng on 2017-02-22.
 */
public class Voice extends BaseClsActivity {
    private ListView mListView;
    private ArrayAdapter<Recorder> mAdapter;
    private List<Recorder> mDatas = new ArrayList<Recorder>();

    private AudioRecorderButton mAudioRecorderButton;

    private View animaView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_voice_main);
        //初始化控件
        init();
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.id_listview);
        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_recorder_button);
        mAudioRecorderButton.setOnAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                Recorder recorder = new Recorder(seconds,filePath);
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mDatas.size() - 1);
            }
        });

        mAdapter = new RecorderAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(animaView != null){
                    animaView.setBackgroundResource(R.drawable.adj);
                    animaView = null;
                }
                //播放动画
                animaView = view.findViewById(R.id.id_recorder_anima);
                animaView.setBackgroundResource(R.drawable.play_anima);
                AnimationDrawable anima = (AnimationDrawable) animaView.getBackground();
                anima.start();
                //播放音频
                MediaManager.playSound(mDatas.get(position).filePath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        animaView.setBackgroundResource(R.drawable.adj);
                    }
                });
            }
        });
    }


}
