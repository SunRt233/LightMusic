package com.sunrt233.music;

public class DataList {
	
	private String musicName = "";
	private String musicAuthor = "";
	private String musicUrl = "";
	private String imageUrl = "";
	private int pagesize = 0;
	private int page = 0;

	public DataList(String name, String author, String murl, String iurl) {

		musicName = name;
		musicAuthor = author;
		musicUrl = murl;
		imageUrl = iurl;
	}
	
	public DataList(String name, String author, String murl, String iurl, int pagesize, int page) {

		musicName = name;
		musicAuthor = author;
		musicUrl = murl;
		imageUrl = iurl;
		this.pagesize = pagesize;
		this.page = page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPage()
	{
		return page;
	}

	public void setPagesize(int pagesize)
	{
		this.pagesize = pagesize;
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicAuthor(String musicAuthor) {
		this.musicAuthor = musicAuthor;
	}

	public String getMusicAuthor() {
		return musicAuthor;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String toString() {
		return getMusicName() + "-" + getMusicAuthor() + ";murl:" + getMusicUrl() + " iurl:" + getImageUrl();
	}

}
