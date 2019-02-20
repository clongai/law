package com.law.idworker;

import com.law.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class IdWorker {

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    public IdWorker(){

    }

    public String nextId() {
        long workerId=1;
        long datacenterId=1;

        snowflakeIdWorker.setWorkerId(workerId);
        snowflakeIdWorker.setDatacenterId(datacenterId);

        snowflakeIdWorker.nextId();
        String date = DateUtil.formatDate(DateUtil.EN_FORMAT_F,new Date());
        String id = String.format("%s%d", date, snowflakeIdWorker.nextId());
        return id;
    }

}
