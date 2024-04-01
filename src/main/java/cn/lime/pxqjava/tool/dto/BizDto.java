package cn.lime.pxqjava.tool.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BizDto
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 15:06
 */
@Data
public class BizDto implements Serializable {
    private String name;
    private Long startTime;
    private String showId;
    private String sessionId;
    private String userSeatId;
    private Double userSeatPrice;
    private Integer buyCount;
    private String token;
    private List<Integer> audienceIndexes;
}
