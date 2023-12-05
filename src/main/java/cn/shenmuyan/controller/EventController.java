package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动controller
 * @className: EventController
 * @author: 叶宝谦
 * @date: 2023/12/05 9:16
 **/
@RestController
@RequestMapping("/event")
public class EventController {
    @GetMapping
    public SaResult getAll(){
        return SaResult.ok();
    }
}
