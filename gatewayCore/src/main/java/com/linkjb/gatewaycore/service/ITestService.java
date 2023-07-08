package com.linkjb.gatewaycore.service;

import com.linkjb.gatewaycore.dto.SqlDTO;

import java.util.List;

public interface ITestService {
    String generateSql(List<SqlDTO> sqlDTOS);
}
