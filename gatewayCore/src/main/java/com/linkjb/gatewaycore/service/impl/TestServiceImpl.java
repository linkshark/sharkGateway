package com.linkjb.gatewaycore.service.impl;

import cn.hutool.core.util.StrUtil;
import com.linkjb.gatewaycore.dto.ColDTO;
import com.linkjb.gatewaycore.dto.SqlDTO;
import com.linkjb.gatewaycore.service.ITestService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author shark
 * @Data 2023/6/1 15:12
 **/
@Service
@Slf4j
public class TestServiceImpl implements ITestService {
    @Override
    public String generateSql(List<SqlDTO> sqlDTOS) {
//        StringBuilder selectBuilder = new StringBuilder("select ");
//        Boolean leftJoinFlag = false;
//        sqlDTOS.forEach(s ->{
//            String tableAlias = s.getTableAlias();
//            String tableName = s.getTableName();
//            List<ColDTO> cols = s.getCols();
//            cols.forEach(col ->{
//                selectBuilder.append(tableAlias+"."+col.getColName()+ (StrUtil.isEmpty(col.getColAlias())?"":"as "+col.getColAlias()));
//            });
//        });
        return null;
    }
}
