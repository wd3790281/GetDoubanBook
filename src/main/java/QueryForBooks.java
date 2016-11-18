import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by dingwang on 2016/11/18.
 */
public class QueryForBooks implements Runnable {

    private String query;

    public QueryForBooks(String query) {
        this.query = query;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.douban.com/v2/book/search?tag=" + query +"&count=100";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Gson gson = new Gson();
        try {

            Response response = client.newCall(request).execute();

            JsonElement jelement = new JsonParser().parse(response.body().string());
            JsonObject jsobject = jelement.getAsJsonObject();
            JsonArray books = jsobject.getAsJsonArray("books");
            int counts = jsobject.get("count").getAsInt();
            int total = jsobject.get("total").getAsInt();
            for (JsonElement jeInBook : books) {
                Book book = gson.fromJson(jeInBook, Book.class);
                if (Integer.parseInt(book.getRating().getNumRaters()) >= 1000) {
                    GetBook.validBooks.add(book);
                }
            }
            int pages = total/counts;

            for (int i = 0; i <= pages; i++) {
                String url2 = "https://api.douban.com/v2/book/search?tag=" + query + "&start=" + i*100 +"&count=100";
                Request request2 = new Request.Builder()
                        .url(url2)
                        .build();
                Response response2 = client.newCall(request2).execute();

                JsonElement jelement2 = new JsonParser().parse(response2.body().string());
                JsonObject jsobject2 = jelement2.getAsJsonObject();
                JsonArray books2 = jsobject2.getAsJsonArray("books");

                for (int j=0; j < books2.size(); j++){
                    JsonElement bookJson = books2.get(j);
                    Book getted = gson.fromJson(bookJson.getAsJsonObject(), Book.class);

                    if (Integer.parseInt(getted.getRating().getNumRaters()) >= 1000) {

                        if (!GetBook.validBooks.contains(getted)) {
                            GetBook.validBooks.add(getted);
                        }
                        System.out.println(getted.getTitle());
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
