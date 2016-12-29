package com.darren.personal.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReaderUtil {
    private static final Logger LOG = Logger.getLogger(ExcelFileReaderUtil.class);
    private static final String DEFAULT_DELIMITER = "|";

    /**
     * Read Excel file suffix is .xlsx
     * 
     * @param path
     *            File path
     * @return Map<String, List<String>>, key is sheet name, value is line collections
     * @throws IOException
     */
    public static Map<String, List<String>> readExcel(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        Map<String, List<String>> result = readExcel(input);

        return result;
    }

    /**
     * Read Excel file suffix is .xlsx
     * 
     * @param input
     *            File input stream
     * @return Map<String, List<String>>, key is sheet name, value is line collections
     * @throws IOException
     */
    public static Map<String, List<String>> readExcel(InputStream input) throws IOException {
        Map<String, List<String>> result = readExcel(input, 0, 0, 1, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        return result;
    }

    /**
     * Read Excel file suffix is .xlsx
     * 
     * @param input
     *            File input stream
     * @param startSheetIndex
     *            value [0 1 2 ...]
     * @param endSheetIndex
     *            value [1 2 ...]
     * @param startLine
     *            value [0 1 2 ...] 0 contains header, 1 doesn't contains header
     * @param endLine
     *            value [1 2 3 ...]
     * @param startColumnIndex
     *            value [0 1 2 ...]
     * @param endColumnIndex
     *            value [1 2 3 ...]
     * @return Map<String, List<String>>, key is sheet name, value is line collections
     * @throws IOException
     */
    public static Map<String, List<String>> readExcel(InputStream input, int startSheetIndex, int endSheetIndex,
            int startLine, int endLine, int startColumnIndex, int endColumnIndex) throws IOException {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(input);
        int sheetCount = xssfWorkbook.getNumberOfSheets();
        if (startSheetIndex < 0 || startSheetIndex > sheetCount || startSheetIndex > endSheetIndex || startLine < 0
                || startLine > endLine || startColumnIndex < 0 || startColumnIndex > endColumnIndex) {
            LOG.error("Parameter error!");
            xssfWorkbook.close();
            input.close();

            return result;
        }
        endSheetIndex = endSheetIndex > sheetCount - 1 ? sheetCount - 1 : endSheetIndex;

        // Read the Sheet
        for (int sheetIndex = startSheetIndex; sheetIndex <= endSheetIndex; sheetIndex++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
            if (xssfSheet == null) {
                continue;
            }
            String sheetName = xssfSheet.getSheetName();

            List<String> list = new ArrayList<String>();
            int rowCount = xssfSheet.getLastRowNum();
            endLine = endLine > rowCount ? rowCount : endLine;
            // Read the Row
            for (int rowIndex = startLine; rowIndex <= endLine; rowIndex++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
                if (xssfRow != null) {
                    int columnCount = xssfRow.getPhysicalNumberOfCells();
                    endColumnIndex = endColumnIndex > columnCount - 1 ? columnCount - 1 : endColumnIndex;
                    StringBuilder builder = new StringBuilder();
                    for (int columnIndex = startColumnIndex; columnIndex <= endColumnIndex; columnIndex++) {
                        builder.append(getValue(xssfRow.getCell(columnIndex))).append(DEFAULT_DELIMITER);
                    }
                    // Remove last delimiter
                    String line = builder.substring(0, builder.length() - 1);
                    list.add(line);
                }
            }
            result.put(sheetName, list);
        }

        xssfWorkbook.close();
        input.close();

        return result;
    }

    @SuppressWarnings({ "static-access", "deprecation" })
    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return null;
        }

        if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }
}
