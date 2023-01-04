package ro.srth.leila.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetCopypasta {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getCopypasta(String query) throws Exception{

        if (query.contains(" ")){
            query = query.replaceAll(" ", "%20");
        }


        String url = "https://www.reddit.com/r/copypasta/search.json?q=title:" + query + "&sort=top&restrict_sr=true&t=all&limit=1";

        URL url1 = new URL(url);
        URLConnection request = url1.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        JsonObject jsonObject = gson.fromJson(new InputStreamReader((InputStream) request.getContent()), JsonObject.class);
        String content = jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString();

        if (query.contains("%20")){
            query = query.replaceAll("%20", " ");
        }

        if (jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().length() >= 1950){
            return "cant show this copypasta because it is over 2000 characters, so heres the link: https://www.reddit.com" + jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("permalink").getAsString() + "\n\n**Query: " + query + "**";
        } else if(jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().isEmpty()){
            return "no results came up for this query \n\n**Query: " + query + "**";
        } else {
            return content + "\n\n**Query: " + query + "**";
        } // i hate this but it works
    }
}
