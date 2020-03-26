package com.ityb.qugou.websocket.demo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableScheduling
public class WebSocketDemo {
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	@RequestMapping("/marchant/demo/websocket")
    public String index() {
        return "websocket-demo";
    }

	/**************************↓↓↓↓以下两种发送都可以发送消息****************************/
	
    /**
     * 方式一
     * 接受前端发送过来的数据
     * @author yangbin
     * @date 2018年2月3日
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/send")
    @SendTo("/topic/send")
    public SocketMessage send(SocketMessage message) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.date = df.format(new Date());
        System.out.println("send.."+message.date);
        return message;
    }

    /**
     * 方式二
     * 执行推动消息给后台
     * @author yangbin
     * @date 2018年2月3日
     * @return
     * @throws Exception
     */
    /*@Scheduled(fixedRate = 1000)*/
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //调用自身
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        System.out.println("callback..."+df.format(new Date()));
        return "callback";
    }
}