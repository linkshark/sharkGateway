package com.linkjb.gatewaycore.biz;

import com.linkjb.gatewaycore.biz.model.*;


/**
 * shark
 */
public interface GateWayBiz {

    /**
     * beat
     * @return
     */
    public ReturnT<String> beat();

    /**
     * idle beat
     *
     * @param idleBeatParam
     * @return
     */
    public ReturnT<String> idleBeat(IdleBeatParam idleBeatParam);

    /**
     * run
     * @param triggerParam
     * @return
     */
    public ReturnT<String> run(TriggerParam triggerParam);

    /**
     * kill
     * @param killParam
     * @return
     */
    public ReturnT<String> kill(KillParam killParam);

    /**
     * log
     * @param logParam
     * @return
     */
    public ReturnT<LogResult> log(LogParam logParam);

}
