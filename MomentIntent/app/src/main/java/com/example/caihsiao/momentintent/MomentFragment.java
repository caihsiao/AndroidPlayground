package com.example.caihsiao.momentintent;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.UUID;


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
    public static final String EXTRA_MOMENT_ID = "com.example.caihsiao.momentintent.moment_id";

    private Moment mMoment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MomentFragment.
     */
    public static MomentFragment newInstance(UUID momentId) {
        MomentFragment fragment = new MomentFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MOMENT_ID, momentId);
        fragment.setArguments(args);
        return fragment;
    }
    public MomentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mMoment = new Moment();
        // UUID momentId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_MOMENT_ID);
        UUID momentId = (UUID) getArguments().getSerializable(EXTRA_MOMENT_ID);
        mMoment = MomentLab.getInstance(getActivity()).getMoment(momentId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment, container, false);

        // Handle display date.
        Button dateField = (Button) view.findViewById(R.id.moment_date);
        dateField.setText(DateFormat.getDateTimeInstance().format(mMoment.getDate()));
        dateField.setEnabled(false);

        // Handle checkbox.
        CheckBox isPublic = (CheckBox) view.findViewById(R.id.is_public);
        isPublic.setChecked(mMoment.isPublic());
        isPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mMoment.setPublic(b);
          }
        });

        // Handle input title.
        EditText titleField = (EditText) view.findViewById(R.id.moment_title);
        titleField.setText(mMoment.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

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
