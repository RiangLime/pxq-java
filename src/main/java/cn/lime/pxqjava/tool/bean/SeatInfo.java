package cn.lime.pxqjava.tool.bean;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName: SeatInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:37
 */
@Data
public class SeatInfo {
    private String showSessionId;
    private String seatPlanId;
    private String colorValue;
    private double originalPrice;
    private String seatPlanName;
    private String stdSeatPlanId;
    private boolean isCombo;
    private String seatPlanCategory;
    private List<String> saleTags;
    private boolean planHot;
    private List<Object> items;

    private boolean hasActivity;
    private int canBuyCount;

    public SeatInfo(SeatCountInfo countInfo,SeatPlanInfo planInfo) {
        BeanUtils.copyProperties(countInfo,this);
        BeanUtils.copyProperties(planInfo,this);
    }
}
