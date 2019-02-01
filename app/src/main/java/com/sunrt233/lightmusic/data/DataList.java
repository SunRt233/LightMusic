package com.sunrt233.lightmusic.data;

public class DataList
{
	private String musicName = "";
	private String musicAuthor = "";
	private String musicUrl = "";
	private String imageUrl = "";

	public DataList(String name,String author,String murl, String iurl)
	{
		musicName = name;
		musicAuthor = author;
		musicUrl = murl;
		imageUrl = iurl;
	}

	public void setMusicName(String musicName)
	{
		this.musicName = musicName;
	}

	public String getMusicName()
	{
		return musicName;
	}

	public void setMusicAuthor(String musicAuthor)
	{
		this.musicAuthor = musicAuthor;
	}

	public String getMusicAuthor()
	{
		return musicAuthor;
	}

	public void setMusicUrl(String musicUrl)
	{
		this.musicUrl = musicUrl;
	}

	public String getMusicUrl()
	{
		return musicUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}



}

