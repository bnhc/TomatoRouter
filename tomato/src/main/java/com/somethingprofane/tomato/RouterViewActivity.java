package com.somethingprofane.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by garrett on 2/25/14.
 */
public class RouterViewActivity extends Activity {


    @InjectView(R.id.mainscr_btnRouter) Button routerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ButterKnife.inject(this);

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
    public void LoginClicked(Button routerButton){
        new retrieveRouterInfo().execute();
    }

    private class retrieveRouterInfo extends AsyncTask<TextView, Void, String> {

        @Override
        protected String doInBackground(TextView... textViews) {

            new Router("http://192.168.1.1","root","admin");

            return null;
        }
    }
}
