package com.relurori.reflection;

import java.util.List;

/* Use support.v4.app because we use FragmentActivity. 
 * Use FragmentActivity because we use FragmentPagerAdapter.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MainPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	public MainPagerAdapter(FragmentManager fm, List<Fragment> frags) {
		super(fm);
		fragments = frags;
	}


	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	
	@Override
	public int getCount() {
		return fragments.size();
	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
