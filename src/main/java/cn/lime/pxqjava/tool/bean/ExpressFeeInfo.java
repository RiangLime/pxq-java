package cn.lime.pxqjava.tool.bean;

import lombok.Data;

/**
 * @ClassName: ExpressFeeInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 14:14
 */
@Data
public class ExpressFeeInfo {
    private String priceItemId;
    private String priceItemType;
    private String priceItemName;
    private double priceItemVal;
    private String direction;
    private String priceItemSpecies;
    private ExpressExtractInfo expressExtraInfo;
}
