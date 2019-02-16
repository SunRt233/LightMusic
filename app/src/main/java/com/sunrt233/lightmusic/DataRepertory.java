package com.sunrt233.lightmusic;

import java.util.*;

public class DataRepertory
{
	private static ArrayList<DataRepertory.DataReceiver> receivers = new ArrayList<DataRepertory.DataReceiver>();
	private static ArrayList<Object[]> temporaryDatas = new ArrayList<Object[]>();

	public static void putData(Object data, String tag)
	{
		temporaryDatas.add(new Object[]{data,tag});
	}

	public static Object getData(String tag)
	{
		Object o = null;
		
		try{
		
			for (int i = 0;i <= temporaryDatas.size();i++)
			{
				Object[] dataArray = temporaryDatas.get(i);
				if (dataArray[1].toString() == tag)
				{
					o = dataArray[0];
				}
				else
				{
					o =  null;
				}

			}
		
		}
		catch(Throwable e)
		{
			
		}
		
		return o;
	}
	
	public static void addReceiver(DataRepertory.DataReceiver receiver)
	{
		receivers.add(receiver);
	}
	
	public static void noticeAll()
	{
		for(DataRepertory.DataReceiver receiver : receivers)
		{
			receiver.updata();
		}
	}
	
	public interface DataReceiver
	{
		public void updata();
	}
	
}
