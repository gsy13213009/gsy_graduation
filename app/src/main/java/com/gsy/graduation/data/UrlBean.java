package com.gsy.graduation.data;

public class UrlBean {
	private String url;// apk������·��
	private int versionCode;//�°汾��
	private String desc;//������Ϣ

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	@Override
	public String toString() {
		return "UrlBean [url=" + url + ", versionCode=" + versionCode
				+ ", desc=" + desc + "]";
	}

}
