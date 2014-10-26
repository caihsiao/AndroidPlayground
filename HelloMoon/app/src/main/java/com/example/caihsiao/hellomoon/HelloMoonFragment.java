package com.example.caihsiao.hellomoon;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelloMoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HelloMoonFragment extends Fragment {
    private Button mPlayButton;
    private Button mStopButton;

    private AudioPlayer mPlayer = new AudioPlayer();

//    // TODO: Rename and change types and number of parameters
//    public static HelloMoonFragment newInstance(String param1, String param2) {
//        HelloMoonFragment fragment = new HelloMoonFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//    public HelloMoonFragment() {
//        // Required empty public constructor
//    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button) view.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.play(getActivity());
            }
        });

        mStopButton = (Button) view.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }
}
