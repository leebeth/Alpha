package developers.apus.alphabet.actividades;

import android.content.Context;
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
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

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
        playList.put("The Alphabet Chant","aEYcmNhz7Uc"); //https://www.youtube.com/watch?v=aEYcmNhz7Uc
        playList.put("Phonics Song with TWO Words","hq3yfQnllfQ"); //https://www.youtube.com/watch?v=hq3yfQnllfQ
        playList.put("ABC Song","zAlX1V3lK5s"); //https://www.youtube.com/watch?v=zAlX1V3lK5s
        playList.put("Alphabet Song","Q8JmK5z6QD4"); //https://www.youtube.com/watch?v=Q8JmK5z6QD4
        playList.put("Sesame Street","ML8IL77gQ3k"); //https://www.youtube.com/watch?v=ML8IL77gQ3k
        playList.put("Sing the Alphabet Song!","783EsrHchXA"); //https://www.youtube.com/watch?v=783EsrHchXA
        playList.put("Usher's ABC Song","SWvBAQf7v8g"); //https://www.youtube.com/watch?v=SWvBAQf7v8g
        playList.put("The Alphabet Chant 2","cR-Qr1V8e_w"); //https://www.youtube.com/watch?v=cR-Qr1V8e_w
        playList.put("ABC Songs for Children","B5csN8gQY4E"); //https://www.youtube.com/watch?v=B5csN8gQY4E

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
            player.cueVideo(playList.get(getNameSongs()[0])); // Plays https://www.youtube.com/watch?v=zAlX1V3lK5s
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