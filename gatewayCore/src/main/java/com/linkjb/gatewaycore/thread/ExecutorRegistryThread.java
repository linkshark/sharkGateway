package com.linkjb.gatewaycore.thread;


import com.linkjb.gatewaycore.biz.GateWayAdminBiz;
import com.linkjb.gatewaycore.biz.model.RegistryParam;
import com.linkjb.gatewaycore.biz.model.ReturnT;
import com.linkjb.gatewaycore.enums.RegistryConfig;
import com.linkjb.gatewaycore.executor.GateWayExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * shark
 */
public class ExecutorRegistryThread {
    private static Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

    private static ExecutorRegistryThread instance = new ExecutorRegistryThread();
    public static ExecutorRegistryThread getInstance(){
        return instance;
    }

    private Thread registryThread;
    private volatile boolean toStop = false;
    public void start(final String appname, final String address){

        // valid
        if (appname==null || appname.trim().length()==0) {
            logger.warn(">>>>>>>>>>> shark-gateway, executor registry config fail, appname is null.");
            return;
        }
        if (GateWayExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>> shark-gateway, executor registry config fail, adminAddresses is null.");
            return;
        }

        registryThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // registry
                while (!toStop) {
                    try {
                        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appname, address);
                        for (GateWayAdminBiz adminBiz: GateWayExecutor.getAdminBizList()) {
                            try {
                                ReturnT<String> registryResult = adminBiz.registry(registryParam);
                                if (registryResult!=null && ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                                    registryResult = ReturnT.SUCCESS;
                                    logger.debug(">>>>>>>>>>> shark-gateway registry success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                    break;
                                } else {
                                    logger.info(">>>>>>>>>>> shark-gateway registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                }
                            } catch (Exception e) {
                                logger.info(">>>>>>>>>>> shark-gateway registry error, registryParam:{}", registryParam, e);
                            }

                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }

                    }

                    try {
                        if (!toStop) {
                            TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                        }
                    } catch (InterruptedException e) {
                        if (!toStop) {
                            logger.warn(">>>>>>>>>>> shark-gateway, executor registry thread interrupted, error msg:{}", e.getMessage());
                        }
                    }
                }

                // registry remove
                try {
                    RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appname, address);
                    for (GateWayAdminBiz adminBiz: GateWayExecutor.getAdminBizList()) {
                        try {
                            ReturnT<String> registryResult = adminBiz.registryRemove(registryParam);
                            if (registryResult!=null && ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                                registryResult = ReturnT.SUCCESS;
                                logger.info(">>>>>>>>>>> shark-gateway registry-remove success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                                break;
                            } else {
                                logger.info(">>>>>>>>>>> shark-gateway registry-remove fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                            }
                        } catch (Exception e) {
                            if (!toStop) {
                                logger.info(">>>>>>>>>>> shark-gateway registry-remove error, registryParam:{}", registryParam, e);
                            }

                        }

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
                logger.info(">>>>>>>>>>> shark-gateway, executor registry thread destroy.");

            }
        });
        registryThread.setDaemon(true);
        registryThread.setName("xxl-job, executor ExecutorRegistryThread");
        registryThread.start();
    }

    public void toStop() {
        toStop = true;

        // interrupt and wait
        if (registryThread != null) {
            registryThread.interrupt();
            try {
                registryThread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

}
