import com.google.gson.Gson;

/**
 * Created by dingwang on 2016/11/9.
 */

public class Book {
    private String title;
    private String price;
    private String pubdate;
    private Rate rating;
    private String[] author;
    private String publisher;
    private String id;

    public Book(String title, String price, String pubdate, String rating, String author, String publisher, String id){
        Gson gson = new Gson();
        this.title = title;
        this.price = price;
        this.pubdate = pubdate;
        this.rating = gson.fromJson(rating, Rate.class);
        this.author = gson.fromJson(author, String[].class);
        this.publisher = publisher;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPubdate() {
        return pubdate;
    }

    public Rate getRating() {
        return rating;
    }

    public String[] getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setRating(Rate rating) {
        this.rating = rating;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
