package cn.sz.free.test.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import cn.sz.free.test.R;
import cn.sz.free.test.fragment.AlbumFragment;
import cn.sz.free.test.fragment.ArtistFragment;
import cn.sz.free.test.listener.TabListener;

public class ActionBarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionbar);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setOverflowShowingAlways();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		addTabs(actionBar);
	}

	private void addTabs(ActionBar actionBar) {
		// TODO Auto-generated method stub
		Tab tab = actionBar
				.newTab()
				.setText("artist")
				.setTabListener(
						new TabListener<ArtistFragment>(this, "artist",
								ArtistFragment.class));
		actionBar.addTab(tab);
		tab = actionBar
				.newTab()
				.setText("album")
				.setTabListener(
						new TabListener<AlbumFragment>(this, "album",
								AlbumFragment.class));
		actionBar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_actionbar, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();

		MenuItem shareItem = menu.findItem(R.id.action_share);
		ShareActionProvider shareActionProvider = (ShareActionProvider) shareItem
				.getActionProvider();
		shareActionProvider.setShareIntent(getShareIntent());
		// 配置SearchView属性
		// searchItem.setOnActionExpandListener(new OnActionExpandListener() {
		//
		// @Override
		// public boolean onMenuItemActionExpand(MenuItem arg0) {
		// // TODO Auto-generated method stub
		// Log.d("Debug", "on expand");
		// return false;
		// }
		//
		// @Override
		// public boolean onMenuItemActionCollapse(MenuItem arg0) {
		// // TODO Auto-generated method stub
		// Log.d("Debug", "on collapse");
		// return false;
		// }
		// });
		return true;
	}

	private Intent getShareIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add:
			Log.d("Debug", "action add");
			return true;

		case R.id.action_delete:
			Log.d("Debug", "action delete");
			return true;

		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;

		default:
			Log.d("Debug", "default");
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 让Overflow中的选项显示图标
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * 解决不同设备ActionBar显示的差异性
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
