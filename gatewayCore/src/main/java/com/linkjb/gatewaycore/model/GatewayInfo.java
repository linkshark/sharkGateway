package com.linkjb.gatewaycore.model;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName GatewayInfo
 * @Description api表
 * @Author shark
 * @Data 2023/7/11 15:23
 **/
@Data
@ApiModel("api")
@Accessors(chain = true)
@TableName("hcsb_gateway_info")
public class GatewayInfo {
    @ApiModelProperty("自增主键")
    @TableId(value = "increment_id", type = IdType.AUTO)
    private Integer incrementId;
    @ApiModelProperty("唯一id")
    @TableField(value = "api_business_id")
    private String apiBusinessId;
    @ApiModelProperty("同一个api的唯一标识,可存在多个相同api_id的api作为负载均衡的base")
    @TableField(value = "api_id")
    private String apiId;
    @ApiModelProperty("api名称")
    @TableField(value = "api_name")
    private String apiName;
    @ApiModelProperty("api的host(请求地址例如 http://10.x.x.x.x/request)")
    @TableField(value = "api_host")
    private String apiHost;
    @ApiModelProperty("1 get 2 post 3 put 4 delete ")
    @TableField(value = "api_request_method")
    private Integer apiRequestMethod;
    @ApiModelProperty("1 json 2 xml 3 webservice")
    @TableField(value = "api_request_type")
    private Integer apiRequestType;
    @ApiModelProperty("api的权重")
    @TableField(value = "api_range_weight")
    private Integer apiRangeWeight;
    @ApiModelProperty("负载均衡策略businessid")
    @TableField(value = "load_balance_method_business_id")
    private String loadBalanceMethodBusinessId;
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

}
