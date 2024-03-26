package cn.lime.pxqjava.tool.bean;

import lombok.Data;

/**
 * @ClassName: SessionInfo
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:10
 */
@Data
public class SessionInfo {
    private String bizShowSessionId;
    private String sessionStatus;
    private boolean hasSessionSoldOut;
    private boolean ctSession;
    private boolean uploadPhoto;
    private boolean supportSeatPicking;
}
