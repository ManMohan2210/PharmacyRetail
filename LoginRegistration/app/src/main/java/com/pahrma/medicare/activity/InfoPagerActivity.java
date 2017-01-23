package com.pahrma.medicare.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.liangfeizc.slidepageindicator.PageIndicator;
import com.liangfeizc.slidepager.SlidePagerAdapter;
import com.pahrma.medicare.R;

import java.util.Arrays;

public class InfoPagerActivity extends AppCompatActivity implements View.OnClickListener {
	public static final String EXTRA_TITLE = "slidepageractivity.extra.title";
	public static final String EXTRA_PICTURES = "slidepageractivity.extra.pictures";
	public static final String EXTRA_INDICATOR_TYPE = "slidepageractivity.extra.indicator.type";


	private static final String[] IMAGES = new String[]{"" + R.drawable.image1, "" + R.drawable
			.image2, "" + R.drawable.image3, "" + R.drawable.image4};
	private PageIndicator mPageIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Fresco.initialize(this);

		setContentView(com.liangfeizc.slidepager.R.layout.activity_slide_pager);

		ViewPager pager = (ViewPager) findViewById(com.liangfeizc.slidepager.R.id.pager);

		SlidePagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());

		((TextView)findViewById(R.id.tv_lets_start)).setOnClickListener(this);

		if (getIntent() == null) return;

		// set title
		ActionBar ab = getActionBar();
		if (ab != null) {
			String title = getIntent().getStringExtra(EXTRA_TITLE);
			if (!TextUtils.isEmpty(title)) {
				ab.setTitle(title);
			}
		}

		// set pictures
//		String[] pics = getIntent().getStringArrayExtra(EXTRA_PICTURES);
		pagerAdapter.addAll(Arrays.asList(IMAGES));

		pager.setAdapter(pagerAdapter);
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int
					positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mPageIndicator = (PageIndicator) findViewById(com.liangfeizc.slidepager.R.id.indicator);
		mPageIndicator.setIndicatorType(PageIndicator.IndicatorType.CIRCLE);
		mPageIndicator.setViewPager(pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.liangfeizc.slidepager.R.menu.menu_slide_pager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == com.liangfeizc.slidepager.R.id.action_indicator_circle) {
			mPageIndicator.setIndicatorType(PageIndicator.IndicatorType.CIRCLE);
		} else if (id == com.liangfeizc.slidepager.R.id.action_indicator_fraction) {
			mPageIndicator.setIndicatorType(PageIndicator.IndicatorType.FRACTION);
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.tv_lets_start) {
			Intent intent = new Intent(InfoPagerActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
