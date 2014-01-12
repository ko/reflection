package com.relurori.reflection.usbdevice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.graphics.Path;
import android.net.Uri;
import android.util.Log;

public class MountPoint {

	private final static String TAG = MountPoint.class.getSimpleName();
	
	public static List<ProcMountsLine> getProcMountsLines() throws IOException {
	
		Log.d(TAG,"getProcMountsLines");
		
		List<String> lines = catProcMounts();
	
		Log.d(TAG,"getProcMountsLines|lines.size=" + lines.size());
		
		return parseProcMounts(lines);
	}
	
	
	public static List<String> catProcMounts() throws IOException {
		FileInputStream fis = new FileInputStream("/proc/mounts");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		String line;
		List<String> lines = new ArrayList<String>();
		
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		
		return lines;
	}
	
	public static List<ProcMountsLine> parseProcMounts(List<String> lines) {
		List<ProcMountsLine> parsed = new ArrayList<ProcMountsLine>();
		ProcMountsLine parsedLine;

		int idx = 0;
		
		StringTokenizer st;
		for (String line : lines) { 
			
			st = new StringTokenizer(line);
			parsedLine = parseProcMountsLine(st);
			
			parsed.add(parsedLine);
		}
		
		Log.d(TAG,"parseProcMounts|parsed.size()=" + parsed.size());
		
		return parsed;
	}

	private static ProcMountsLine parseProcMountsLine(StringTokenizer st) {
		
		ProcMountsLine line = new ProcMountsLine();
		
		int n = 0;
		
		while (st.hasMoreElements()) {
			
			
			switch(n) {
			case ProcMountsConstants.DEV_NODE_INDEX:
				line.setDevNode((String) st.nextElement());
				break;
			case ProcMountsConstants.MOUNT_POINT_INDEX:
				line.setMountPoint((String) st.nextElement());
				break;
			default:
				st.nextElement();
				break;
			}
			n++;
		}
		
		return line;
	}
}
