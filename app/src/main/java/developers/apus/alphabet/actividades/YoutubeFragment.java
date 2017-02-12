package developers.apus.alphabet.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.HashMap;
import java.util.Map;

import developers.apus.alphabet.BuildConfig;
import developers.apus.alphabet.R;

public class YoutubeFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    public static final String YOUTUBE_API_KEY = "AIzaSyDpFw0GsVel2DSP-mMP_b_u1AUn2uI_cZE";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayer YPlayer;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    private Map<String,String> playList;
    private int positionActual;
    private AdView adView;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Context activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_youtube, container, false);

        adView = (AdView) rootView.findViewById(R.id.adView);

        AdRequest adRequest;

        if(BuildConfig.DEBUG){
            adRequest = new AdRequest.Builder()
                    .addTestDevice("E6D875D21E5D7044F76A3C6603BC25D6")//Lo
                    .addTestDevice("1D6E14D9D821973C13370F0C46ECD264")//Mi
                    .addTestDevice("04675459C2BE09CF506EDD1002143111")//Genymotion tablet
                    .addTestDevice("2911693A4370B61588F14C331189465F")//Nexus one
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
        }
        else{
            adRequest = new AdRequest.Builder().build();
        }

        adView.loadAd(adRequest);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YOUTUBE_API_KEY, this);

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        final ListView listView = (ListView) rootView.findViewById( R.id.listView1 );

        initPlayList();
        String[] values = getNameSongs();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values)
        {
            @Override
            public View getView(int position, View convertView,	ViewGroup parent)
            {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                reproducirVideo( position );
            }
        });
        return rootView;
    }

    private void initPlayList(){
        playList = new HashMap<>();
        playList.put("Vowels Song","2V4LZs3Lfwc"); //https://www.youtube.com/watch?v=2V4LZs3Lfwc
        playList.put("Short Vowels Song","hnVhx3vk1Jg"); //https://www.youtube.com/watch?v=hnVhx3vk1Jg
        playList.put("Apples and Bananas - Vowels Song","lNNMknj0PyE");//https://www.youtube.com/watch?v=lNNMknj0PyE
        playList.put("The Vowels Phonics Song","r-kfBpm6qPo");//https://www.youtube.com/watch?v=r-kfBpm6qPo
        playList.put("Short and Long Vowels","RBAxxzXh7Fc");//https://www.youtube.com/watch?v=RBAxxzXh7Fc
        playList.put("Phonics Song","P4c9ewnypCU");//https://www.youtube.com/watch?v=P4c9ewnypCU
    }

    private void reproducirVideo(int position) {
        String key = getNameSongs()[position];
        if(positionActual != position)
        {
            player.cueVideo(playList.get(key));
        }
        positionActual = position;
    }

    private String[] getNameSongs(){
        String[] names = new String[playList.size()];
        return playList.keySet().toArray( names );
    }

    private String[] getUrlSong(int position){
        String[] names = new String[playList.size()];

        return playList.keySet().toArray( names );
    }


    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            player.cueVideo(playList.get(getNameSongs()[0])); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            positionActual = 0;
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this.getActivity(), RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this.getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
*/
    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }

    @Override
    public void onPause()
    {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        adView.resume();
    }

    @Override
    public void onDestroy()
    {
        adView.destroy();
        super.onDestroy();
    }
}