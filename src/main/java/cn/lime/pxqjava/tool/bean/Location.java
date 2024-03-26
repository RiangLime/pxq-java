package cn.lime.pxqjava.tool.bean;

import lombok.Data;

/**
 * @ClassName: Location
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 14:13
 */
@Data
public class Location {
    private String locationId;
    private String province;
    private String city;
    private String district;
    private boolean supportSameDayDelivery;
}
