package com.machinecontroler.myroomapplication;

/**
 * Created by Richie on 17-05-2015.
 */



import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Richie on 28-04-2015.
 */
public class HttpManger {

    public static String getData(String url) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {

            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            StringBuilder build = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;


            while ((line = reader.readLine()) != null) {
                build.append(line + "\n");
            }

            return build.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
/*
    public static String getData(String url,String username,String pwd){

        BufferedReader reader=null;
        byte[] loginByte = (username+ ":" +pwd).getBytes() ;
        StringBuilder loginbuild = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginByte, Base64.DEFAULT));

*/
/*
       String uri = p.getUri();
if(p.getMethod().equals("GET")){
   uri+="?"+p.getEncodedParams();
}
*//*

        try{
            URL uri = new URL(url);
            HttpURLConnection con = (HttpURLConnection) uri.openConnection();
            con.addRequestProperty("Authorization",loginbuild.toString());
//
//           JSONObject json = new JSONObject(p.getParams());
//           String params  = "userID="+ json.toString();


*/
/*
  if(p.getMethod().equals("POST")){
           StringBuilder build = new StringBuilder().append("Basic ")
                   .append(Base64.encode(loginByte,Base64.DEFAULT));

      con.setDoOutput(true);
      OutputStreamWriter write = new OutputStreamWriter(con.getOutputStream());
      write.write(p.getEncodedParams());
     // write.write(params);
      write.flush();
  }
*//*

            StringBuilder buil = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while((line = reader.readLine())!=null){
                buil.append(line+"\n");
            }

            return buil.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
*/


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
//            OkHttpClient client = new OkHttpClient();

            // http url connnection
//            HttpURLConnection con = client.open(url);

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
                OutputStreamWriter writer = new OutputStreamWriter(                        con.getOutputStream());
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

    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }return null;
        }


}




