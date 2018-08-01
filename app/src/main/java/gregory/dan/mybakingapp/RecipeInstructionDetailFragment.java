package gregory.dan.mybakingapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;

public class RecipeInstructionDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String RECIPE_STEP = "recipe_step";
    public static final String RECIPE_NAME = "recipe_name";


    private ArrayList<CookingStep> mCookingStep;
    private int cookingStepNumber;
    private Button previousButton;
    private Button nextButton;
    private TextView detailsTextView;
    Activity activity;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer player;
    Uri videoUri;
    long position;


    public RecipeInstructionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = C.TIME_UNSET;
        if(savedInstanceState != null){
            mCookingStep = savedInstanceState.getParcelableArrayList("current_recipe");
            cookingStepNumber = savedInstanceState.getInt("current_step");
            position = savedInstanceState.getLong("selected_position", C.TIME_UNSET);
        }else{
            mCookingStep = getArguments().getParcelableArrayList(ARG_ITEM_ID);
            cookingStepNumber = getArguments().getInt(RECIPE_STEP);
        }


        activity = this.getActivity();

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_step", cookingStepNumber);
        outState.putParcelableArrayList("current_recipe", mCookingStep);
        if(player != null){
            outState.putLong("selected_position", position);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipeinstruction_detail, container, false);

        previousButton = rootView.findViewById(R.id.previous_step_button);
        nextButton = rootView.findViewById(R.id.next_step_button);
        detailsTextView = rootView.findViewById(R.id.detail_step_text_view);
        simpleExoPlayerView = rootView.findViewById(R.id.exo_player_view);

        // Show the dummy content as text in a TextView.
        if (mCookingStep != null) {
            detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cookingStepNumber > 0) {
                    player.setPlayWhenReady(false);
                    cookingStepNumber -= 1;
                    detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
                    checkAndPlayVideo();
                } else {
                    player.setPlayWhenReady(false);
                    cookingStepNumber = mCookingStep.size() - 1;
                    detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
                    checkAndPlayVideo();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cookingStepNumber < mCookingStep.size() - 1) {
                    player.setPlayWhenReady(false);
                    cookingStepNumber += 1;
                    detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
                    checkAndPlayVideo();
                } else {
                    player.setPlayWhenReady(false);
                    cookingStepNumber = 0;
                    detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
                    checkAndPlayVideo();
                }
            }
        });
        simpleExoPlayerView.setPlayer(player);

        checkAndPlayVideo();

        return rootView;
    }

    private void checkAndPlayVideo() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!mCookingStep.get(cookingStepNumber).getVideoUrl().equals("")) {
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                videoUri = Uri.parse(mCookingStep.get(cookingStepNumber).getVideoUrl());
                playVideo(videoUri);
            } else if (!mCookingStep.get(cookingStepNumber).getThumbnailUrl().equals("")) {
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                videoUri = Uri.parse(mCookingStep.get(cookingStepNumber).getThumbnailUrl());
                playVideo(videoUri);
            } else {
                simpleExoPlayerView.setVisibility(View.GONE);
            }
        } else {
            if (!mCookingStep.get(cookingStepNumber).getVideoUrl().equals("")) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                hideButtons();
                videoUri = Uri.parse(mCookingStep.get(cookingStepNumber).getVideoUrl());
                playVideo(videoUri);
            } else if (!mCookingStep.get(cookingStepNumber).getThumbnailUrl().equals("")) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                hideButtons();
                videoUri = Uri.parse(mCookingStep.get(cookingStepNumber).getThumbnailUrl());
                playVideo(videoUri);
            } else {
                simpleExoPlayerView.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                previousButton.setVisibility(View.VISIBLE);
                detailsTextView.setVisibility(View.VISIBLE);
                detailsTextView.setText(mCookingStep.get(cookingStepNumber).getDescription());
            }
        }

    }

    private void hideButtons(){
        nextButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.GONE);
        detailsTextView.setVisibility(View.GONE);
    }

    private void playVideo(Uri uri) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "MyBakingApp"), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        if(position != C.TIME_UNSET){
            player.seekTo(position);
        }
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null){
            position = player.getCurrentPosition();
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
    }
}
