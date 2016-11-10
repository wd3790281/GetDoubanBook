import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by dingwang on 2016/11/9.
 */
public class GetBook {
    public static void main(String[] args) {
        GetBook get = new GetBook();
        get.searchForbooks("互联网 编程 算法");
    }

    public void searchForbooks(String query) {
        ArrayList<Book> validBooks = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.douban.com/v2/book/search?q=" + query+"&count=100";
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
                    validBooks.add(book);
                }
            }
            int pages = total/counts;

            for (int i = 0; i <= pages; i++) {
                String url2 = "https://api.douban.com/v2/book/search?q=" + query + "&start=" + i+"&count=100";
                Request request2 = new Request.Builder()
                        .url(url2)
                        .build();
                Response response2 = client.newCall(request2).execute();

                JsonElement jelement2 = new JsonParser().parse(response2.body().string());
                JsonObject jsobject2 = jelement2.getAsJsonObject();
                JsonArray books2 = jsobject2.getAsJsonArray("books");

                for (int j=0; j < books2.size(); j++){
                    Book getted = gson.fromJson(books2.get(j), Book.class);

                    if (Integer.parseInt(getted.getRating().getNumRaters()) >= 1000) {
                        System.out.println(i);
                        System.out.println(getted.getTitle());
                        if (!validBooks.contains(getted)) {
                            validBooks.add(getted);
                        }
                        System.out.println(getted.getTitle());
                    }
                }

            }

            ArrayList<Book> sorted = quickSort(validBooks, 0, validBooks.size()-1);


            writeToFile(sorted);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Book> quickSort(ArrayList<Book> books, int low, int high) {

        if (low >= high)
            return books;
        int middle = low + (high - low) / 2;
        Book pivot = books.get(middle);

        int i = low, j = high;
        while (i <= j) {
            while (Float.parseFloat(books.get(i).getRating().getAverage()) < Float.parseFloat(pivot.getRating().getAverage())) {
                i++;
            }

            while (Float.parseFloat(books.get(j).getRating().getAverage()) > Float.parseFloat(pivot.getRating().getAverage())) {
                j--;
            }

            if (i <= j) {
                Book temp = books.get(i);
                books.set(i, books.get(j));
                books.set(j, temp);
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(books, low, j);

        if (high > i)
            quickSort(books, i, high);

        return books;
    }

    public void writeToFile(ArrayList<Book> books) {

        int total = books.size();
        if (total > 100) {
            total = 100;
        }
        Workbook wb = new HSSFWorkbook();

        Sheet sheet = wb.createSheet("new sheet");


        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("书名");
        row.createCell(2).setCellValue("评分");
        row.createCell(3).setCellValue("评价人数");
        row.createCell(4).setCellValue("作者");
        row.createCell(5).setCellValue("出版社");
        row.createCell(6).setCellValue("出版日期");
        row.createCell(7).setCellValue("价格");

        for(int i = 0; i < total; i++) {
            Row row2 = sheet.createRow(i+1);

            row2.createCell(0).setCellValue(books.get(i).getId());
            row2.createCell(1).setCellValue(books.get(i).getTitle());
            row2.createCell(2).setCellValue(books.get(i).getRating().getAverage());
            row2.createCell(3).setCellValue(books.get(i).getRating().getNumRaters());
            String authors = "";
            for (String author: books.get(i).getAuthor()){
                authors = authors + author + " ";
            }
            row2.createCell(4).setCellValue(authors);
            row2.createCell(5).setCellValue(books.get(i).getPublisher());
            row2.createCell(6).setCellValue(books.get(i).getPubdate());
            row2.createCell(7).setCellValue(books.get(i).getPrice());
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("workbook.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println("File error");
            e.printStackTrace();
        }

    }
}
