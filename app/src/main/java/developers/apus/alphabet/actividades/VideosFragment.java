package developers.apus.alphabet.actividades;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import developers.apus.alphabet.R;

public class VideosFragment extends Fragment
{
    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_video, container, false);

                adView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice( AdRequest.DEVICE_ID_EMULATOR )
                .build();
        adView.loadAd(adRequest);

        VideoView videoView = (VideoView) rootView.findViewById(R.id.video);
        MediaController mc = new MediaController( getActivity() );

        videoView.setMediaController(mc);
        super.onCreate(savedInstanceState);

        final ListView listView = (ListView) rootView.findViewById(R.id.listView1);

        String[] values = new String[] {"alphabet", "Canci\u00f3n Alvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values)
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


        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                reproducirTabla( position, ( VideoView ) rootView.findViewById(R.id.video) );
            }
        });

        return rootView;

    }

    private void reproducirTabla( int videoSelect, VideoView mVideoView )
    {
        String uriPath = "";
        Uri uri = null;
        switch ( videoSelect )
        {
            case 0:
                uriPath = "android.resource://developers.apus.alphabet/"+R.raw.abc;
                uri = Uri.parse(uriPath);
                mVideoView.setVideoURI(uri);
                mVideoView.requestFocus();
                mVideoView.start();
                break;
            case 1:
                uriPath = "android.resource://developers.apus.alphabet/"+R.raw.alvin;
                uri = Uri.parse(uriPath);
                mVideoView.setVideoURI(uri);
                mVideoView.requestFocus();
                mVideoView.start();
                break;
            default:
                break;
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
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        adView.destroy();
        super.onDestroy();
    }

}

