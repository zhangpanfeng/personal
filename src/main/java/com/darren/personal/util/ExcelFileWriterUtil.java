package com.darren.personal.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileWriterUtil {
    private static final Logger LOG = Logger.getLogger(ExcelFileWriterUtil.class);
    private static final String DEFAULT_DELIMITER = "\\|";

    /**
     * Write Excel file suffix is .xlsx
     * 
     * @param path
     *            File path
     * @param title
     *            Title list [column_title1, column_title1, column_title1 ......]
     * @param content
     *            Content list [column1|column2|column3 ......, column1|column2|column3 ......]
     * @return True: write success, False: write failure
     * @throws IOException
     */
    public static boolean writeExcel(String path, List<String> title, List<String> content) throws IOException {
        FileOutputStream output = new FileOutputStream(path);
        boolean result = writeExcel(output, title, content);

        return result;
    }

    /**
     * Write Excel file suffix is .xlsx
     * 
     * @param output
     *            File output stream
     * @param title
     *            Title list [column_title1, column_title1, column_title1 ......]
     * @param content
     *            Content list [column1|column2|column3 ......, column1|column2|column3 ......]
     * @return True: write success, False: write failure
     * @throws IOException
     */
    public static boolean writeExcel(OutputStream output, List<String> title, List<String> content) throws IOException {
        if (content == null || output == null) {
            if (output != null) {
                output.close();
            }
            LOG.error("Parameter error!");

            return false;
        }
        int titleLine = 0;
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");
        // title line
        if (title != null) {
            titleLine = 1;
            int colCount = title.size();
            XSSFRow xssfRow = xssfSheet.createRow(0);
            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                XSSFCell xssfCell = xssfRow.createCell(colIndex);
                xssfCell.setCellValue(title.get(colIndex));
            }
        }

        // content line
        int rowCount = content.size();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            XSSFRow xssfRow = xssfSheet.createRow(rowIndex + titleLine);
            String line = content.get(rowIndex);
            String[] lineArray = line.split(DEFAULT_DELIMITER);
            int colCount = lineArray.length;
            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                XSSFCell xssfCell = xssfRow.createCell(colIndex);
                xssfCell.setCellValue(lineArray[colIndex]);
            }
        }

        xssfWorkbook.write(output);
        xssfWorkbook.close();
        output.close();

        return true;
    }
}
