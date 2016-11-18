import java.util.ArrayList;


/**
 * Created by dingwang on 2016/11/9.
 */
public class GetBook {

    public static ArrayList<Book> validBooks = new ArrayList<>();

    public static void main(String[] args) {

        QueryForBooks query1 = new QueryForBooks("互联网");
        QueryForBooks query2 = new QueryForBooks("算法");
        QueryForBooks query3 = new QueryForBooks("编程");

        query1.run();
        query2.run();
        query3.run();

        quickSort(validBooks, 0, validBooks.size()-1);

        new CreateExcel().writeToFile(validBooks);
     }


    public static void quickSort(ArrayList<Book> books, int low, int high) {

        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        Book pivot = books.get(middle);

        int i = low, j = high;
        while (i <= j) {
            while (Float.parseFloat(books.get(i).getRating().getAverage()) > Float.parseFloat(pivot.getRating().getAverage())) {
                i++;
            }

            while (Float.parseFloat(books.get(j).getRating().getAverage()) < Float.parseFloat(pivot.getRating().getAverage())) {
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
    }


}
