package cn.lime.pxqjava.tool;

import cn.lime.pxqjava.tool.dto.BizDto;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/ticket/start")
    public String startTicket(@RequestBody BizDto dto){
        service.deal(dto);
        return "success";
    }

}
