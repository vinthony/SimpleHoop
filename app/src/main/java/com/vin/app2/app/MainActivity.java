package com.vin.app2.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.SQLException;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static String[] itemsArr={"hot","china","soccer","cba","nba","sports","tennis","f1","bbs_hot"};
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().show();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // MyDBAdapter myDBAdapter = new MyDBAdapter(this);

        //Log.d("HUPO","Main list restart!!!");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            MyDBAdapter db = new MyDBAdapter(this);
            String userName="";
            String sign ="";
            try {
                db.open();
                userName=db.fetchData(1).getString(2);
                sign = db.fetchData(1).getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(db.isLogin()){
                Intent i = new Intent(MainActivity.this,UserLoged.class);
                i.putExtra("username",userName);
                i.putExtra("sign",sign);
                startActivity(i);
            }else{
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
            db.close();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        static PlaceholderFragment fragment;
        private RefreshableView refreshableView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            Log.d("HUPU_sec",""+sectionNumber);
            fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            Log.d("HUPOSER","i am fragment");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                Bundle savedInstanceState) {
                final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

                final ListView lv = (ListView) rootView.findViewById(R.id.listView);

                refreshableView = (RefreshableView) rootView.findViewById(R.id.refreshable_view);

                refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {
                            JsonMaker jm = null;
                            try {

                                jm = new JsonMaker("voice", itemsArr[getArguments().getInt(ARG_SECTION_NUMBER) - 1], fragment.getActivity().getApplicationContext());
                            } catch (Exception e) {
                                Log.e("HUPOERROR", "JSONmaker error");
                            }
                            jm.setJson(rootView, fragment, fragment.getActivity().getApplicationContext(), lv);
                            refreshableView.finishRefreshing();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0);
                JsonMaker jm = null;
                try {
                    jm = new JsonMaker("voice", itemsArr[getArguments().getInt(ARG_SECTION_NUMBER) - 1], fragment.getActivity().getApplicationContext());
                    if(getArguments().getInt(ARG_SECTION_NUMBER)==9){
                        jm.setFlag("bbs");
                    }
                } catch (Exception e) {
                    Log.e("HUPOERROR", "JSONmaker error");
                }
                jm.setJson(rootView, fragment, fragment.getActivity().getApplicationContext(), lv);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
