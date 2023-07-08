package com.linkjb.gatewaycore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SqlDTO
 * @Description TODO
 * @Author shark
 * @Data 2023/6/1 10:14
 **/
@Data
@ApiModel("sql类")
public class SqlDTO {

    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("表别名")
    private String tableAlias;
    @ApiModelProperty("查询的字段 list为空代表查询*")
    private List<ColDTO> cols;
    @ApiModelProperty("使用where的标志位")
    private Boolean whereFlag;
    @ApiModelProperty("前置表")
    private SqlDTO preSqlDTO;
    @ApiModelProperty("后置表")
    private SqlDTO afterSqlDTO;



}
