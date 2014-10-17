package com.example.caihsiao.momentintent;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MomentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MomentFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MomentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Moment mMoment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MomentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MomentFragment newInstance(String param1, String param2) {
        MomentFragment fragment = new MomentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public MomentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoment = new Moment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment, container, false);

        EditText titleField = (EditText) view.findViewById(R.id.moment_title);
        titleField.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // Leave blank.
          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mMoment.setTitle(charSequence.toString());
          }

          @Override
          public void afterTextChanged(Editable editable) {
            // Leave blank.
          }
        });

        return view;
    }

}
