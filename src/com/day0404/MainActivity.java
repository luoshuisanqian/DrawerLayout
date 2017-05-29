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
	//�����ActionBar������ʱ��
	private ActionBarDrawerToggle mDrawerToggle;
	//������Ǹ�����
	private String mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//�ʼ�ı���
		mTitle = (String) getTitle();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			menuLists.add("����ѧԺ0" + i);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuLists);
			mDrawerList.setAdapter(adapter);
			mDrawerList.setOnItemClickListener(this);
			
			//��Ȼ����forѭ������д    �������������    �򿪺͹رյ���Դ����Ҫ��values�ļ����µ�string�ļ����������name
			
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
				@Override
				public void onDrawerOpened(View drawerView) {
					super.onDrawerOpened(drawerView);
					getActionBar().setTitle("��ѡ��");
					//��ִ�д򿪻�ر�ʱҪ���¶�ActionBar   Menu�˵�����
					//����Ҫ����invalidateOptionsMenu()���ػ�ActonBar�ϵĲ˵���
					invalidateOptionsMenu();//��ʱϵͳ���Զ����� һ������ Call onPrepareOPtionsMenu(),��������Ҫ��д�������
				}
				@Override
				public void onDrawerClosed(View drawerView) {
					super.onDrawerClosed(drawerView);
					//����Ҫ��Ϊ�ʼ���ַ���
					getActionBar().setTitle(mTitle);
					//��ִ�д򿪻�ر�ʱҪ���¶�ActionBar   Menu�˵�����
					//����Ҫ����invalidateOptionsMenu()���ػ�ActonBar�ϵĲ˵���
					invalidateOptionsMenu();
				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			
			//��������ActionBar  �� ��APP ICON�Ĺ���
			getActionBar().setDisplayHomeAsUpEnabled(true);//����ͼ��
			getActionBar().setHomeButtonEnabled(true);//���Ͻǵ�ͼ�����
			//����ҪҪ�ҵ�OnActonItemSelected()����
			
		}
	}

	//��ÿ���һ��listview��itemʱ��Ͷ�̬������ͼ�в���һ��fragment
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//��̬����һ��Fragment��FragmentLayout����
		//�������ʹ��� һ��fragment
		Fragment contentFragment = new ContentFragment();
		//Ϊ������ÿ��fragment�Ĳ�һ�����õ�ǰ��fragmentЯ��������
		Bundle args = new Bundle();
		args.putString("text", menuLists.get(position));
		contentFragment.setArguments(args);
		//fragment�������ˣ�����Ҫһ��FragmentManager
		FragmentManager fm = getFragmentManager();
		//fm.beginTransaction()����һ�����
		//��ΪҪ��һ���� ��fragment�滻��һ����ǰ��fragment
									//�ɵ�           ��       �µ�
		//��󵱸�һ��fragment����һ�������ʱ��	һ��Ҫ�ύ,�Ż�ʹ��ǰ��������Ч
		fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
		//��ǰ�ĵ����˵���Ҫ������
		mDrawerLayout.closeDrawer(mDrawerList);
		//��Ϊ�����ÿ��framgent����һ�������������ȥ���Ǵ�����ContentFragment���մ��ݽ����Ĳ���
		
		
	
		
		
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//��Ҫ��ActionDrawer	Toggle �� DrawerLayout��״̬ͬ��
		//��ActionDrawerToggle�е�drawerͼ�꣬����ΪActionBar��Home-Button��	ICON
		mDrawerToggle.syncState();
	}
	//��Ļ��ת������onConfigurationChanged�仯��ʱ��
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	//ActionBar Menu�˵��ĵ���¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//��ActionBar��ͼ����Drawer�������
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
		//����Ҫ��ȡ��drawerLayout�Ĵ�״̬
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//�������������ϽǵĿɼ�״̬,
		menu.findItem(R.id.action_websearcher).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	//����ActionBar������
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
