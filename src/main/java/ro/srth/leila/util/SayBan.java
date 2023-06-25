package ro.srth.leila.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import ro.srth.leila.annotations.NeedsRevamp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
@NeedsRevamp(reason = "inefficient")
public class SayBan {
    Gson gson = new GsonBuilder().create();

    public JsonArray jArray = new JsonArray();

    public void writeJson(){
        try (FileWriter writer = new FileWriter("C:\\Users\\SRTH_\\Desktop\\leilabot\\saybanned.json")) {
            gson.toJson(jArray, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JsonArray readJson() {

        JsonArray ids = null;
        try (Reader reader = new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\saybanned.json")) {

            ids = gson.fromJson(reader, JsonArray.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

/*
    public int getIndex(String user) {
        JsonArray json = readJson().getAsJsonArray();
        int i;
        for (i = 0; i > json.size(); i++) {
            if (user.equals(json.get(i).getAsString())) {
                System.out.println(json.get(i));
                System.out.println(i);
                break;
            }
        }
        return i;
    }*/
}
