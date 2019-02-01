package com.sunrt233.lightmusic.utils;

import android.os.*;
import java.util.*;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import com.sunrt233.lightmusic.data.*;
import android.util.*;

public class MusicSearchUtils
{

	public static void searchFromKuGou(final String keyWord, final Handler mhd)
	{
		getFromKuGou get = new getFromKuGou(keyWord, mhd);
		get.start();
	}

}

class getFromKuGou extends Thread
{
	private Handler mhd;
	private String keyWord;

	public getFromKuGou(String key,Handler h)
	{
		mhd = h;
		keyWord = key;
	}

	@Override
	public void run()
	{
		super.run();
		// TODO: Implement this method
		ArrayList<DataList> songsList = new ArrayList<DataList>();
		String engineUrl = "http://mobilecdn.kugou.com/api/v3/search/song?format=json&keyword=KEYWORD&page=1&pagesize=100&showtype=1";
		String[] input = engineUrl.split("KEYWORD");
		String url = input[0] + keyWord + input[1];

		try{
			Document doc = Jsoup.connect(url).get();

			JSONObject json = new JSONObject(doc.text());
			JSONArray songs = json.getJSONObject("data").getJSONArray("info");
			for (int i = 0;i < (songs.length() + 1);i++)
			{
				JSONObject song = (JSONObject) songs.get(i);

				Document doc2 = Jsoup.connect("http://www.kugou.com/yy/index.php?r=play/getdata&hash=" + song.get("hash")).get();
				JSONObject singleSong = new JSONObject(doc2.text()).getJSONObject("data");
				JSONObject authors = singleSong.getJSONArray("authors").getJSONObject(0);
				Document doc3 = Jsoup.connect("http://m.kugou.com/app/i/getSongInfo.php?hash=" + song.get("hash") + "&cmd=playInfo").get();
				JSONObject songImg = new JSONObject(doc3.text());
				String imgUrl = songImg.get("album_img").toString().replace("/{size}/","/");
				//String[] imgUrl = songImg.get("album_img").toString().split("{size}");
//							songsList.add(new DataList(singleSong.get("audio_name").toString(), authors.get("author_name").toString(), singleSong.get("play_url").toString(), imgUrl[0]+ "/" + imgUrl[1]));
				songsList.add(new DataList(singleSong.get("audio_name").toString(), authors.get("author_name").toString(), singleSong.get("play_url").toString(), imgUrl));

			}
		}
		catch (Throwable e)
		{
			Log.i("log",e.toString());
			songsList.add(new DataList("获取失败", "null", "不要播放",""));
//						mhd.sendEmptyMessage(1);
//						Message msg = new Message();
//						msg.obj = e;
//						mhd.sendMessage(msg);
		}

		mhd.sendEmptyMessage(0);
		Message msg = new Message();
		msg.obj = songsList;
		mhd.sendMessage(msg);


	}
}




