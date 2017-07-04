package com.machinecontroler.myroomapplication;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Richie on 24-09-2016.
 */
public class HttpPost {

    public static String getdata(Requestdata p) {



        BufferedReader read = null;
        String uri = p.getUri();

        // this statement is used to append the parameters


		/* JSONObject jsn = new JSONObject(p.getParams());
		 String params =  "prams=" + jsn.toString();
		*/
        if (p.getMethod().equals("GET")) {
            uri += "?" + p.getEncodedParams();
        }

        try {
            URL url = new URL(uri);
            // http url connnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());
            // get data from your the url


//			  JSONObject jsn = new JSONObject(p.getParams());
            //JSONObject josn = new JSONObject();
            //jsn.accumulate("author", "Robin Sharma");

//			  String params = "userID" + jsn.toString();
            //String param2 =  "categories=" + josn.toString();

            if (p.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(
                        con.getOutputStream());
                writer.write(p.getEncodedParams());
                writer.flush();
            }

            StringBuilder sb = new StringBuilder();
            read = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String line;

            // as long as i get data it's not null
            while ((line = read.readLine()) != null) {
                sb.append(line + "\n");
            }
            // return the content to the sb string.
            return sb.toString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

}
