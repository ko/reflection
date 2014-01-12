package com.relurori.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.relurori.reflection.usbdevice.ProcMountsLine;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	private final String TAG = MainActivity.class.getSimpleName();

	private PagerAdapter mPagerAdapter = null;
	private MyViewPager pager = null;
	
	private static String srcPath = null;
	private static String dstPath = null;
	
	private static List<ProcMountsLine> preLines = null;
	private static List<ProcMountsLine> srcLines = null;
	private static List<ProcMountsLine> dstLines = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragments);

		pre();
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		
		final int MENU_PREFERENCES = Menu.FIRST;
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		/*
		menu.add(0, MENU_PREFERENCES, 0, "Restart")
			.setIcon(android.R.drawable.ic_menu_revert);
		*/
		
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		int menuId = item.getItemId();
		
		switch(menuId) {
		case R.id.action_settings:
			break;
		case R.id.action_restart:
			restartSteps();
			break;
		}
		return true;
	}

	private void restartSteps() {
		// TODO Auto-generated method stub
		
		DialogFragment f = RestartDialogFragment.newInstance();
		f.show(super.getSupportFragmentManager(), RestartDialogFragment.TAG);
		
		pager.setCurrentItem(MainConstants.PAGER_SRC_INDEX);
		MainActivity.resetSrcDst();
		
		/*
		MainActivity.setPre("");
		MainActivity.setPreList(null);
		*/
	}
	
	public static void resetSrcDst() {
		
		MainActivity.setSrcLines(null);
		MainActivity.setDstLines(null);
		
	}

	private void pre() {
		Log.d(TAG,"0");
		List<Fragment> fragments = new ArrayList<Fragment>();

		Log.d(TAG,"2");
		fragments.add(Fragment.instantiate(this, SelectSourceFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectDestinationFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SyncFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SyncDoneFragment.class.getName()));
		
		Log.d(TAG,"3");
		mPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		Log.d(TAG,"4");
		pager = (MyViewPager)super.findViewById(R.id.viewpager);
		
		Log.d(TAG,"5");
		pager.setAdapter(mPagerAdapter);
		
		Log.d(TAG,"6");
	}
	
	
	/***************************** String paths ********************************/
	public static void setSrc(String s) {
		srcPath = s;
	}
	
	public static String getSrc() {
		return srcPath;
	}
	
	public static void setDst(String s) {
		dstPath = s;
	}
	
	public static String getDst() {
		return dstPath;
	}
	
	/***************************** List<ProcMountsLine> ************************/
	
	public static void setPreLines(List<ProcMountsLine> lines) {
		preLines = lines;
	}
	
	public static List<ProcMountsLine> getPreLines() {
		return preLines;
	}
	
	public static void setSrcLines(List<ProcMountsLine> lines) {
		srcLines = lines;
		setSrc(lines.get(0).getMountPoint());
	}
	
	public static List<ProcMountsLine> getSrcLines() {
		return srcLines;
	}
	
	public static void setDstLines(List<ProcMountsLine> lines) {
		dstLines = lines;
		setDst(lines.get(0).getMountPoint());
	}
	
	public static List<ProcMountsLine> getDstLines() {
		return dstLines;
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

	public void updateProgressDialog(int d, int currentFile, int totalFile) {
		FragmentManager fm = super.getSupportFragmentManager();
		Fragment f = fm.findFragmentByTag(TAG);
		
		((CopyDialogFragment)f).updateProgress(d, currentFile, totalFile);
	}

	
}
