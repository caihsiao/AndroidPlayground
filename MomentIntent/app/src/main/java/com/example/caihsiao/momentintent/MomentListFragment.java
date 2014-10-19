package com.example.caihsiao.momentintent;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MomentListFragment extends ListFragment {

    private static final String TAG = "MomentListFragment";

    private ArrayList<Moment> mMoments;

    // private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.moment_title);
        mMoments = MomentLab.getInstance(getActivity()).getMoments();

        // TODO: Change Adapter to display your content
        setListAdapter(new MomentAdapter(mMoments));
    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Moment moment = ((MomentAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, moment.getTitle() + " was clicked!");

        Intent intent = new Intent(getActivity(), MomentActivity.class);
        intent.putExtra(MomentFragment.EXTRA_MOMENT_ID, moment.getId());
        startActivity(intent);

//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
    }

    private class MomentAdapter extends ArrayAdapter<Moment> {
      private MomentAdapter(ArrayList<Moment> moments) {
        super(getActivity(), 0, moments);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
          convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_moment, null);
        }
        Moment moment = getItem(position);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.moment_list_item_titleTextView);
        titleTextView.setText(moment.getTitle());
        TextView dateTextView = (TextView) convertView.findViewById(R.id.moment_list_item_dateTextView);
        dateTextView.setText(moment.getDate().toString());
        CheckBox publicCheckBox = (CheckBox) convertView.findViewById(R.id.moment_list_item_publicCheckbox);
        publicCheckBox.setChecked(moment.isPublic());

        return convertView;
      }
    }
//
//    /**
//    * This interface must be implemented by activities that contain this
//    * fragment to allow an interaction in this fragment to be communicated
//    * to the activity and potentially other fragments contained in that
//    * activity.
//    * <p>
//    * See the Android Training lesson <a href=
//    * "http://developer.android.com/training/basics/fragments/communicating.html"
//    * >Communicating with Other Fragments</a> for more information.
//    */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
//    }

}
