package com.vin.app2.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class BBS extends Activity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().show();
        actionBar.setTitle(getResources().getStringArray(R.array.bbs_items)[getIntent().getIntExtra("type",0)]);
        int type=getIntent().getIntExtra("type",0);
        String [] list={};
        switch (type){
            case 4:
                list=getResources().getStringArray(R.array.bbs_ganbiya_items);
                break;
            case 1:
                list=getResources().getStringArray(R.array.bbs_nba_items);
                break;
            case 2:
                list=getResources().getStringArray(R.array.bbs_cba_items);
                break;
            case 5:
                list=getResources().getStringArray(R.array.bbs_equitment_items);
                break;
            case 3:
                list=getResources().getStringArray(R.array.bbs_soccer_items);
                break;
            case 6:
                list=getResources().getStringArray(R.array.bbs_zonghe_items);
                break;
            case 7:
                list=getResources().getStringArray(R.array.bbs_szjs_items);
                break;
            case 8:
                list=getResources().getStringArray(R.array.bbs_cpzx_items);
                break;
            case 9:
                list=getResources().getStringArray(R.array.bbs_zwgl_items);
                break;
            case 10:
                list=getResources().getStringArray(R.array.bbs_games_items);
                break;
            case 0:
            default:
                JsonMaker jsonMaker=new JsonMaker("my_bbs_items",BBS.this);
                jsonMaker.setJson();
                //list=jsonMaker.getResult("my_bbs_items");
                break;
        }
        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        list),
                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        getFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        return true;
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

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bb, container, false);
            //Log.d("HUPO_sec",getArguments().getInt(ARG_SECTION_NUMBER)+"");
            //Log.d("HUPO_type",getActivity().getIntent().getIntExtra("type",0)+"");
             JsonMaker jsonMaker = new JsonMaker("bbs_item",getActivity().getIntent().getIntExtra("type",0),getArguments().getInt(ARG_SECTION_NUMBER)-1,getActivity());
             jsonMaker.setJson();
            return rootView;
        }
    }

}
