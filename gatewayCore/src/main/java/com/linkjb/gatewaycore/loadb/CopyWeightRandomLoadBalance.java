package com.linkjb.gatewaycore.loadb;

import com.linkjb.gatewaycore.model.GatewayInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @ClassName CopyWeightRandomLoadBalance
 * @Description 加权随机 实际线上可能机器的性能各不相同，为了让性能更好的机器分配更多的流量，可以为每台机器设置一个权重，按照一定的比例进行任务的均分。
 * @Author shark
 * @Data 2023/7/13 14:47
 **/
public class CopyWeightRandomLoadBalance implements GateWayBalance {


    @Override
    public GatewayInfo doSelect(List<GatewayInfo> gatewayInfoList) {
        boolean averageWeight = true;
        int totalWeight = 0;
        if (!CollectionUtils.isEmpty(gatewayInfoList)) {
            for (int i = 0; i < gatewayInfoList.size(); i++) {
                GatewayInfo gatewayInfo = gatewayInfoList.get(i);
                if (averageWeight && i > 0 && gatewayInfo.getApiRangeWeight() != gatewayInfoList.get(i - 1).getApiRangeWeight()) {
                    averageWeight = false;
                }
                totalWeight += gatewayInfo.getApiRangeWeight();
            }
            if (averageWeight || totalWeight < 1) {
                return gatewayInfoList.get(0);
            }
            int index = new Random().nextInt(totalWeight);
            for(GatewayInfo gatewayInfo:gatewayInfoList){
                if (index < gatewayInfo.getApiRangeWeight()) {
                    return gatewayInfo;
                }
                index -= gatewayInfo.getApiRangeWeight();
            }
            return gatewayInfoList.get(index);
        } else {
            throw new IllegalArgumentException("不存在相关api可供hcsb-gateway负载!");
        }
    }
}
