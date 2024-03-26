package cn.lime.pxqjava.tool.bean;

import lombok.Data;

/**
 * @ClassName: AddressInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 14:12
 */
@Data
public class AddressInfo {
    private String addressId;
    private String username;
    private String cellphone;
    private String locationId;
    private String detailAddress;
    private boolean isDefault;
    private Location location;
}
