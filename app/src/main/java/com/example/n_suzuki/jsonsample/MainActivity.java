package com.example.n_suzuki.jsonsample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

  private RequestQueue mQueue;

  private Response.Listener<JSONArray> mListener= new Response.Listener<JSONArray>() {
    @Override
    public void onResponse(JSONArray jsonArrayResponse){
      ArrayAdapter<String> arrayAdapter =
          new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

      try {
        int cnt = jsonArrayResponse.length();
        List<JSONObject> jsonList = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
          jsonList.add(jsonArrayResponse.getJSONObject(i));
        }
        for (JSONObject jsonObject : jsonList) {
          String item = jsonObject.getString("content");
          arrayAdapter.add(item);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

      ListView listView = (ListView) findViewById(R.id.jsonlistview);
      listView.setAdapter(arrayAdapter);
    }
  };

  private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
      // 処理記載
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // AsyncTaskを使用したHTTPリクエスト
    // AsyncHttpRequest task = new AsyncHttpRequest(this);
    // task.execute();

    // Volleyを使用したHTTPリクエスト
    String url = "http://1.latest.slim3demo001.appspot.com/AjaxTweet.json";

    mQueue = Volley.newRequestQueue(this);
    mQueue.add(new JsonArrayRequest(url,  mListener, mErrorListener));

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}


