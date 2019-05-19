package com.ssaw.ssawstreamproducer.controller;

import com.ssaw.ssawstreamproducer.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * @date 2019/02/18
 */
@RestController
public class ProducerController {

    private final SendService sendService;

    @Autowired
    public ProducerController(SendService sendService) {
        this.sendService = sendService;
    }

    @GetMapping("/send/{msg}")
    public void send(@PathVariable(name = "msg") String msg) {
        sendService.sendMsg(msg);
    }
}
