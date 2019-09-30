package com.sunrt233.lightmusic.utils;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.bamboy.bamboycollected.utils.*;
import com.sunrt233.lightmusic.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class BitmapUtils
{
	public static ArrayList<Bitmap> setBitmapFromNetWork(final String url, final ImageView i, final List<Bitmap> images) 
	{  
		new Thread(new Runnable(){
				public void run()
				{
					try 
					{ 
						URL pictureUrl = new URL(url); 
						InputStream in = pictureUrl.openStream(); 
						//final Bitmap bitmap = BitmapFactory.decodeStream(in); 
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 4;
						final Bitmap bitmap = BitmapFactory.decodeStream(in, new Rect(), options);
						in.close(); 

						//images.add(bitmap);

						i.post(new Runnable(){

								@Override
								public void run()
								{
									i.setImageBitmap(bitmap);
									// TODO: Implement this method
								}
							});

					}
					catch(Throwable e) 
					{ 
						i.post(new Runnable(){

								@Override
								public void run()
								{
									i.setImageResource(R.drawable.ic_music_icon_layout);
									// TODO: Implement this method
								}
							});
					} 
				}}).start();

		return (ArrayList<Bitmap>) images;
	}

	public static void setBitmapFromNetWork(final String url, final ImageView i, final int size) 
	{  
		new Thread(new Runnable(){
				public void run()
				{
					try 
					{ 
						URL pictureUrl = new URL(url); 
						InputStream in = pictureUrl.openStream(); 

						if(!(size < 1))
						{
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = size;
							final Bitmap bitmap = BitmapFactory.decodeStream(in, new Rect(), options);

							in.close(); 

							i.post(new Runnable(){

									@Override
									public void run()
									{
										i.setImageBitmap(bitmap);
										
										// TODO: Implement this method
									}
								});
						}
						else
						{
							final Bitmap bitmap = BitmapFactory.decodeStream(in);

							in.close(); 

							i.post(new Runnable(){

									@Override
									public void run()
									{
										i.setImageBitmap(bitmap);
										
										// TODO: Implement this method
									}
								});
						}



					}
					catch(Throwable e) 
					{ 
						i.post(new Runnable(){

								@Override
								public void run()
								{
									i.setImageResource(R.drawable.ic_music_icon_layout);
									// TODO: Implement this method
								}
							});
					} 
				}}).start();


	}

	public static void setBlurBitmapFromNetWork(final Context context,final String url, final ImageView view, final float value)
	{
		new Thread(new Runnable(){
				public void run()
				{
					try 
					{ 
						URL pictureUrl = new URL(url); 
						InputStream in = pictureUrl.openStream(); 
						Bitmap temp = BitmapFactory.decodeStream(in);

						in.close(); 
						
						UtilBitmap up = new UtilBitmap();
						final Bitmap bitmap = up.blurBitmap(context, temp, value);
						
						view.post(new Runnable(){

								@Override
								public void run()
								{
									view.setImageBitmap(bitmap);
									
									// TODO: Implement this method
								}
							});

					}
					catch(Throwable e) 
					{ 
						view.post(new Runnable(){

								@Override
								public void run()
								{
									view.setImageResource(R.drawable.ic_music_icon_layout);
									// TODO: Implement this method
								}
							});
					} 
				}}).start();
	}
	
	public static void setBlurBitmapFromNetWorkToBackgroung(final Context context,final String url, final View view, final float value)
	{
		new Thread(new Runnable(){
				public void run()
				{
					try 
					{ 
						URL pictureUrl = new URL(url); 
						InputStream in = pictureUrl.openStream(); 
						Bitmap temp = BitmapFactory.decodeStream(in);

						in.close(); 

						UtilBitmap up = new UtilBitmap();
						final Bitmap bitmap = FastBlurUtil.toBlur(temp,8);
						while(bitmap == null)
						{
							
						}
						view.post(new Runnable(){

								@Override
								public void run()
								{
									view.setBackgroundDrawable(new BitmapDrawable(bitmap));
		
									// TODO: Implement this method
								}
							});

					}
					catch(Throwable e) 
					{ 
						
					} 
				}}).start();
	}

	public static void setBitmapFromNetWorkToBackground(final String url, final View i, final int b, final Context c) 
	{  
		new Thread(new Runnable(){
				public void run()
				{
					try 
					{ 
						URL pictureUrl = new URL(url); 
						InputStream in = pictureUrl.openStream(); 
						final Bitmap bitmap = BitmapFactory.decodeStream(in); 
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 4;
						//final Bitmap bitmap = new  UtilBitmap().blurBitmap(c, BitmapFactory.decodeStream(in,new Rect(),options),b);
						in.close(); 

						i.post(new Runnable(){

								@Override
								public void run()
								{
									i.setBackgroundDrawable(new BitmapDrawable(bitmap));
									// TODO: Implement this method
								}
							});

					}
					catch(Throwable e) 
					{ 
						i.post(new Runnable(){

								@Override
								public void run()
								{
//									i.setBackgroundResource(R.drawable.ic_music_icon_layout);
									// TODO: Implement this method
								}
							});
					} 
				}}).start();


	}
}

