package com.example.caihsiao.momentintent;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private boolean mSubtitleVisible;

    private ArrayList<Moment> mMoments;

    // private OnFragmentInteractionListener mListener;


    public MomentListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMoments = MomentLab.getInstance(getActivity()).getMoments();

        setRetainInstance(true);
        mSubtitleVisible = false;

        // TODO: Change Adapter to display your content
        setListAdapter(new MomentAdapter(mMoments));
    }

    @TargetApi(11)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The activity title has to be set here instead of onCreate, because
        // when rotated this fragment is retained so onCreate will not be called again, but the
        // activity hosting this fragment has been destroyed. In the future we should put all the
        // codes that change the activity's behavior in onCreateView.
        getActivity().setTitle(R.string.moment_title);
        // View view = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.empty_frame, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
            // Use contextual action bar on Honeycomb and higher.
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                    // Not used in this implementation.
                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    MenuInflater inflater1 = actionMode.getMenuInflater();
                    inflater1.inflate(R.menu.moment_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    // not implemented
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_item_delete_moment:
                            MomentAdapter adapter = (MomentAdapter) getListAdapter();
                            MomentLab momentLab = MomentLab.getInstance(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; --i) {
                                if (getListView().isItemChecked(i)) {
                                    momentLab.deleteMoment(adapter.getItem(i));
                                }
                            }
                            actionMode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {

                }
            });
        }




        // The following two lines can be omitted because of the magic variable
        // android.R.id.list and R.id.empty
//        ListView listView = (ListView) view.findViewById(android.R.id.list);
//        listView.setEmptyView(view.findViewById(R.id.empty));
        Button button = (Button) view.findViewById(R.id.add_first_moment_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Moment moment = new Moment();
                MomentLab.getInstance(getActivity()).addMoment(moment);
                Intent intent = new Intent(getActivity(), MomentPagerActivity.class);
                intent.putExtra(MomentFragment.EXTRA_MOMENT_ID, moment.getId());
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MomentAdapter)getListAdapter()).notifyDataSetChanged();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_moment_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.memu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null){
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.moment_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        MomentAdapter adapter = (MomentAdapter) getListAdapter();
        Moment moment = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_moment:
                MomentLab.getInstance(getActivity()).deleteMoment(moment);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_moment:
                Moment moment = new Moment();
                MomentLab.getInstance(getActivity()).addMoment(moment);
                Intent intent = new Intent(getActivity(), MomentPagerActivity.class);
                intent.putExtra(MomentFragment.EXTRA_MOMENT_ID, moment.getId());
                startActivityForResult(intent, 0);
                return true;
            case R.id.memu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    item.setTitle(R.string.hide_subtitle);
                    mSubtitleVisible = true;
                } else {
                    getActivity().getActionBar().setSubtitle(null);
                    item.setTitle(R.string.show_subtitle);
                    mSubtitleVisible = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Moment moment = ((MomentAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, moment.getTitle() + " was clicked!");

        // Intent intent = new Intent(getActivity(), MomentActivity.class);
        Intent intent = new Intent(getActivity(), MomentPagerActivity.class);
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
