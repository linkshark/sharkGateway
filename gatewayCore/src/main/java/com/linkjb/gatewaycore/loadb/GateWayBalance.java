package com.linkjb.gatewaycore.loadb;

import com.linkjb.gatewaycore.model.GatewayInfo;

import java.util.List;

;

public interface GateWayBalance {
    default GatewayInfo doSelect(List<GatewayInfo> gatewayInfoList){
        if(gatewayInfoList==null||gatewayInfoList.size() ==0){
            throw new IllegalArgumentException("不存在相关api可供hcsb-gateway负载!");
        }else{
            return gatewayInfoList.get(0);
        }
    }


}
