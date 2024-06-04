package com.example.chat;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.blank_learn.dark.R;
//
//import io.agora.rtc2.Constants;
//import io.agora.rtc2.IRtcEngineEventHandler;
//import io.agora.rtc2.RtcEngine;
//import io.agora.rtc2.video.VideoCanvas;
//
//import io.agora.rtc2.video.VideoEncoderConfiguration;
//
//public class videocall_Activity extends AppCompatActivity {
//
//    private static final String YOUR_APP_ID = "e00a9b6269b341f7a2df0ba3f587782e";
//    private String id;
//
//    private RtcEngine rtcEngine;
//    private FrameLayout localVideoContainer;
//    private FrameLayout remoteVideoView;
//    private Intent intent;
//
//    private boolean isMuted = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_videocall);
//        intent = getIntent();
//
//        localVideoContainer = findViewById(R.id.local_video_container);
//        remoteVideoView = findViewById(R.id.remote_video_view);
//        id = intent.getStringExtra("idd");
//
//        initializeAgoraEngine();
//        setupLocalVideo();
//
//        findViewById(R.id.join_call_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                joinChannel();
//            }
//        });
//
//        findViewById(R.id.mute_audio_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleAudioMute();
//            }
//        });
//
//        findViewById(R.id.switch_camera_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchCamera();
//            }
//        });
//
//        findViewById(R.id.end_call_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endCall();
//            }
//        });
//
//        findViewById(R.id.share_screen).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startScreenSharing();
//            }
//        });
//    }
//
//    private void initializeAgoraEngine() {
//        try {
//            rtcEngine = RtcEngine.create(getApplicationContext(), YOUR_APP_ID, rtcEventHandler);
//            rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
//            rtcEngine.enableVideo();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setupLocalVideo() {
//        rtcEngine.enableLocalVideo(true);
//        rtcEngine.setupLocalVideo(new VideoCanvas(localVideoContainer, VideoCanvas.RENDER_MODE_FIT, 0));
//        rtcEngine.startPreview();
//    }
//
//    private void joinChannel() {
//        rtcEngine.joinChannel(null, id, null, 0);
//    }
//
//    private void toggleAudioMute() {
//        isMuted = !isMuted;
//        rtcEngine.muteLocalAudioStream(isMuted);
//    }
//
//    private void switchCamera() {
//        rtcEngine.switchCamera();
//    }
//
//    private void endCall() {
//        rtcEngine.leaveChannel();
//        rtcEngine.destroy();
//        finish();
//    }
//
////    private void startScreenSharing() {
////        try {
////            VideoEncoderConfiguration.VideoDimensionsBuilder builder = new VideoDimensionsBuilder();
////            builder.setWidth(1920).setHeight(1080);
////            VideoEncoderConfiguration config = new VideoEncoderConfiguration(builder.build(),
////                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
////                    VideoEncoderConfiguration.STANDARD_BITRATE,
////                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT);
////
////            rtcEngine.startScreenCapture(null, config);
////        } catch (Exception e) {
////            e.printStackTrace();
////            Toast.makeText(this, "Screen sharing failed", Toast.LENGTH_SHORT).show();
////        }
////    }
//
//    private final IRtcEngineEventHandler rtcEventHandler = new IRtcEngineEventHandler() {
//        @Override
//        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    setupRemoteVideo(uid);
//                }
//            });
//        }
//    };
//
//    private void setupRemoteVideo(int uid) {
//        rtcEngine.setupRemoteVideo(new VideoCanvas(remoteVideoView, VideoCanvas.RENDER_MODE_FIT, uid));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (rtcEngine != null) {
//            rtcEngine.leaveChannel();
//            rtcEngine.destroy();
//        }
//    }
//}
