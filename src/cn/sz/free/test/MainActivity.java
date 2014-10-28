package cn.sz.free.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sz.free.test.activity.ActionBarActivity;

public class MainActivity extends Activity implements OnClickListener {

	Button actionBarButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		actionBarButton = (Button) findViewById(R.id.actionBarButton);
		
		actionBarButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Class<?> intentClass = null;
		switch (view.getId()) {
		case R.id.actionBarButton:
			intentClass = ActionBarActivity.class;
			break;

		default:
			return;
		}

		Intent intent = new Intent(MainActivity.this, intentClass);
		startActivity(intent);
	}

}
