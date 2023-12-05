package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Events;
import cn.shenmuyan.service.EventService;
import cn.shenmuyan.vo.EventWhereVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动controller
 * @className: EventController
 * @author: 叶宝谦
 * @date: 2023/12/05 9:16
 **/
@RestController
@RequestMapping("/event")
public class EventController {
    @Resource
    EventService eventService;
    @GetMapping
    public SaResult getAll(){
        return SaResult.ok();
    }
    @PostMapping("/getAll")
    public SaResult getAll(@Validated EventWhereVO eventWhereVO){
        List<Events> all = eventService.findAll(eventWhereVO);
        return SaResult.ok().setData(all);
    }
}
