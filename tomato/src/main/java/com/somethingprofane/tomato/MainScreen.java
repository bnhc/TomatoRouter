package com.somethingprofane.tomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainScreen extends ActionBarActivity {

    @InjectView(R.id.mainscr_btnRouter)ImageButton routerButton;
    @InjectView(R.id.mainscr_btnDevices)ImageButton devicesButton;
    @InjectView(R.id.mainscr_btnBasic)ImageButton basicButton;
    @InjectView(R.id.mainscr_btnAdvanced)ImageButton advancedButton;
    @InjectView(R.id.mainscr_btnGroups)ImageButton groupsButton;
    @InjectView(R.id.mainscr_btnProfiles)ImageButton profilesButton;
    @InjectView(R.id.mainscr_btnLogout)ImageButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ButterKnife.inject(this);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MainScreen.MODE_PRIVATE);
        Intent i = getIntent();
        Router router = (Router) i.getParcelableExtra("passed_router");

        // Resetting the font of the Title in the main screen. May not be working...
        Typeface sensationFont = Typeface.createFromAsset(getAssets(), "SourceSansPro-Regular.otf");
        TextView title = (TextView) findViewById(R.id.mainscr_Title);
        title.setTypeface(sensationFont);

        // On touch listener for Router ImageButton
        routerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    routerButton.setBackgroundResource(R.drawable.router_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    routerButton.setBackgroundResource(R.drawable.router_icon_flat_pressed);
                }
                return false;
            }
        });

        devicesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    devicesButton.setBackgroundResource(R.drawable.devices_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    devicesButton.setBackgroundResource(R.drawable.devices_icon_flat_pressed);
                }
                return false;
            }
        });

        basicButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    basicButton.setBackgroundResource(R.drawable.basic_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    basicButton.setBackgroundResource(R.drawable.basic_icon_flat_pressed);
                }
                return false;
            }
        });

        advancedButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    advancedButton.setBackgroundResource(R.drawable.advanced_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    advancedButton.setBackgroundResource(R.drawable.advanced_icon_flat_pressed);
                }
                return false;
            }
        });

        groupsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    groupsButton.setBackgroundResource(R.drawable.groups_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    groupsButton.setBackgroundResource(R.drawable.groups_icon_flat_pressed);
                }
                return false;
            }
        });

        profilesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    profilesButton.setBackgroundResource(R.drawable.profiles_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    profilesButton.setBackgroundResource(R.drawable.profiles_icon_flat_pressed);
                }
                return false;
            }
        });

        logoutButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    logoutButton.setBackgroundResource(R.drawable.logout_icon_flat_pressed);
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    logoutButton.setBackgroundResource(R.drawable.logout_icon_flat_pressed);
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
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

    @OnClick(R.id.mainscr_btnRouter)
    public void LoginClicked(ImageButton routerButton){
        new moveToRouter().execute();
    }


    private class moveToRouter extends AsyncTask<TextView, Void, String> {

        @Override
        protected String doInBackground(TextView... textViews) {
            Intent intent = new Intent(MainScreen.this, RouterOverviewActivity.class);
            MainScreen.this.startActivity(intent);
            // Finish the activity;
            return null;
        }
    }

    @OnClick(R.id.mainscr_btnDevices)
    public void devicesClicked (ImageButton devicesButton){
        new moveToDevices().execute();
    }

    private class moveToDevices extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = new Intent(MainScreen.this, DeviceScreen.class);
            MainScreen.this.startActivity(intent);
            return null;
        }
    }

}
