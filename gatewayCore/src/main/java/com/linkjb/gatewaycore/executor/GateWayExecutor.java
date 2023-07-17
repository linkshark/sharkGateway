package com.linkjb.gatewaycore.executor;

import com.linkjb.gatewaycore.biz.GateWayAdminBiz;
import com.linkjb.gatewaycore.biz.client.GateWayAdminBizClient;
import com.linkjb.gatewaycore.log.GateWayFileAppender;
import com.linkjb.gatewaycore.server.NettyServer;
import com.linkjb.gatewaycore.thread.LogFileCleanThread;
import com.linkjb.gatewaycore.util.IpUtil;
import com.linkjb.gatewaycore.util.NetUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GateWayExecutor
 * @Description gateway-netty网关启动类
 * @Author shark
 * @Data 2023/5/10 17:25
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "网关netty注册器")
public class GateWayExecutor {

    private static final Logger logger = LoggerFactory.getLogger(GateWayExecutor.class);

    @ApiModelProperty(value = "主网关地址 可提供多个以,分割")
    private String gateWayAddresses;
    @ApiModelProperty(value = "token")
    private String accessToken;
    @ApiModelProperty(value = "应用名称如 hcsb普通应用")
    private String appName;
    @ApiModelProperty(value = "netty发布地址")
    private String address;
    @ApiModelProperty(value = "netty发布ip")
    private String ip;
    @ApiModelProperty(value = "netty发布端口")
    private int port;
    @ApiModelProperty(value = "日志存储地址-相对地址")
    private String logPath;
    @ApiModelProperty(value = "日志存储保存时间")
    private int logRetentionDays;
    public static List<GateWayAdminBiz> getAdminBizList(){
        return gateWayAdminBizList;
    }
    public void statr() throws Exception{
        //初始化日志目录
        GateWayFileAppender.initLogPath(logPath);
        //初始化shark-gateway网关 ，支持集群
        initGateWayBizList(gateWayAddresses, accessToken);
        // init JobLogFileCleanThread
        // 初始化日志清理守护线程，比如清理超过30天的日志
        LogFileCleanThread.getInstance().start(logRetentionDays);
        // init TriggerCallbackThread
        // 初始化回调调度平台的守护线程
        //不做回调

        // init executor-server
        //初始化执行器
        initNettyServer(address, ip, port, appName, accessToken);
    }

    private static List<GateWayAdminBiz> gateWayAdminBizList;
    private void initGateWayBizList(String gateWayAddresses, String accessToken) {
        if (gateWayAddresses!=null && gateWayAddresses.trim().length()>0) {
            for (String address: gateWayAddresses.trim().split(",")) {
                if (address!=null && address.trim().length()>0) {

                    GateWayAdminBiz adminBiz = new GateWayAdminBizClient(address.trim(), accessToken);

                    if (gateWayAdminBizList == null) {
                        gateWayAdminBizList = new ArrayList<GateWayAdminBiz>();
                    }
                    gateWayAdminBizList.add(adminBiz);
                }
            }
        }
    }

    NettyServer nettyServer = null;

    private void initNettyServer(String address, String ip, int port, String appname, String accessToken) throws Exception {

        // fill ip port
        port = port>0?port: NetUtil.findAvailablePort(9999);
        ip = (ip!=null&&ip.trim().length()>0)?ip: IpUtil.getIp();

        // generate address
        if (address==null || address.trim().length()==0) {
            String ip_port_address = IpUtil.getIpPort(ip, port);   // registry-address：default use address to registry , otherwise use ip:port if address is null
            address = "http://{ip_port}/".replace("{ip_port}", ip_port_address);
        }

        // accessToken
        if (accessToken==null || accessToken.trim().length()==0) {
            logger.warn(">>>>>>>>>>> shark-gateway accessToken is empty. To ensure system security, please set the accessToken.");
        }

        // start
        nettyServer = new NettyServer();
        nettyServer.start(address, port, appname, accessToken);
    }


//    public void destroy(){
//        // destroy executor-server
//        stopEmbedServer();
//
//        // destroy jobThreadRepository
//        if (jobThreadRepository.size() > 0) {
//            for (Map.Entry<Integer, JobThread> item: jobThreadRepository.entrySet()) {
//                JobThread oldJobThread = removeJobThread(item.getKey(), "web container destroy and kill the job.");
//                // wait for job thread push result to callback queue
//                if (oldJobThread != null) {
//                    try {
//                        oldJobThread.join();
//                    } catch (InterruptedException e) {
//                        logger.error(">>>>>>>>>>> shark-gateway, JobThread destroy(join) error, jobId:{}", item.getKey(), e);
//                    }
//                }
//            }
//            jobThreadRepository.clear();
//        }
//        jobHandlerRepository.clear();
//
//
//        // destroy JobLogFileCleanThread
//        JobLogFileCleanThread.getInstance().toStop();
//
//        // destroy TriggerCallbackThread
//        TriggerCallbackThread.getInstance().toStop();
//
//    }

}
