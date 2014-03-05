package com.relurori.reflection;

import java.util.List;

/* Use support.v4.app because we use FragmentActivity. 
 * Use FragmentActivity because we use FragmentPagerAdapter.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

public class MainPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	private static final String TAG = MainPagerAdapter.class.getCanonicalName();
	
	public MainPagerAdapter(FragmentManager fm, List<Fragment> frags) {
		super(fm);
		fragments = frags;
	}


	@Override
	public Fragment getItem(int position) {
		Log.d(TAG,"getItem|" + position);
		return fragments.get(position);
	}
	
	@Override
	public int getCount() {
		return fragments.size();
	}
}
