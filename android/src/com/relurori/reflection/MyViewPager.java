package com.relurori.reflection;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private boolean swipe = false;
	
	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	
	public void setSwipe(boolean b) {
		swipe = b;
	}
	
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (swipe) {
            return super.onInterceptTouchEvent(arg0);
        }

        // Never allow swiping to switch between pages
        return false;
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent event) {
    	if (swipe) {
    		return super.onTouchEvent(event);
    	}
    	
    	return false;
    }
}
