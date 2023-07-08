package com.linkjb.gatewaycore.biz;

import com.linkjb.gatewaycore.biz.model.HandleCallbackParam;
import com.linkjb.gatewaycore.biz.model.RegistryParam;
import com.linkjb.gatewaycore.biz.model.ReturnT;

import java.util.List;

public interface GateWayAdminBiz {


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackParamList
     * @return
     */
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    public ReturnT<String> registry(RegistryParam registryParam);

    /**
     * registry remove
     *
     * @param registryParam
     * @return
     */
    public ReturnT<String> registryRemove(RegistryParam registryParam);


    // ---------------------- biz (custome) ----------------------
    // group、job ... manage

}
