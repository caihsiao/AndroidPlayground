package com.example.caihsiao.momentintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.util.Date;
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
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final String TAG = "MomentFragment";

    private Moment mMoment;
    private Button mDateField;
    private ImageButton mPhotoButton;

    private void updateDate() {
        mDateField.setText(mMoment.getDate().toString());
    }

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
        setHasOptionsMenu(true);
        // mMoment = new Moment();
        // UUID momentId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_MOMENT_ID);
        UUID momentId = (UUID) getArguments().getSerializable(EXTRA_MOMENT_ID);
        mMoment = MomentLab.getInstance(getActivity()).getMoment(momentId);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment, container, false);

        // show up navigation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        // Handle display date.
        mDateField = (Button) view.findViewById(R.id.moment_date);
        // mDateField.setText(DateFormat.getDateTimeInstance().format(mMoment.getDate()));
        updateDate();
        // dateField.setEnabled(false);
        mDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mMoment.getDate());
                dialog.setTargetFragment(MomentFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

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

        mPhotoButton = (ImageButton) view.findViewById(R.id.moment_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MomentCameraActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mMoment.setDate(date);
            updateDate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MomentLab.getInstance(getActivity()).saveMoments();
    }
}
