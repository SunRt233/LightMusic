package com.sunrt233.lightmusic;

import java.util.*;

public class DataRepertory
{
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
}
