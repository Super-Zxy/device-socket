package com.util;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * @author sunshuai
 * @data 2021/3/25
 * @Description
 */
public class ExcelUtils {


        private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

        /**
         * 课程excel
         * @param in
         * @param fileName
         * @return
         * @throws Exception
         */
        public static List getCourseListByExcel(InputStream in, String fileName) throws Exception {

            List list = new ArrayList<>();

            // 创建excel工作簿
            Workbook work = getWorkbook(in, fileName);
            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }

            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            for (int i = 0; i < work.getNumberOfSheets(); i++) {

                sheet = work.getSheetAt(i);
                if(sheet == null) {
                    continue;
                }

                // 滤过第一行标题
                for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }

                    List<Object> li = new ArrayList<>();

                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);

                        li.add(cell);
                    }
                    list.add(li);
                }
            }
            work.close();
            return list;
        }

        /**
         * 判断文件格式
         * @param in
         * @param fileName
         * @return
         */
        private static Workbook getWorkbook(InputStream in, String fileName) throws Exception {

            Workbook book = null;
            String filetype = fileName.substring(fileName.lastIndexOf("."));

            if(".xls".equals(filetype)) {
                book = new HSSFWorkbook(in);
            } else if (".xlsx".equals(filetype)) {
                book = new XSSFWorkbook(in);
            } else {
                throw new Exception("请上传excel文件！");
            }

            return book;
        }


    public static List<Map<String,Object>> readExcel(InputStream in) {
        logger.info("开始解析xls...");
         POIFSFileSystem fs;
         HSSFWorkbook wb;
        HSSFSheet sheet;
        HSSFRow row;


       int sheetNo = 1;
        sheetNo--;//从1开始及从0开始

        Map<String,Object> dataMap = null;
        List<Map<String,Object>> dataList= new ArrayList<>();
        String value = "";
        try {
            fs = new POIFSFileSystem(in);
            wb = new HSSFWorkbook(fs);

        sheet = wb.getSheetAt(sheetNo);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] keyArray = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            keyArray[i] = getCellFormatValue(row.getCell((short) i));
        }
        int rowNum = sheet.getLastRowNum();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            dataMap= new HashMap<>();
            row = sheet.getRow(i);
            if(row!=null){
                int j = 0;
                while (j < colNum) {
                    //这里把列循环到Map
                    if(row.getCell((short) j)!=null){
                        value = getCellFormatValue(row.getCell((short) j)).trim();
                        dataMap.put(keyArray[j],value);
                    }
                    j++;
                }
                value = "";
                dataList.add(dataMap);
            }
        }
        logger.info("解析xls完成...");
        } catch (IOException e) {

        }
        try {
            if(in!=null)
                in.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return dataList;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC:
                case FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        DecimalFormat df = new DecimalFormat("0");
                        String dfStr = df.format(cell.getNumericCellValue());
                        cellvalue = dfStr;
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static List<Map<String, Object>> readeExcelData(InputStream excelInputSteam,
                                                           int sheetNumber,
                                                           int headerNumber,
                                                           int rowStart) throws IOException, InvalidFormatException {
        //需要的变量以及要返回的数据
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<String> headers = new ArrayList<String>();
        //生成工作表
        Workbook workbook = WorkbookFactory.create(excelInputSteam);
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Row header = sheet.getRow(headerNumber);
        //最后一行数据
        int rowEnd = sheet.getLastRowNum();
        DataFormatter dataFormatter = new DataFormatter();
        //获取标题信息
        for (int i = 0; i < header.getLastCellNum(); ++i) {
            Cell cell = header.getCell(i);
            headers.add(dataFormatter.formatCellValue(cell));
        }
        //获取内容信息
        for (int i = rowStart; i <= rowEnd; ++i) {
            Row currentRow = sheet.getRow(i);
            if (Objects.isNull(currentRow)) {
                continue;
            }
            Map<String, Object> dataMap = new HashMap<>();
            for (int j = 0; j < headers.size(); ++j) {
                int temp = headers.size();
                //将null转化为Blank
                Cell data = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                data.setCellType(STRING);
                if (Objects.isNull(data)) {     //感觉这个if有点多余
                    dataMap.put(headers.get(j), null);
                } else {
                    switch (data.getCellType()) {   //不同的类型分别进行存储
                        case STRING:
                            dataMap.put(headers.get(j), data.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:

                                dataMap.put(headers.get(j), String.valueOf(data.getNumericCellValue()));

                            break;
                        case FORMULA:
                            dataMap.put(headers.get(j), data.getCellFormula());
                            break;
                        case BOOLEAN:
                            dataMap.put(headers.get(j), data.getBooleanCellValue());
                            break;
                        default:
                            dataMap.put(headers.get(j), null);
                    }
                }
            }
            result.add(dataMap);
        }
        return result;
    }
}
