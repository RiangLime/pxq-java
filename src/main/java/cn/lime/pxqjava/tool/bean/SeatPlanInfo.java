package cn.lime.pxqjava.tool.bean;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: SeatPlanInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:34
 */
@Data
public class SeatPlanInfo {
    private String showSessionId;
    private String seatPlanId;
    private String colorValue;
    private double originalPrice;
    private String seatPlanName;
    private String stdSeatPlanId;
    private boolean isCombo;
    private boolean hasActivity;
    private String seatPlanCategory;
    private List<String> saleTags;
    private boolean planHot;
    private List<Object> items;
}
