package pe.edu.ulima.petapp.ui.navigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.ui.navigator.Item.AddPetFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fm =getSupportFragmentManager();
        if (id == R.id.nav_add_pet) {
            fragment = new AddPetFragment();
        } else if (id == R.id.nav_profile_pet) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send_sms) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_all_sms) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_activities) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_new_activity) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        } else if(id == R.id.nav_setting) {
            Toast.makeText(getBaseContext(),"Próximamente",Toast.LENGTH_SHORT).show();
        }

        if(fragment!=null)
            fm.beginTransaction().replace(R.id.container,fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
