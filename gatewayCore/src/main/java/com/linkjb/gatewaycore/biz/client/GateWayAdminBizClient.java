package com.linkjb.gatewaycore.biz.client;

import com.linkjb.gatewaycore.biz.GateWayAdminBiz;
import com.linkjb.gatewaycore.biz.model.HandleCallbackParam;
import com.linkjb.gatewaycore.biz.model.RegistryParam;
import com.linkjb.gatewaycore.biz.model.ReturnT;
import com.linkjb.gatewaycore.util.RemotingUtil;


import java.util.List;

/**
 * admin api test
 *
 * @author shark
 */
public class GateWayAdminBizClient implements GateWayAdminBiz {

    public GateWayAdminBizClient() {
    }
    public GateWayAdminBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl ;
    private String accessToken;
    private int timeout = 3;


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return RemotingUtil.postBody(addressUrl+"api/callback", accessToken, timeout, callbackParamList, String.class);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return RemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryParam, String.class);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return RemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryParam, String.class);
    }

}
