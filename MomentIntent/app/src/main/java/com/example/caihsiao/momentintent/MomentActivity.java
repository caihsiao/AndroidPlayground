package com.example.caihsiao.momentintent;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public class MomentActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
      UUID momentId = (UUID) getIntent().getSerializableExtra(MomentFragment.EXTRA_MOMENT_ID);
      return MomentFragment.newInstance(momentId);
  }

//  @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        FragmentManager fm = getFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
//
//        if (fragment == null) {
//          fragment = new MomentFragment();
//          fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_moment, container, false);
            return rootView;
        }
    }
}
