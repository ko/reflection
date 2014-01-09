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

import android.graphics.Path;
import android.net.Uri;

public class MountPoint {

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
	
	public static List<String> parseProcMounts(List<String> lines) {
		List<String> parsed = new ArrayList<String>();
		
		
		
		return parsed;
	}
}
