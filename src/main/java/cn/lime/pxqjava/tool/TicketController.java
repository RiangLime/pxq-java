package cn.lime.pxqjava.tool;

import cn.lime.pxqjava.tool.dto.BizDto;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TicketController
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 15:18
 */
@RestController
public class TicketController {

    @Resource
    private SendService service;

    @PostMapping("/ticket/start/post")
    public String startTicketPost(@RequestBody BizDto dto){
        service.deal(dto);
        return "success";
    }

    @GetMapping("/ticket/start/get")
    public String startTicketGet(@RequestParam("name") String name,
                                 @RequestParam(value = "startTime",required = false) String startTime,
                                 @RequestParam("showId") String showId,
                                 @RequestParam("sessionId") String sessionId,
                                 @RequestParam("userSeatId") String userSeatId,
                                 @RequestParam("buyCount") String buyCount,
                                 @RequestParam("token") String token,
                                 @RequestParam("ids") String ids){
        BizDto dto = new BizDto();
        dto.setName(name);
        if (StringUtils.isNotEmpty(startTime)){
            dto.setStartTime(Long.valueOf(startTime));
        }
        dto.setShowId(showId);
        dto.setSessionId(sessionId);
        dto.setUserSeatId(userSeatId);
        dto.setBuyCount(Integer.valueOf(buyCount));
        dto.setToken(token);
        List<Integer> audiencesIndexes = new ArrayList<>();
        for (String s : ids.split(",")) {
            audiencesIndexes.add(Integer.valueOf(s));
        }
        dto.setAudienceIndexes(audiencesIndexes);

        service.deal(dto);
        return "success";
    }

}
