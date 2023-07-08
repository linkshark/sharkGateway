package com.linkjb.gatewaycore.web;

import com.linkjb.gatewaycore.dto.SqlDTO;
import com.linkjb.gatewaycore.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TestController
 * @Description
 * @Author shark
 * @Data 2023/6/1 10:09
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Resource
    ITestService testService;
    @PostMapping("generateSql")
    public String generateSql(@RequestBody List<SqlDTO> sqlDTOS){
        String result = testService.generateSql(sqlDTOS);
        return result;
    }
}
