package com.example.axellarsson.communicoon;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;

public class archive extends Fragment {

    String urladress="http://communicoon.com/app/GET/test.php";
    String[] name;
    String[] image;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.archive, container, false);

        listView=(ListView)view.findViewById(R.id.archiveListView);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        customListView CustomListView = new customListView( getActivity(), this.name, this.image);
        listView.setAdapter(CustomListView);



        return view;
    }
    private void collectData()
    {
        try
        {
            URL url = new URL(urladress);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line=br.readLine())!=null);
            {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            name = new String[ja.length()];
            image = new String[ja.length()];

            for (int i=0;i<=ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                name[i] = jo.getString("name");
                image[i] = jo.getString("image");

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
    }

