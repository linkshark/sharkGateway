package com.linkjb.gatewaycore.loadb;

import com.linkjb.gatewaycore.model.GatewayInfo;

import java.util.List;
import java.util.Random;

/**
 * @ClassName SimpleRandomLoadBalance
 * @Description 完全随机, 在调用次数比较多的时候会相对平均分布到每台机器上。
 * @Author shark
 * @Data 2023/7/13 14:41
 **/
public class SimpleRandomLoadBalance implements GateWayBalance {

    @Override
    public GatewayInfo doSelect(List<GatewayInfo> gatewayInfoList) {
        if(gatewayInfoList==null||gatewayInfoList.size() ==0){
            throw new IllegalArgumentException("不存在相关api可供hcsb-gateway负载!");
        }

        return gatewayInfoList.get(new Random().nextInt(gatewayInfoList.size()));
    }
}
