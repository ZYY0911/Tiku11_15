package com.example.tiku11_15.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiku11_15.AppClient;
import com.example.tiku11_15.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.change)
    ImageView change;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private AppClient appClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        title.setText("主界面");
        appClient = (AppClient) getApplication();
        if (!appClient.getUsername().equals("user1")) {
            navigationView.getMenu().findItem(R.id.hld).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Class myClass = null;
                switch (menuItem.getItemId()){
                    case R.id.hld:
                        myClass = Z_HLDGLActivity.class;
                        break;
                    case R.id.clwz:
                        myClass = Z_CLWZActivity.class;
                        break;
                    case R.id.lkcx:
                        myClass = Z_LKCXActivity.class;
                        break;
                    case R.id.shzs:
                        myClass = Z_SHZSActivity.class;
                        break;
                    case R.id.sjfx:
                        myClass = Z_SJFXActivity.class;
                        break;
                }
                if (myClass!=null){
                    startActivity(new Intent(MainActivity.this,myClass));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });
    }

    @OnClick(R.id.change)
    public void onViewClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
