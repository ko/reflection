package com.relurori.reflection.usbdevice;

public class ProcMountsLine {

	private String devNode;
	private String mountPoint;
	private String fs;
	private String mountOption;
	private Integer reserved1;
	private Integer reserved2;
	
	public ProcMountsLine() {
		devNode = null;
		mountPoint = null;
		fs = null;
		mountOption = null;
		reserved1 = null;
		reserved2 = null;
	}
	
	public ProcMountsLine(String device, String mount) {
		devNode = device;
		mountPoint = mount;
		fs = null;
		mountOption = null;
		reserved1 = null;
		reserved2 = null;
	}
	
	public String getDevNode() {
		return devNode;
	}
	
	public String getMountPoint() {
		return mountPoint;
	}
	
	public void setDevNode(String dev) {
		devNode = dev;
	}
	
	public void setMountPoint(String mount) {
		mountPoint = mount;
	}
}
