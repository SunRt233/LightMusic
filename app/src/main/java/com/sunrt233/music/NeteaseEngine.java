package com.sunrt233.music;

import android.os.*;
import android.util.*;
import com.sunrt233.music.*;
import java.util.*;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

public class NeteaseEngine
 {

    public final String engineUrl = "https://v1.itooi.cn/netease/search?keyword={KEYWORD}&type=song&pageSize={PAGESIZE}&page={PAGE}";
    public final String imgUrl = "https://v1.itooi.cn/netease/pic?id={ID}&imgSize=400x400";
    public final String musicUrl = "https://v1.itooi.cn/netease/url?id={ID}&quality=flac";
	private ResultCallBack resultCallBack;

    public DataList search(ResultCallBack callBack, final String keyword, final int pagesize, final int page) {

		resultCallBack = callBack;
		
        new Thread() {

            @Override
            public void run() {
                super.run();

                String url = engineUrl.replace("{KEYWORD}", keyword).replace("{PAGESIZE}", "" + pagesize)
                        .replace("{PAGE}", "" + page);

                try {
                    Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                    JSONObject root = new JSONObject(doc.text());
                    JSONArray songs = root.getJSONObject("data").getJSONArray("songs");

                    ArrayList<DataList> results = new ArrayList<DataList>();
                    for (int i = 0; i <= songs.length() - 1; i++) {
                        JSONObject song = songs.getJSONObject(i);
                        results.add(new DataList(song.getString("name"),
                                song.getJSONArray("ar").getJSONObject(0).getString("name"),
                                musicUrl.replace("{ID}", song.get("id").toString()),
                                imgUrl.replace("{ID}", song.get("id").toString()),
								pagesize,
								page)
							);
                    }
					Message msg = new Message();
					Object[] array = {doc,results,pagesize,page};
					msg.obj = array;
					mHandler.sendMessage(msg);
					Log.i("music_srt",results.get(0).toString());
                    //callBack.result(doc, results, pagesize, page);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    Log.e("srt",e.toString());
                }

            }

        }.start();
        // System.out.println("test");

        return null;
    }
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			Object[] array =  (Object[]) msg.obj;
			resultCallBack.result((Document) array[0], (ArrayList<DataList>) array[1], (int) array[2], (int) array[3]);
		}
		
	};

}
