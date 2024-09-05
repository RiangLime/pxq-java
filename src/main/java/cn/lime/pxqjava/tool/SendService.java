package cn.lime.pxqjava.tool;

import cn.lime.pxqjava.tool.dto.BizDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: SendService
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 12:07
 */
@Service
public class SendService {

    // 创建固定大小的线程池:
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public void deal(BizDto dto) throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            executor.submit(new BuyThread(dto.getName(),dto.getStartTime(),dto.getShowId(),dto.getSessionId(),
                    dto.getUserSeatId(),dto.getUserSeatPrice(),dto.getBuyCount(),dto.getToken(),dto.getAudienceIndexes()));
            Thread.sleep(500);
        }
    }

}
