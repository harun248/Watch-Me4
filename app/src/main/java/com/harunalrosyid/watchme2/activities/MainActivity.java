package com.harunalrosyid.watchme2.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.harunalrosyid.watchme2.R;
import com.harunalrosyid.watchme2.fragments.FavoriteFragment;
import com.harunalrosyid.watchme2.fragments.MovieFragment;
import com.harunalrosyid.watchme2.fragments.TvShowFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_page_horizontal)
    ViewPager viewPager;
    @BindView(R.id.navtabbar_horizontal)
    NavigationTabBar ntb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MovieFragment());
        adapter.addFrag(new TvShowFragment());
        adapter.addFrag(new FavoriteFragment());
        viewPager.setAdapter(adapter);
    }

    private void initUI() {
        setupViewPager(viewPager);
        final String[] colors = getResources().getStringArray(R.array.priview_color);

        final NavigationTabBar navigationTabBar = findViewById(R.id.navtabbar_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_movie_black_24dp),
                        Color.parseColor(colors[2])
                )
                        .title(getResources().getString(R.string.tab_movie))
                        .badgeTitle("")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_tv_black_24dp),
                        Color.parseColor(colors[2]))
                        .title(getResources().getString(R.string.tab_tv))
                        .badgeTitle("")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_favorite),
                        Color.parseColor(colors[2]))
                        .title(getResources().getString(R.string.tab_fav))
                        .badgeTitle("")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setInactiveColor(getResources().getColor(R.color.white));
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(() -> {
            for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                navigationTabBar.postDelayed(() -> model.showBadge(), i * 100);
            }
        }, 500);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}
