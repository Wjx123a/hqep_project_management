package com.hqep.dataSharingPlatform.sjfwgj.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: NumberAutoNo
 * @author: wjx
 * @data: 2023/5/2 19:13 PM
 */
public class NumberAutoNo {
    public static String AutoBuildNo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("QA-");
        //八位时间 四位自增序列
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        int count = 0;
//        Sequence sequenceDO = sequenceMapper.getSequenceByName("order_info");
//        sequence = sequenceDO.getCurrentValue();
//        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
//        sequenceMapper.updateByPrimaryKeySelective(sequenceDO);
//        String sequenceStr = String.valueOf(sequence);
//        for (int i = 0; i < 6-sequenceStr.length(); i++) {
//            stringBuilder.append(0);
//        }
//        stringBuilder.append(sequenceStr);
        return null;
    }
}
