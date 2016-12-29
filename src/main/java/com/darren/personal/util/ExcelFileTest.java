package com.darren.personal.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExcelFileTest {
    private static final String BASE_PATH = System.getProperty("user.dir") + "\\files\\";
    private static final String INPUT_PATH = BASE_PATH + "input.xlsx";
    private static final String OUTPUT_PATH = BASE_PATH + "output.xlsx";
    public static void main(String[] args) {
        try {
            Map<String, List<String>> result = ExcelFileReaderUtil.readExcel(INPUT_PATH);
            Collection<List<String>> collections =  result.values();
            List<String> title = new ArrayList<>();
            title.add("A");
            title.add("B");
            title.add("C");
            for(List<String> list: collections){
                ExcelFileWriterUtil.writeExcel(OUTPUT_PATH, title, list);
//                for(String line: list){
//                    System.out.println(line);
//                }
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
