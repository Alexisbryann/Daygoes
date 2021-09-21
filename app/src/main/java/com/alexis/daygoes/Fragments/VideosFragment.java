package com.alexis.daygoes.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexis.daygoes.R;

public class VideosFragment extends Fragment {
    private VideoView videoView;
    private EditText uriEditText;
    private Button playButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_videos, container, false);

        videoView = view.findViewById(R.id.fragment_webvideo_videoView);
        playButton = view.findViewById(R.id.fragment_webvideo_play);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideoFromWeb();
            }
        });

        return view;
    }

    private void playVideoFromWeb() {
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/simple-shopping-290e2.appspot.com/o/ngav.mp4?alt=media&token=13e6858a-7993-4cbc-a341-8ba4b0042d2d");
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
    }


    @Override
    public void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }
}