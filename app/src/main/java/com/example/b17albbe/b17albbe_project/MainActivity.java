package com.example.b17albbe.b17albbe_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<President> presidentList=new ArrayList<President>();


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
            return true;



            //startActivity(AboutActivity);
        }

        if (id == R.id.action_refresh) {
            new FetchData().execute();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String kjell = new String("Tomte!");


        Log.d("brom-debug",kjell);




        String[] rawData = {"Matterhorn","Mont Blanc","Denali"};
        // 2. Create a List object with your array from step 1 as in-data
        List<String> listData = new ArrayList<String>(Arrays.asList(rawData));
        // 3. Create an ArrayAdapter object that connects
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.list_item_textview,
                R.id.my_item_textview,presidentList);

        ListView myListView = (ListView)findViewById(R.id.my_listview);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                President president =presidentList.get(position);

                Intent intent = new Intent(getApplicationContext(), PresidentActivity.class);
                intent.putExtra("NAME", president.toString());
                intent.putExtra("LOCATION", president.getLocation());
                intent.putExtra("DATE", president.getDate());
                intent.putExtra("ACHIEVEMENT", president.getAcievement());
                intent.putExtra("SERVED", president.getServed());
                startActivity(intent);
            }
        });
        new FetchData().execute();
    }


    private class FetchData extends AsyncTask<Void,Void,String> {




        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;


            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/b17albbe/webbplatsdesign/json/getdataasjson.php");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();

                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }


        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.
            Log.d("AlbinLog",o);
            presidentList.clear();
            try {
                // Ditt JSON-objekt som Java
                JSONArray json1 = new JSONArray(o);

                for (int k=0;k<json1.length();k++) {
                    JSONObject obj= json1.getJSONObject(k);
                    String id= obj.getString("ID");
                    String name= obj.getString("name");
                    String birthLocation= obj.getString("birthLocation");
                    String birthDate= obj.getString("birthDate");
                    String achievement= obj.getString("achievement");
                    String served= obj.getString("served");

                    President mobj = new President(name,birthLocation,birthDate,achievement,served);
                    presidentList.add(mobj);
                    Log.d("albinecool",mobj.info());
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.list_item_textview,
                        R.id.my_item_textview,presidentList);
                ListView myListView = (ListView)findViewById(R.id.my_listview);
                myListView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e("brom","E:"+e.getMessage());
            }
            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created President class.
        }
    }
}
