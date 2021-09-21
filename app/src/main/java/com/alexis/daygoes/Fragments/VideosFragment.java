package com.alexis.daygoes.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexis.daygoes.R;

public class VideosFragment extends Fragment {

    private static final String VIDEO_SAMPLE =
            "gs://simple-shopping-290e2.appspot.com/ngav.mp4";

    private VideoView mVideoView;
    private TextView mBufferingTextView;

    // Current playback position (in milliseconds).  
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.  
    private static final String PLAYBACK_TIME = "play_time";

    public VideosFragment() {
    }

    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_videos, container, false);

        mVideoView = mView.findViewById(R.id.videoview);
        mBufferingTextView = mView.findViewById(R.id.buffering_textview);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.  
        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Load the media each time onStart() is called.  
        initializePlayer();
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
            mVideoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be  
        // stopped and released at this time.  
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the  
        // instance state bundle.  
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.  
        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.  
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).  
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.  
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.  
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.  
                            mVideoView.seekTo(1);
                        }

                        // Start playing!  
                        mVideoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished  
        // playing).  
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getContext(),
                                "Video completed",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.  
                        mVideoView.seekTo(0);
                    }
                });
    }


    // Release all media-related resources. In a more complicated app this  
    // might involve unregistering listeners or releasing audio focus.  
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is  
    // embedded in the app resources or available on the internet.  
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.  
            return Uri.parse(mediaName);
        }
        return null;
    }

}  