package com.day0404;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;
	//当点击ActionBar标题栏时候
	private ActionBarDrawerToggle mDrawerToggle;
	//最初的那个标题
	private String mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//最开始的标题
		mTitle = (String) getTitle();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			menuLists.add("极客学院0" + i);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuLists);
			mDrawerList.setAdapter(adapter);
			mDrawerList.setOnItemClickListener(this);
			
			//居然是在for循环里面写    最后两个参数是    打开和关闭的资源，就要在values文件夹下的string文件中添加两个name
			
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
				@Override
				public void onDrawerOpened(View drawerView) {
					super.onDrawerOpened(drawerView);
					getActionBar().setTitle("请选择");
					//当执行打开或关闭时要重新对ActionBar   Menu菜单操作
					//所以要调用invalidateOptionsMenu()来重绘ActonBar上的菜单项
					invalidateOptionsMenu();//这时系统会自动调用 一个方法 Call onPrepareOPtionsMenu(),所以下面要重写这个方法
				}
				@Override
				public void onDrawerClosed(View drawerView) {
					super.onDrawerClosed(drawerView);
					//这里要改为最开始的字符串
					getActionBar().setTitle(mTitle);
					//当执行打开或关闭时要重新对ActionBar   Menu菜单操作
					//所以要调用invalidateOptionsMenu()来重绘ActonBar上的菜单项
					invalidateOptionsMenu();
				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			
			//最后最后开启ActionBar  上 的APP ICON的功能
			getActionBar().setDisplayHomeAsUpEnabled(true);//返回图标
			getActionBar().setHomeButtonEnabled(true);//左上角的图标可用
			//还需要要找到OnActonItemSelected()访求
			
		}
	}

	//当每点击一个listview的item时候就动态在主视图中插入一个fragment
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//动态插入一个Fragment到FragmentLayout当中
		//接下来就创建 一个fragment
		Fragment contentFragment = new ContentFragment();
		//为了区别每个fragment的不一样，让当前的fragment携带个参数
		Bundle args = new Bundle();
		args.putString("text", menuLists.get(position));
		contentFragment.setArguments(args);
		//fragment创建好了，还需要一个FragmentManager
		FragmentManager fm = getFragmentManager();
		//fm.beginTransaction()开启一个事物，
		//因为要用一个新 的fragment替换掉一个当前的fragment
									//旧的           ，       新的
		//最后当给一个fragment开启一个事物的时候，	一定要提交,才会使当前的事物生效
		fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
		//当前的导航菜单就要隐藏了
		mDrawerLayout.closeDrawer(mDrawerList);
		//因为上面给每个framgent绑定了一个参数，下面就去我们创建的ContentFragment接收传递进来的参数
		
		
	
		
		
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//需要将ActionDrawer	Toggle 与 DrawerLayout的状态同步
		//将ActionDrawerToggle中的drawer图标，设置为ActionBar的Home-Button的	ICON
		mDrawerToggle.syncState();
	}
	//屏幕旋转，发生onConfigurationChanged变化的时候
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	//ActionBar Menu菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//将ActionBar上图标与Drawer结合起来
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_websearcher:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.jikexueyuan.com");
			intent.setData(uri);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//首先要获取到drawerLayout的打开状态
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//接下来设置右上角的可见状态,
		menu.findItem(R.id.action_websearcher).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	//创建ActionBar标题栏
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
