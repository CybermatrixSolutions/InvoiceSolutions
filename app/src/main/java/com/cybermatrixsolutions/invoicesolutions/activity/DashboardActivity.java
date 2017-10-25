package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cybermatrixsolutions.invoicesolutions.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.DashboardAdapter;
import com.cybermatrixsolutions.invoicesolutions.fragment.FragmentDrawer;


public class DashboardActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    String[] data;
    Integer[] itemname;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    LinearLayout ll_overlay;Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    GridView grid;

    String[] web = {
            "Petrol/Diesel sales Entry",
            "Lube sales Entry",
            "Nozzle Readiing",
            "Sales Settlement",
            "Profile",
            "Others"
            } ;
    int[] imageId = {
            R.drawable.pertol,
            R.drawable.oil,
            R.drawable.nozzle,
            R.drawable.sales,
            R.drawable.user,
            R.drawable.wallet
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

/*
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
*/
/*
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.entry:
                                selectedFragment = SalesEntryFragment.newInstance();
                                break;
                            case R.id.summary:
                                selectedFragment = SalesSummaryFragment.newInstance();
                                break;
                            case R.id.profile:
                                selectedFragment = ProfileActivity.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
*/
        mToolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById( R.id.fragment_navigation_drawer);
        drawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        grid=(GridView)findViewById(R.id.gridView);
        DashboardAdapter adapter=new DashboardAdapter(DashboardActivity.this,web,imageId);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(DashboardActivity.this,CreditCustomerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent barintent=new Intent(DashboardActivity.this,BarcodeScanActivity.class);
                        barintent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(barintent);
                        break;
                    case 2:
                        Intent nozzleReading=new Intent(DashboardActivity.this,CreditCustomerActivity.class);
                        nozzleReading.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(nozzleReading);
                        break;

                    case 3:
                        Intent nozzleReading1=new Intent(DashboardActivity.this,CreditCustomerActivity.class);
                        nozzleReading1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(nozzleReading1);
                        break;

                    case 4:
                        Intent profile=new Intent(DashboardActivity.this,ProfileActivity.class);
                        profile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(profile);
                        break;
                }

            }
        });

        //Manually displaying the first fragment - one time only
       /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, SalesEntryFragment.newInstance());
        transaction.commit();    */}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = getString( R.string.app_name);

/*
        switch (position) {
            case 0:
                Intent intent1 = new Intent(Dashboard.this, MyCartInformationActivity.class);
                startActivity(intent1);
                break;
            case 1:

                intent1 = new Intent(getApplicationContext(), WishListActivityWithTab.class);
                startActivity(intent1);
                break;

            case 2:
                intent1 = new Intent(Dashboard.this, MyOrderDetailsActivity.class);
                startActivity(intent1);
                break;
            case 3:
                Intent intetent = new Intent(Dashboard.this, NotificationActivity.class);
                startActivity(intetent);
                break;
            case 4:
                Utility.shareApp(this);
                break;
            case 5:
                Utility.openLink(this, "https://play.google.com/store/apps/details?id=com.beneficmedia.MyCP");
                break;
            case 6:

                Intent intent = new Intent(Dashboard.this, HelpAndSupportActivity.class);
                startActivity(intent);

                break;
            case 7:
                SessionManager sessionManager = new SessionManager(Dashboard.this);
                sessionManager.clearSession();
                DatabaseManager databaseManager = new DatabaseManager(Dashboard.this);
                databaseManager.deleteTable(SqlQueryConstant.MY_CART_TABLE_NAME);
                databaseManager.deleteTable(SqlQueryConstant.MY_WISH_LIST_TABLE_NAME);
                databaseManager.deleteTable(SqlQueryConstant.MY_ORDER_DETAILS_TABLE);
                databaseManager.deleteTable(SqlQueryConstant.MY_ORDER_PRODUCT_DETAILS);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(Constant.USER_EMAIL, "");
                editor.putString(Constant.USER_EMAIL, "");
                editor.commit();
                Intent intent2 = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent2);
                finish();
                //                startActivity(intent1);
                break;
            case 8:

                fragment = new HomeFragment();
                title = getString( R.string.home_name);
                setActivateTab(1);
//                BtnTextColor(true, false, false, false);
                break;
            case 9:
                fragment = new DealsFragment();
                title = "Deals";
                setActivateTab(2);
                break;
            case 10:
                fragment = new BuzzFragment();
                title = "Events& Buzz";
                setActivateTab(3);
                break;
            case 11:
                fragment = new OfferFragment();
                title = "In Store Offers";
                setActivateTab(4);
                break;
            case 12:
                fragment = new CouponFragment();
                title = "Coupons";
                setActivateTab(5);
                break;
            case 13:
                fragment = new FinderFragment();
                title = getString( R.string.home_name);
                setActivateTab(6);
                break;
            default:
                break;
        }
*/

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.frame_layout, fragment);
            fragmentTransaction.commit();

          //  s = new SpannableString(title);
//            s.setSpan(new TypefaceSpan(this, "enso.ttf"), 0, s.length(),
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // set the toolbar title
            getSupportActionBar().setTitle("Dashboard");
        }
    }

}
