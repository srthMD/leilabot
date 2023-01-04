package ro.srth.leila.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ro.srth.leila.Bot;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetCopypasta {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getCopypasta(String query) throws Exception{

        String[] triggers = Bot.env.get("COPYPASTASEARCHNSFWTRIGGERS").split(","); // i am not publicly posting this list on github

        if (query.contains(" ")){
            query = query.replaceAll(" ", "%20");
        }

        String content;

        String url = "https://www.reddit.com/r/copypasta/search.json?q=title:" + query + "&sort=top&restrict_sr=true&t=all&limit=1";

        Bot.log.info(url);

        URL url1 = new URL(url);
        URLConnection request = url1.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        JsonObject jsonObject = gson.fromJson(new InputStreamReader((InputStream) request.getContent()), JsonObject.class);
        try{
            content = jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString();
        } catch (IndexOutOfBoundsException e ){
            return "no results came up for this query \n\n**Query: " + query + "**";
        }

        boolean flag = true;

        for (String trigger : triggers) {
            if (content.contains(trigger)) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        System.out.println(flag);


        if (query.contains("%20")){
            query = query.replaceAll("%20", " ");
        }

        if (jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().length() >= 1950) {
            return "cant show this copypasta because it is over 2000 characters, so heres the link: https://www.reddit.com" + jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("permalink").getAsString() + "\n\n**Query: " + query + "**";
        } else if(jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("selftext").getAsString().isEmpty()){
            return "no results came up for this query \n\n**Query: " + query + "**";
        } else if(flag || jsonObject.get("data").getAsJsonObject().get("children").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("over_18").getAsBoolean()){
            return "Post might be nsfw so spoilers added\n\n||" + content + "||\n\n**Query: " + query + "**";
        }else {
            return content + "\n\n**Query: " + query + "**";
        } // i hate this but it works
    }
}
