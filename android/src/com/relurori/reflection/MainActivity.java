package com.relurori.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	private final String TAG = MainActivity.class.getSimpleName();

	private PagerAdapter mPagerAdapter = null;
	private MyViewPager pager = null;
	
	private static String src = null;
	private static String dst = null;
	
	private static List<String> srcList = null;
	private static List<String> dstList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragments);

		pre();
	}

	private void pre() {
		Log.d(TAG,"0");
		List<Fragment> fragments = new ArrayList<Fragment>();

		Log.d(TAG,"2");
		fragments.add(Fragment.instantiate(this, SelectSourceFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectDestinationFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SyncFragment.class.getName()));
		
		Log.d(TAG,"3");
		mPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		Log.d(TAG,"4");
		pager = (MyViewPager)super.findViewById(R.id.viewpager);
		
		Log.d(TAG,"5");
		pager.setAdapter(mPagerAdapter);
		
		Log.d(TAG,"6");
	}
	
	public static void setSrc(String source) {
		src = source;
	}

	public static String getSrc() {
		return src;
	}
	
	public static void setDst(String destination) {
		dst = destination;
	}
	
	public static String getDst() {
		return dst;
	}
	
	public static void setSrcList(List<String> list) {
		srcList = list;
	}
	
	public static void setDstList(List<String> list) {
		dstList = list;
	}
	
	public static List<String> getSrcList() {
		return srcList;
	}
	
	public static List<String> getDstList() {
		return dstList;
	}
	
	public void showProgressDialog() {
		
		FragmentManager fm = super.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag(TAG);
		
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		DialogFragment frag = CopyDialogFragment.newInstance();
		frag.show(fm, TAG);
	}

	public void removeProgressDialog() {

		FragmentManager fm = super.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag(TAG);
		
		if (prev != null) {
			ft.remove(prev);
			((DialogFragment)prev).dismiss();
		}
		
		ft.commit();
		
		Log.d(TAG,"removeProgressDialog");
	}

	public void updateProgressDialog(double d) {
		FragmentManager fm = super.getSupportFragmentManager();
		Fragment f = fm.findFragmentByTag(TAG);
		
		((CopyDialogFragment)f).updateProgress(d);
	}
}
