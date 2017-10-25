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
    private static String[] count = null;
    private LinearLayout login_signup;
    private FragmentDrawerListener drawerListener;
    ImageView edit_profile;
    private TextView login,sign_up;

    Integer[] imageId = {
            R.mipmap.profile,
            R.mipmap.salesentry,
            R.mipmap.saleshistory,
            R.mipmap.logout
     };

    /**
     * Declaration of Variable
     **/
    String mUserID;String mFirstName;
    String mLastName;
    String mMobile;
    String mEmailId;
    String image;
    boolean status;


    /**
     * Declaration of Shared Preferences
     **/
    SharedPreferences mPreferences;
    SharedPreferences sharedPreferences;
    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem(titles[i], imageId[i]);

            navItem.setTitle(titles[i]);
           // navItem.setCount(count[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
/*
        if(status==false){
           // mProfileLayout.setVisibility(View.GONE);
            adapter.hideView(7);
           // adapter.hideView(2);
        }
*/
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

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
       // count = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        /** Initialization of Shared Preferences **/


      /*  mUserID = mPreferences.getString(Constant.USER_ID, "");
        mFirstName = mPreferences.getString(Constant.USER_FIRST_NAME, "");
        mLastName = mPreferences.getString(Constant.USER_LAST_NAME, "");
        mMobile = sessionManager.getUserInformation().getMobile();
        mEmailId = sessionManager.getUserInformation().getEmailId();
        progressBar=(ProgressBar)layout.findViewById(R.id.progressBar);
        image=user.getFacebookID();


        if (!mUserID.equalsIgnoreCase("")) {

        }
*/
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
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
      //  edit_profile = (ImageView) layout.findViewById(R.id.edit_profile);
         login = (TextView) layout.findViewById(R.id.login);
      /*  sign_up = (TextView) layout.findViewById(R.id.signup);
        nameText = (TextView) layout.findViewById(R.id.txtUsername);
        mobileText = (TextView) layout.findViewById(R.id.txtUserEmail);
       */// mUserImage = (CircleImageView) layout.findViewById(R.id.imgAvatar);

        //nameText.setText(mFirstName + " " + mLastName);
       // nameText.setText(Name);
         //   mobileText.setText(mEmailId);
          //  mobileText.setText(Email);
/*
        Picasso.with(getActivity())
                .load(image)
                .placeholder(R.drawable.image)
                .resize(64,64)
                .into(mUserImage);
*/

        /** Initialization of UI **/

        /** Initailization of addListener **/
        //addListener();

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
                //changeDrawerIconOnDrawerClick(R.mipmap.ic_date);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                //changeDrawerIconOnDrawerClick(R.mipmap.ic_back);
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
        //to change default icon to hamburger item initially
        //changeDrawerIconOnDrawerClick(R.mipmap.ic_back);
    }

    private void changeDrawerIconOnDrawerClick(int resourceId) {
        //Drawable icon = ContextCompat.getDrawable(getApplicationContext(), resourceId);
        Drawable icon = ResourcesCompat.getDrawable(getResources(), resourceId, null);
        icon.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        //mDrawerToggle.setDrawerIndicatorEnabled(false);
        // mDrawerToggle.setHomeAsUpIndicator(icon);
        getActivity().invalidateOptionsMenu();
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
            Bitmap myBitmap = BitmapFactory.decodeFile(storagePath.getAbsolutePath());

          //  mUserImage.setImageBitmap(myBitmap);

        }
    }

    /******************************************
     * Here we use addListener() Method
     **/

/*
    public void addListener() {
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), AboutShoppingInCp.class);
                intent1.putExtra("title", getActivity().getResources().getString(R.string.about_shopping_in_cp));
                intent1.putExtra("url", "http://www.myconnaughtplace.com/aboutus");
                getActivity().startActivity(intent1);
            }
        });
    }
*/

}
