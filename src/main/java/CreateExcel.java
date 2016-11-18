import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dingwang on 2016/11/18.
 */
public class CreateExcel {

    public void writeToFile(ArrayList<Book> books) {

        int total = books.size();
        if (total > 100) {
            total = 100;
        }
        HSSFWorkbook wb = new HSSFWorkbook();

        Sheet sheet = wb.createSheet("new sheet");


        HSSFCellStyle cellStyle =  wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setWrapText(true);

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

        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
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
