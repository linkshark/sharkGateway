package com.linkjb.gatewaycore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName ColDTO
 * @Description TODO
 * @Author shark
 * @Data 2023/6/1 11:34
 **/
@Data
@ApiModel("字段")
public class ColDTO {
    @ApiModelProperty("字段名")
    private String colName;
    @ApiModelProperty("字段类型")
    private String colType;
    @ApiModelProperty("字段别名")
    private String colAlias;
    @ApiModelProperty("字段的条件")
    private String queryCondition;

}
