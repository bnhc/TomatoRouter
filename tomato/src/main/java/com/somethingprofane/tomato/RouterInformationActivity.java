package com.somethingprofane.tomato;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RouterInformationActivity extends ActionBarActivity {

    @InjectView(R.id.new_request_button)Button requestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate your layout and set it to the screen
        setContentView(R.layout.activity_router_information);




        // Inject Button
        ButterKnife.inject(this);

        // get the view by Id from the layout that you just inflated
        //TextView textview = (TextView) findViewById(R.id.textView);

        // now that you have the object you can access different methods on it, like setText("").
        //textview.setText("Joel this is how you set text");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // Check to see if on Honeycomb for ActionBar API. Not sure why...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            //Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @OnClick(R.id.new_request_button)
    public void requestHTML(Button button){
        TextView textview = (TextView) findViewById(R.id.textView);
        new GetURLTask().execute(textview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_new_page, menu);
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

    private class GetURLTask extends AsyncTask<TextView, Void, String> {
        TextView inTextView;
        String results = "N/A";
        private Parser parserClass = new Parser();

        @Override
        protected String doInBackground(TextView... textViews) {
            this.inTextView = textViews[0];
            String http_id = null;
            http_id = new Parser().GetRouterHTTPId();
            try {
                String html = new Parser().PostToWebadress("http://192.168.1.1/status-data.jsx","root","admin",new Parser().buildParamsMap("_http_id",http_id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return GetHTMLFromURL();
        }

        final String GetHTMLFromURL() {
            //EditText mEdit = (EditText)findViewById(R.id.new_IP_textInput);
            String url = "http://www.duoh.com/404";//String.valueOf(mEdit.getText());

            results = "Test" ;
            return results;
        }

        protected void onPostExecute(String html) {

            // access the activity thread
            inTextView.setText(html);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_open_new_page, container, false);
            return rootView;
        }
    }

}