package com.example.fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    private AHBottomNavigation nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        nav.addItem(new AHBottomNavigationItem("Tab 1", R.drawable.ic_adb_black_24dp));
        nav.addItem(new AHBottomNavigationItem("Tab 2", R.drawable.ic_movie_black_24dp));
        nav.addItem(new AHBottomNavigationItem("Tab 3", R.drawable.ic_local_bar_black_24dp));
        nav.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        nav.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));
        nav.setInactiveColor(ContextCompat.getColor(this, R.color.inactive_tabs));

        nav.setOnTabSelectedListener(this);
        onTabSelected(0, false);
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        // Pop off everything up to and including the current tab
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Add the new tab fragment
        fragmentManager.beginTransaction()
                .replace(R.id.container, TabFragment.newInstance(position + 1), String.valueOf(position))
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

    /**
     * Add a fragment on top of the current stack
     */
    public void addFragmentOnTop(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragments = getSupportFragmentManager();
        Fragment homeFrag = fragments.findFragmentByTag("0");

        if (fragments.getBackStackEntryCount() > 1) {
            // We have fragments on the backstack that are poppable
            fragments.popBackStackImmediate();
        } else if (homeFrag == null || !homeFrag.isVisible()) {
            // We aren't showing the home screen, so that is the next stop on the back journey
            nav.setCurrentItem(0);
        } else {
            // We are already showing the home screen, so the next stop is out of the app.
            supportFinishAfterTransition();
        }
    }
}