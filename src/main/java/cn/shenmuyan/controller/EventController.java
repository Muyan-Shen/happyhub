package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Events;
import cn.shenmuyan.service.EventService;
import cn.shenmuyan.service.SeatService;
import cn.shenmuyan.vo.EventInsertVO;
import cn.shenmuyan.vo.EventWhereVO;
import cn.shenmuyan.vo.SeatInsertVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    private EventService eventService;
    @Resource
    private SeatService seatService;
    @GetMapping("getAll")
    public SaResult getAll(@RequestParam("keyword") String keyword) {
        EventWhereVO vo = new EventWhereVO();
        vo.setOrganizer(keyword);
        vo.setDescription(keyword);
        vo.setTitle(keyword);
        vo.setLocation(keyword);
        List<Events> all = eventService.findAll(vo);
        return SaResult.ok().setData(all);
    }
    @PostMapping("/getAll")
    public SaResult getAll(@Validated EventWhereVO eventWhereVO){
        List<Events> all = eventService.findAll(eventWhereVO);
        return SaResult.ok().setData(all);
    }

    /**
     * 获取活动详情
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
        BigDecimal[] gearPrices = seatService.getGearPrices(eventId);
        return SaResult.ok().setData(event).set("gearPrices",gearPrices);
    }

    /**
     * 创建活动
     * @param event
     * @return
     */
    @PostMapping("/create")
    public SaResult create(@Validated @RequestBody EventInsertVO event) {
        eventService.addEvent(event);
        return SaResult.ok("创建成功");
    }

    /**
     * 生成座位
     * @param vo
     * @return
     */
    @PostMapping("/generateSeats")
    public SaResult generateSeat(@Validated @RequestBody SeatInsertVO vo){
        String[] directions = vo.getDirection();
        for (String direction : directions) {
            seatService.setSeat(vo.getEventId(),vo.getTopGear(),direction,vo.getMaxCol(),vo.getGearSum(), vo.getGearPrice());
        }
        return SaResult.ok("创建成功");
    }

    /**
     * 获得剩下的座位信息
     * @param eventId   活动id
     * @return
     */
    @GetMapping ("/getAllSeatsInfo")
    public SaResult getAllSeats(@NotNull Integer eventId){
        Events event = eventService.findById(eventId);
        Integer topGear = event.getTopGear();
        BigDecimal[] gearPrices = seatService.getGearPrices(eventId);
        Integer[] gearSeatNum = new Integer[topGear];
        for (int i = 0; i < gearSeatNum.length; i++) {
            Integer num = seatService.getLastSeatNum(eventId, i + 1, 1);
            gearSeatNum[i] = num;
        }
        return SaResult.ok().set("topGear",topGear).set("gearPrices",gearPrices).set("gearSeatNum",gearSeatNum);
    }
}
