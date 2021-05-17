package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONreader {

    private final String BaseUrl = "https://api.themoviedb.org/3/";
    private final String api = "?api_key=c4ca447c00aab9e96d6f9b202dbdf289";
    private final String popular ="movie/popular";
    private final String movie = "movie/";
    private final String tv = "tv/";
    private final String SeriesPopular = "tv/popular";
    private final String Trends = "/trending/all/day";
    private final String language = "&language=en-US";
    private final String Search = "&query=";

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
    public ArrayList<items> getEpisodes(int id ,int se) throws IOException, JSONException {
        JSONArray myArr = getJSON(new URL(BaseUrl+tv+id+"/season/"+se+api+language)).getJSONArray("episodes");
        ArrayList<items> items = new ArrayList<>();
        for(int i=0;i<myArr.length();i++){
            items item = new items();
            item.setID(myArr.getJSONObject(i).getInt("id"));
            item.setType("tv");
            try {
                item.setBackground("http://image.tmdb.org/t/p/w185/" + myArr.getJSONObject(i).getString("still_path"));
            }catch(Exception e){
                break;
            }
            item.setTitle(myArr.getJSONObject(i).getString("name"));
            item.setDate(myArr.getJSONObject(i).getString("air_date").split("-")[0]);
            item.setDuration(myArr.getJSONObject(i).getInt("episode_number"));
            item.setDescription(myArr.getJSONObject(i).getString("overview"));
            item.setRate(""+myArr.getJSONObject(i).getDouble("vote_average"));
            items.add(item);
        }
        return items;
    }
    public int getSeasonEp(int id,int i) throws IOException, JSONException {
        return getJSON(new URL(BaseUrl+tv+id+"/season/"+i+api+language)).getJSONArray("episodes").length();
    }
    public items getSeriesDetails(int id) throws IOException, JSONException {
        items item = new items();
        System.out.println(BaseUrl+tv+id+api+language);
        JSONObject jsonObj = getJSON(new URL(BaseUrl+tv+id+api+language));

        item.setID(id);
        item.setType("tv");
        item.setImgUrl(jsonObj.getString("poster_path"));
        item.setBackground(jsonObj.getString("backdrop_path"));
        item.setTitle(jsonObj.getString("name"));
        item.setDate(jsonObj.getString("last_air_date").split("-")[0]);
        item.setRate(""+jsonObj.getDouble("vote_average"));
        item.setDuration(jsonObj.getInt("number_of_seasons"));
        item.setDescription(jsonObj.getString("overview"));
        item.setLanguage(jsonObj.getString("original_language"));
        JSONArray test = jsonObj.getJSONArray("genres");
        String[] str = new String[10];
        for(int i=0;i<test.length();i++)
            str[i] = test.getJSONObject(i).getString("name");
        item.setGenres(str);
        item.setTagline(jsonObj.getString("tagline"));
        return item;
    }

    public items getMovieDetails(int id) throws IOException, JSONException {
        items item = new items();
        System.out.println(BaseUrl+movie+id+api+language);
        JSONObject jsonObj = getJSON(new URL(BaseUrl+movie+id+api+language));

        item.setID(id);
        item.setType("movie");
        item.setImgUrl(jsonObj.getString("poster_path"));
        item.setBackground(jsonObj.getString("backdrop_path"));
        item.setTitle(jsonObj.getString("title"));
        item.setDate(jsonObj.getString("release_date").split("-")[0]);
        item.setRate(""+jsonObj.getDouble("vote_average"));
        item.setDuration(jsonObj.getInt("runtime"));
        item.setDescription(jsonObj.getString("overview"));
        item.setLanguage(jsonObj.getString("original_language"));
        JSONArray test = jsonObj.getJSONArray("genres");
        String[] str = new String[10];
        for(int i=0;i<test.length();i++)
            str[i] = test.getJSONObject(i).getString("name");
        item.setGenres(str);
        item.setTagline(jsonObj.getString("tagline"));
        return item;
    }

    public String CheckType(int id){
        String str="tv";
        try {
            getJSON(new URL(BaseUrl+tv+id+api+language));
        } catch (IOException | JSONException e) {
            str="movie";
        }
        return str;
    }

    public ArrayList<items> Search(String str) throws IOException, JSONException {
        ArrayList<items> items = new ArrayList<>();
        JSONArray myArr = getJSON(new URL(BaseUrl+"/Search/movie"+api+Search+str)).getJSONArray("result");

        for(int i=0;i<5;i++){
            items item = new items();
            item.setID(myArr.getJSONObject(i).getInt("id"));
            item.setType(myArr.getJSONObject(i).getString("media_type"));
            item.setImgUrl("http://image.tmdb.org/t/p/w154/"+myArr.getJSONObject(i).getString("poster_path"));
            item.setTitle(myArr.getJSONObject(i).getString("title"));
            if(item.getType().equals("movie"))
                item.setDate(myArr.getJSONObject(i).getString("release_date").split("-")[0]);
            else
                item.setDate(myArr.getJSONObject(i).getString("first_air_date").split("-")[0]);
            items.add(item);
        }
        return items;
    }

    public items getMostTrends() throws JSONException, IOException {
        return getMovieDetails(getJSON(new URL(BaseUrl+Trends+api)).getJSONArray("results").getJSONObject(0).getInt("id"));
    }

    public List<items> get_Trends() throws Exception {

        List<items> items = new ArrayList<>();

        JSONArray myArr = getJSON(new URL(BaseUrl+Trends+api)).getJSONArray("results");

        for(int i=1;i<20;i++){
            items item = new items();
            item.setID(myArr.getJSONObject(i).getInt("id"));
            item.setType(myArr.getJSONObject(i).getString("media_type"));
            item.setImgUrl("http://image.tmdb.org/t/p/w154/"+myArr.getJSONObject(i).getString("poster_path"));
            if(myArr.getJSONObject(i).getString("media_type").equals("movie"))
                item.setTitle(myArr.getJSONObject(i).getString("title"));
            else
                item.setTitle(myArr.getJSONObject(i).getString("name"));
            if(myArr.getJSONObject(i).getString("media_type").equals("movie"))
                item.setDate(myArr.getJSONObject(i).getString("release_date").split("-")[0]);
            else
                item.setDate(myArr.getJSONObject(i).getString("first_air_date").split("-")[0]);
            item.setDescription(myArr.getJSONObject(i).getString("overview"));
            item.setRate(""+myArr.getJSONObject(i).getDouble("vote_average"));
            items.add(item);
        }
        return items;
    }
}
