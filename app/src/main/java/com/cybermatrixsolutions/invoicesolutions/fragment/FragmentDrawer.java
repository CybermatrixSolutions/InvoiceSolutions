package com.cybermatrixsolutions.invoicesolutions.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.NavigationDrawerAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.NavDrawerItem;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Oranz on 6/6/2016.
 */
public class FragmentDrawer extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static String[] titles1 = null;
    private static String[] count = null;
    private LinearLayout login_signup;
    private FragmentDrawerListener drawerListener;
    ImageView edit_profile;
    private TextView login,sign_up;

    Integer[] imageId = {
            R.mipmap.petrol,
            R.mipmap.oil,
            R.mipmap.nozzle,
            R.mipmap.shift,
            R.mipmap.white_wallet,
            R.mipmap.white_wallet,
            R.mipmap.logout
     };
        Integer[] imageId1 = {
            R.mipmap.petrol,
            R.mipmap.oil,
            R.mipmap.nozzle,
            R.mipmap.logout
    };
    TextView name;TextView email;


    /**
     * Declaration of Shared Preferences
     **/
    private PrefsManager prefsManager;
    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();



        PrefsManager manager=new PrefsManager(getActivity());


        if(manager.getdisignation().equals("Salesman")){
            for (int i = 0; i < titles1.length; i++) {
                NavDrawerItem navItem = new NavDrawerItem(titles1[i], imageId1[i]);
                navItem.setTitle(titles1[i]);
                // navItem.setCount(count[i]);
                data.add(navItem);
            }
        }else {
            for (int i = 0; i < titles.length; i++) {
                NavDrawerItem navItem = new NavDrawerItem(titles[i], imageId[i]);
                navItem.setTitle(titles[i]);
                // navItem.setCount(count[i]);
                data.add(navItem);
            }
        }




        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        titles1 = getActivity().getResources().getStringArray(R.array.nav_drawer_labels1);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        prefsManager=new PrefsManager(getActivity());
        String Email=prefsManager.getUserDetails().get("email");
        String Name=prefsManager.getUserDetails().get("Personnel_Name");
        name= layout.findViewById(R.id.name);
        email= layout.findViewById(R.id.email);

        if(Name!=null){
            name.setText(Name);
        }
        if(Email!=null){
            email.setText(Email);
        }
        recyclerView = layout. findViewById(R.id.drawerList);
            adapter = new NavigationDrawerAdapter(getActivity(), getData());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    drawerListener.onDrawerItemSelected(view, position);
                    mDrawerLayout.closeDrawer(containerView);
                }
                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        adapter.notifyDataSetChanged();
        login = layout.findViewById(R.id.login);
        showImage();
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    public void showImage() {
        File storagePath = new File(Environment.getExternalStorageDirectory() + "/DOC/profile/docuser.jpg");
        if (storagePath.exists()) {

        }
    }
}
