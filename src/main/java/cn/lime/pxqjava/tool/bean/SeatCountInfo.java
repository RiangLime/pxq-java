package cn.lime.pxqjava.tool.bean;

import lombok.Data;

/**
 * @ClassName: SeatCountInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:28
 */
@Data
public class SeatCountInfo {
    private String seatPlanId;
    private boolean hasActivity;
    private int canBuyCount;
}
