package com.relurori.reflection;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	private final String TAG = MainActivity.class.getSimpleName();

	private PagerAdapter mPagerAdapter = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragments);

		pre();
	}

	private void pre() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, InstructionFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectSourceFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectDestinationFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SyncFragment.class.getName()));
		
		mPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(mPagerAdapter);
	}

}
