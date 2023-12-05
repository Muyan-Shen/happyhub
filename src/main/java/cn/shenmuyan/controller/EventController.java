package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.db.sql.Order;
import cn.shenmuyan.bean.Events;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.service.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 活动controller
 *
 * @className: EventController
 * @author: 叶宝谦
 * @date: 2023/12/05 9:16
 **/
@RestController
@RequestMapping("/event")
public class EventController {
    @Resource
    private EventService eventService;

    @GetMapping
    public SaResult getAll() {
        return SaResult.ok();
    }

    /**
     * 获取活动详情（档位未显示）
     *
     * @param eventId 活动id
     * @return
     */
    @GetMapping("/eventId")
    public SaResult getEventById(Integer eventId) {
        Events event = eventService.findById(eventId);
        if (event == null) {
            return SaResult.error("获取失败");
        }
        String organizerUsername = eventService.findOrganizerUsername(event.getOrganizerId());
        if (organizerUsername == null) {
            return SaResult.error("没有该活动组织者");
        }
        event.setOrganizerUsername(organizerUsername);
        return SaResult.ok().setData(event);
    }


}
