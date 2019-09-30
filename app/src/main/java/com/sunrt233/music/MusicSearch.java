package com.sunrt233.music;

import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.nodes.Document;

public class MusicSearch {

    public static void main(String[] args) {

        System.out.println("请输入歌曲名称:");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.next();
        System.out.println("请输入数据条数:");
        int pagesize = scanner.nextInt();
        scanner.close();

        searchFromNetease(new ResultCallBack() {

            @Override
            public void result(Document connection, ArrayList<DataList> songInfo, int pagesize, int page) {
                for (DataList dataList : songInfo) {
                    System.out.println(dataList.toString());
                }
            }
        }, keyword, pagesize, 0);
    }

    public static void searchFromNetease(ResultCallBack callBack, String keyword, int pagesize, int page) {
        NeteaseEngine n = new NeteaseEngine();
        n.search(callBack, keyword, pagesize, page);
    }

}