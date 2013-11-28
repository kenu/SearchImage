package kr.kenuheo.searchimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailView extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Bundle extras = getIntent().getExtras();
		String title = extras.getString(CustomizedListView.KEY_TITLE);
		String thumbnail = extras.getString(CustomizedListView.KEY_LINK);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		new DownloadImageTask(imageView).execute(thumbnail);
	}

}
