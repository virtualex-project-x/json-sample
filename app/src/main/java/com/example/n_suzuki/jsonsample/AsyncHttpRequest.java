package com.example.n_suzuki.jsonsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n-suzuki on 2015/01/05.
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

  private Activity mainActivity;
  //  private String strUrl = "http://10.0.2.2:8080/jersey-sample/resource/simple";
  private String strUrl = "http://1.latest.slim3demo001.appspot.com/AjaxTweet.json";
  private String json = null;


  public AsyncHttpRequest(Activity activity) {
    this.mainActivity = activity;
  }

  @Override
  protected String doInBackground(String... paramList) {

    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpParams params = httpClient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 10000);
    HttpConnectionParams.setSoTimeout(params, 10000);
    HttpGet httpRequest = new HttpGet(strUrl);
    HttpResponse httpResponse = null;

    try {
      httpResponse = httpClient.execute(httpRequest);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      HttpEntity httpEntity = httpResponse.getEntity();

      try {
        json = EntityUtils.toString(httpEntity);
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          httpEntity.consumeContent();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    httpClient.getConnectionManager().shutdown();

    return json;
  }

  @Override
  protected void onPostExecute(String result) {

    ArrayAdapter<String>
        adapter =
        new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1);

    try {
      JSONArray jsonArray = new JSONArray(result);
      int cnt = jsonArray.length();
      List<JSONObject> jlist = new ArrayList<>();
      for (int i = 0; i < cnt; i++) {
        jlist.add(jsonArray.getJSONObject(i));
      }
      for (JSONObject obj : jlist) {
        String item = obj.getString("content");
//        String item = obj.getString("name1");
        adapter.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    ListView listView = (ListView) mainActivity.findViewById(R.id.jsonlistview);
    listView.setAdapter(adapter);
  }

}
