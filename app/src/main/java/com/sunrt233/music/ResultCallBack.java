package com.sunrt233.music;

import java.util.ArrayList;
import org.jsoup.nodes.Document;

public interface ResultCallBack {

    public void result(Document connection, ArrayList<DataList> songInfo, int pagesize, int page);

}