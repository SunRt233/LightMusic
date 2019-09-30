package com.sunrt233.lightmusic.ui.activity;

import com.sunrt233.lightmusic.base.*;
import android.os.*;
import com.sunrt233.lightmusic.*;
import android.support.v7.widget.*;

public class LocalMusicActivity extends BaseActivity
{
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localmusic);
	}
	
	private void initToolbar()
	{
		toolbar = (Toolbar) findViewById(R.id.activity_localmusic_toolbar);
		toolbar.setTitle("本地音乐");
		setSupportActionBar(toolbar);
	}
	
}
