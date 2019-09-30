package com.sunrt233.lightmusic.ui.activity;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import com.sunrt233.lightmusic.*;
import com.sunrt233.lightmusic.base.*;
import com.sunrt233.lightmusic.ui.fragment.*;
import android.view.*;
import android.support.design.widget.*;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{
	private Toolbar toolbar;
	private Fragment currentFragment = null;
	private HomeFragment homefragment = new HomeFragment();
	private DrawerLayout drawer;
	private NavigationView navigationView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		drawer = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout);
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		
		navigationView.setNavigationItemSelectedListener(this);
		switchFragment(homefragment,R.id.activity_main_frameLayout);
		
		setExitMode(EXIT_MODE_TWICE);
		setSnackBarRootlayout(findViewById(R.id.activity_main_frameLayout));
		//overridePendingTransition(R.anim.bottom_up,R.anim.bottom_down);
		navigationView.setCheckedItem(R.id.nav_homepage);
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(drawer.isDrawerOpen(Gravity.LEFT))
			{
				drawer.closeDrawers();
			}
			else
			{
			return super.onKeyDown(keyCode, event);
			}
		}
		
		return false;
	}
	
	public DrawerLayout getDrawerLayout()
	{
		return drawer;
	}
	
	public void switchFragment(Fragment f, int id)
	{
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		String tag = f.getClass().getSimpleName();
		if(currentFragment == null)
		{
			transaction.add(id, f, tag).commit();
			currentFragment = f;
			//printToast("初始化成功",1);
		}
		else
		{
			if(manager.findFragmentByTag(tag) == null)
			{
				transaction.hide(currentFragment).add(id, f, tag).commit();
				currentFragment = f;
				//printToast("添加成功",1);
			}
			if(manager.findFragmentByTag(tag) != null&&currentFragment.getTag() != tag)
			{
				for(Fragment inf : manager.getFragments())
				{
					transaction.hide(inf);
				}
				transaction.show(manager.findFragmentByTag(tag)).commit();
				currentFragment = f;
				//printToast("显示成功",1);
			}
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem p1)
	{
		// TODO: Implement this method
		switch(p1.getItemId())
		{
			case R.id.nav_homepage:
				//printToast("tt",1);
				switchFragment(homefragment,R.id.activity_main_frameLayout);
				break;
			case R.id.nav_theme:
				showSnackBar("此功能尚在开发",Snackbar.LENGTH_SHORT);
				navigationView.setCheckedItem(R.id.nav_homepage);
				break;
		}
		return true;
	}
	
}
