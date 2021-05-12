package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONreader {

    private String BaseUrl = "https://api.themoviedb.org/3/";
    private String api = "?api_key=c4ca447c00aab9e96d6f9b202dbdf289";
    private String popular ="movie/popular";
    private String details = "movie/";
    private String language = "&language=en-US";
    private String Search = "&query=";

    private JSONObject getJSON(URL obj) throws IOException, JSONException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    public items getDetails(int id) throws IOException, JSONException {
        items item = new items();
        JSONObject jsonObj = getJSON(new URL(BaseUrl+details+id+api+language));

        item.setImgUrl(jsonObj.getString("poster_path"));
        item.setBackground(jsonObj.getString("backdrop_path"));
        item.setTitle(jsonObj.getString("title"));
        item.setDate(jsonObj.getString("release_date").split("-")[0]);
        item.setRate(""+jsonObj.getInt("vote_average"));
        item.setDuration(jsonObj.getInt("runtime"));
        item.setDescription(jsonObj.getString("overview"));

        return item;
    }

    public items getMostPopular() throws JSONException, IOException {
        return getDetails(getJSON(new URL(BaseUrl+popular+api+language)).getJSONArray("results").getJSONObject(0).getInt("id"));
    }

    public List<items> get_popular() throws Exception {

        List<items> items = new ArrayList<>();

        JSONArray myArr = getJSON(new URL(BaseUrl+popular+api+language)).getJSONArray("results");

        items item;
        for(int i=1;i<20;i++){
            item = new items();
            System.out.println(myArr.getJSONObject(i));
            item.setID(myArr.getJSONObject(i).getInt("id"));
            item.setImgUrl("http://image.tmdb.org/t/p/original/"+myArr.getJSONObject(i).getString("poster_path"));
            item.setTitle(myArr.getJSONObject(i).getString("title"));
            item.setDate(myArr.getJSONObject(i).getString("release_date").split("-")[0]);
            item.setRate(""+myArr.getJSONObject(i).getDouble("vote_average"));
            items.add(item);
        }
        return items;
    }
}
