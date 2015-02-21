package com.bage.redbaby.domain;

/**
 * 版本
 * @author 杜磊
 *
 */
public class VersionCode {
	public String response;
	public Version version;
	
	public class Version{
		public boolean force;
		public boolean isnew;
		public String url;
		public String version;
		@Override
		public String toString() {
			return "Version [force=" + force + ", isnew=" + isnew + ", url="
					+ url + ", version=" + version + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "VersionCode [response=" + response + ", version=" + version
				+ "]";
	}
	
}
