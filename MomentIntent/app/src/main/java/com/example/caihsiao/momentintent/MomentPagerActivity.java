package com.example.caihsiao.momentintent;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
// import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by caihsiao on 10/21/14.
 */
public class MomentPagerActivity extends Activity {
    private ViewPager mViewPager;
    private ArrayList<Moment> mMoments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mMoments = MomentLab.getInstance(this).getMoments();

        FragmentManager fm = getFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public android.app.Fragment getItem(int position) {
                Moment moment = mMoments.get(position);
                return MomentFragment.newInstance(moment.getId());
            }

            @Override
            public int getCount() {
                return mMoments.size();
            }
        });

        UUID momentId = (UUID) getIntent().getSerializableExtra(MomentFragment.EXTRA_MOMENT_ID);
        for (int i = 0; i < mMoments.size(); ++i) {
            if (mMoments.get(i).getId().equals(momentId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Moment moment = mMoments.get(i);
                if (moment.getTitle() != null) {
                    setTitle(moment.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
