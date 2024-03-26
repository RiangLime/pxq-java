package cn.lime.pxqjava.tool;

import cn.lime.pxqjava.tool.bean.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BuyThread
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:07
 */
@Slf4j
public class BuyThread implements Runnable {


    boolean success = false;
    boolean targetEmpty = false;
    int allEmptyCnt = 0;

    private String ticketName;
    private Long startTime;
    private String showId;
    private String sessionId;
    private String userSeatId;
    private Integer buyCount;
    private String token;
    private List<Integer> audienceIndexes;

    private List<String> audienceIds = new ArrayList<>();

    public BuyThread(String name, Long startTime, String showId, String sessionId, String userSeatId, Integer buyCount,
                     String token, List<Integer> audienceIndexes) {
        this.ticketName = name;
        this.startTime = startTime;
        this.showId = showId;
        this.sessionId = sessionId;
        this.userSeatId = userSeatId;
        this.buyCount = buyCount;
        this.token = token;
        this.audienceIndexes = audienceIndexes;
    }

    public BuyThread(Long startTime, String showId, String sessionId, String userSeatId, Integer buyCount,
                     String token, List<Integer> audienceIndexes) {
        this.startTime = startTime;
        this.showId = showId;
        this.sessionId = sessionId;
        this.userSeatId = userSeatId;
        this.buyCount = buyCount;
        this.token = token;
        this.audienceIndexes = audienceIndexes;
    }

    @Override
    public void run() {
        List<AudienceInfo> audienceInfos = null;
        try {
            audienceInfos = RequestSender.getAudiences(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Integer audienceId : audienceIndexes) {
            audienceIds.add(audienceInfos.get(audienceId).getId());
        }

        while (true) {
            // 成功抢票
            if (success) {
                log.info("抢到票啦 债见");
                return;
            }
            if (allEmptyCnt > 5) {
                log.error("5次没票了  歇逼了");
                return;
            }
            log.info("{} is Running", ticketName);
            // 没到开始时间  继续
            if (ObjectUtils.isNotEmpty(startTime) && System.currentTimeMillis() < startTime - 1000) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            try {
                biz();
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void biz() throws Exception {

        // 获取session
//        List<SessionInfo> sessions = RequestSender.getSessions(showId);
//        for (SessionInfo session : sessions) {
//            if (session.getBizShowSessionId().equals(sessionId) && !"ON_SALE".equals(session.getSessionStatus())) {
//                throw new Exception("未获取到在售状态且符合购票数量需求的session_id");
//            }
//        }
        // 座位信息

        List<SeatCountInfo> seatCountInfos = RequestSender.getSeatCount(showId, sessionId);
        List<SeatPlanInfo> seatPlanInfos = RequestSender.getSeatPlans(showId, sessionId);
        List<SeatInfo> seatInfos = new ArrayList<>();
        for (SeatCountInfo seatCountInfo : seatCountInfos) {
            for (SeatPlanInfo seatPlanInfo : seatPlanInfos) {
                if (seatCountInfo.getSeatPlanId().equals(seatPlanInfo.getSeatPlanId())) {
                    seatInfos.add(new SeatInfo(seatCountInfo, seatPlanInfo));
                    break;
                }
            }
        }


        log.info(JSON.toJSONString(seatCountInfos));
        SeatInfo targetSeat = null;
        // 没抢完
        if (!targetEmpty) {
            for (SeatInfo seatInfo : seatInfos) {
                if (seatInfo.getCanBuyCount() >= buyCount && userSeatId.equals(seatInfo.getSeatPlanId())) {
                    targetSeat = seatInfo;
                    break;
                } else if (seatInfo.getCanBuyCount() < buyCount && userSeatId.equals(seatInfo.getSeatPlanId())) {
                    targetEmpty = true;
                    break;
                }
            }
        }
        // 抢完了抢别的
        else {
            for (SeatInfo seatInfo : seatInfos) {
                if (seatInfo.getCanBuyCount() >= buyCount) {
                    targetSeat = seatInfo;
                    break;
                }
            }
        }

        if (ObjectUtils.isEmpty(targetSeat)) {
            log.warn("没有符合条件的座位，将为你继续搜寻其他在售场次");
            allEmptyCnt++;
            return;
        }
        String deliverMethod = RequestSender.getDeliverMethod(showId, sessionId, targetSeat.getSeatPlanId(), targetSeat.getOriginalPrice(), buyCount, token);


        if (deliverMethod.equals("EXPRESS")) {
            AddressInfo addressInfo = RequestSender.getAddress(token);
            ExpressFeeInfo expressFeeInfo = RequestSender.getExpressFee(showId, sessionId, targetSeat.getSeatPlanId(), targetSeat.getOriginalPrice(),
                    targetSeat.getCanBuyCount(), addressInfo.getLocationId(), token);
            RequestSender.createOrder(showId, sessionId, targetSeat.getSeatPlanId(), targetSeat.getOriginalPrice(),
                    buyCount, deliverMethod, expressFeeInfo.getPriceItemVal(), addressInfo.getUsername(), addressInfo.getCellphone(),
                    addressInfo.getAddressId(), addressInfo.getDetailAddress(), addressInfo.getLocationId(), audienceIds, token);
        } else if (deliverMethod.equals("VENUE") || deliverMethod.equals("E_TICKET") || deliverMethod.equals("ID_CARD")) {
            RequestSender.createOrder(showId, sessionId, targetSeat.getSeatPlanId(), targetSeat.getOriginalPrice(),
                    buyCount, deliverMethod, 0, null, null, null, null,
                    null, audienceIds, token);
        } else {
            throw new IOException("不支持的deliver method");
        }
        success = true;

    }
}
