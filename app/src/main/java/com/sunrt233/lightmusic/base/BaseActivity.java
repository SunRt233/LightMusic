package com.sunrt233.lightmusic.base;

import android.content.pm.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import java.util.*;

public class BaseActivity extends AppCompatActivity
{
	private boolean isExit = false;
	public int EXIT_MODE_ONCE = 0;
	public int EXIT_MODE_TWICE = 1;
	private int EXIT_MODE = 0;
	private Object Mmsg;
	private View snackBarRootlayout;

	public void setFragment(Fragment f, int rId)
	{
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(rId, f).commit();
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public PackageInfo getAppVersion()
	{
		Object[] ver_info = new String[4];

        try
		{
            //PackageManager管理器
            PackageManager pm = this.getPackageManager();
            //获取相关信息
            PackageInfo packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
            //版本名称
            String name = packageInfo.versionName;
            //版本号
            int version = packageInfo.versionCode;

            ver_info[0] = pm;
			ver_info[1] = packageInfo;
			ver_info[2] = name;
			ver_info[3] = version;

            return packageInfo;
        }
		catch (PackageManager.NameNotFoundException e)
		{
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //如果出现异常抛出null
        return null;
    }



	public void setExitMode(int Mode)
	{
		EXIT_MODE = Mode;
	}

	public int getExitMode()
	{
		return EXIT_MODE;
	}

	public void exit()
	{
		switch (EXIT_MODE)
		{
			case 0:
				finish();
				break;
			case 1:
				if (!isExit)
				{
					isExit = true;
					printToast("再按一次退出程序", Toast.LENGTH_LONG);
					Timer t = new Timer();
					t.schedule(new TimerTask(){

							@Override
							public void run()
							{
								isExit = false;
							}
						}, 2000);
				}
				else
				{
					finish();
					System.exit(0);
				}
				break;

		}

	}

	public void printToast(String msg, int time)
	{

		Toast.makeText(getApplicationContext(), msg.toString(), time).show();

	}
	
	public void setSnackBarRootlayout(View view)
	{
		snackBarRootlayout = view;
	}
	
	public void showSnackBar(String msg, int time)
	{
		if(snackBarRootlayout == null)
		{
			Snackbar.make(getWindow().getDecorView(),msg,time).show();
		}
		else
		{
			Snackbar.make(snackBarRootlayout,msg,time).show();
		}
	}
	
	public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }
	
	
	public void hideInput() 
	{
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
	}
		
}

