package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Events;
import cn.shenmuyan.service.EventService;
import cn.shenmuyan.service.SeatService;
import cn.shenmuyan.vo.EventInsertVO;
import cn.shenmuyan.vo.EventWhereVO;
import cn.shenmuyan.vo.SeatInsertVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private SeatService seatService;

    @GetMapping("getCity")
    public SaResult getCity(){
        String[] city = eventService.getCity();
        return SaResult.ok().setData(city);
    }

    @GetMapping("getAll")
    public SaResult getAll(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                           @RequestParam(defaultValue = "1", required = false) int pageNum,
                           @RequestParam(defaultValue = "16", required = false) int limit) {
        EventWhereVO vo = new EventWhereVO();
        if (!keyword.isEmpty()) {
            vo.setOrganizer(keyword);
            vo.setDescription(keyword);
            vo.setTitle(keyword);
            vo.setLocation(keyword);
        }
        List<Events> all = eventService.findAll(vo);
        //设置分页
        PageHelper.startPage(pageNum,limit);
        PageInfo<Events> events = new PageInfo<>(all);
        return SaResult.ok().setData(events);
    }

    @PostMapping("/getAll")
    public SaResult getAll(@Validated EventWhereVO eventWhereVO) {
        List<Events> all = eventService.findAll(eventWhereVO);
        return SaResult.ok().setData(all);
    }

    /**
     * 获取活动详情
     *
     * @param eventId 活动id
     * @return
     */
    @GetMapping("/{eventId}")
    public SaResult getEventById(@PathVariable Integer eventId) {
        System.out.println(eventId);
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
        List<Integer> index=new ArrayList<>();
        for (int i = 1; i <=gearPrices.length; i++) {
            index.add(i);
        }
        System.out.println(index);
        return SaResult.ok().setData(event).set("gearPrices",gearPrices).set("index",index);
    }

    /**
     * 创建活动
     *
     * @param event
     * @return
     */
    @PostMapping("/create")
    public SaResult create(@Validated @RequestBody EventInsertVO event) {
        EventWhereVO whereVO = new EventWhereVO();
        whereVO.setTitle(event.getTitle());
        if (eventService.findAll(whereVO).size() > 0){
            return SaResult.error("活动标题重复");
        }
        Integer eventId = eventService.addEvent(event);
        return SaResult.ok("创建成功").set("eventId",eventId);
    }

    /**
     * 生成座位
     *
     * @param vo
     * @return
     */
    @PostMapping("/generateSeats")
    public SaResult generateSeat(@Validated @RequestBody SeatInsertVO vo) {
        String[] directions = vo.getDirection();
        for (String direction : directions) {
            System.out.println(1);
            seatService.setSeat(vo.getEventId(), vo.getTopGear(), direction, vo.getMaxCol(), vo.getGearSum(), vo.getGearPrice());
        }
        return SaResult.ok("创建成功");
    }

    /**
     * 获得剩下的座位信息
     *
     * @param eventId 活动id
     * @return
     */
    @GetMapping("/getAllSeatsInfo")
    public SaResult getAllSeats(@NotNull Integer eventId) {
        Events event = eventService.findById(eventId);
        Integer topGear = event.getTopGear();
        BigDecimal[] gearPrices = seatService.getGearPrices(eventId);
        Integer[] gearSeatNum = new Integer[topGear];
        for (int i = 0; i < gearSeatNum.length; i++) {
            Integer num = seatService.getLastSeatNum(eventId, i + 1, 1);
            gearSeatNum[i] = num;
        }
        return SaResult.ok().set("topGear", topGear).set("gearPrices", gearPrices).set("gearSeatNum", gearSeatNum);
    }
}
