package com.scheduledtask;

import com.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zxy
 * @date 2021/10/12 16:12
 * @description
 */
@Component
public class CreateTableAndClearData {

    SimpleDateFormat ymSdf = new SimpleDateFormat("yyyyMM");

    private static String tag = "CreateTableAndClearData====";

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(CreateTableAndClearData.class);

    @Value("${deviceDbAndTable.dbName}")
    private String dbName;

    @Value("${deviceDbAndTable.tableName}")
    private String tableName;

    @Autowired
    private DeviceService deviceService;

    // 每6小时执行一次,检查本月监控数据表和下月监控数据表是否已建，未建则建立
    @Scheduled(fixedRate = 1000 * 60 * 60 * 6)
    public void dealProductInfoProcess(){
        logger.info(tag + "定时创建表+清理2年前数据，程序启动。。。。");
        try{
            Date nowDate = new Date();

            //本月
            String currentYm = ymSdf.format(nowDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.MONTH, +1);
            //下月
            String nextYm = ymSdf.format(calendar.getTime());

            //两年前的月份
            calendar.add(Calendar.MONTH, -26);
            String lastTwoYearMonth = ymSdf.format(calendar.getTime());

            //检查本月监控表是否已建立
            int currentYmTableRet = deviceService.createLBDataTableAndIndex(this.dbName, this.tableName + "_" + currentYm);

            //检查下月监控表是否已建立
            int nextYmTableRet = deviceService.createLBDataTableAndIndex(this.dbName, this.tableName + "_" + nextYm);

            //清理两年前对应表，例如202110对应的是201909
            int dropTwoYearTableRet = deviceService.dropTable(this.dbName, this.tableName + "_" + lastTwoYearMonth);

            logger.info(tag + "定时创建表+清理2年前数据，处理结果：本月表=" + currentYmTableRet + ",下月表=" + nextYmTableRet + ",清理结果=" + dropTwoYearTableRet);
        }catch (Exception e){
            logger.error("定时创建表+清理2年前数据--异常！！",e.getMessage());
            e.printStackTrace();
        }

    }
}
