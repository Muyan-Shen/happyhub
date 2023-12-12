package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.shenmuyan.bean.Permission;
import cn.shenmuyan.service.PermissionService;
import cn.shenmuyan.vo.PermissionInsertVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限Controller
 *
 * @Title: PermissionController
 * @Author ShenMuyan
 * @Package cn.shenmuyan.controller
 * @Date 2023/12/5 21:00
 */

@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询
     * @param name
     * @param code
     * @param page
     * @param limit
     * @return
     */
    @GetMapping
    @SaCheckPermission(value =  "permission:list",orRole = "admin")
    public SaResult list(String name,
                         String code,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page,limit);
        List<Permission> list = permissionService.findAll(name, code);
        PageInfo<Permission> pageInfo = new PageInfo<>(list);
        return SaResult.ok()
                .setData(pageInfo.getList())
                .set("count",pageInfo.getTotal());
    }

    @PutMapping
    @SaCheckPermission(value = "permission:add",orRole = "admin")
    public SaResult add(@RequestBody @Validated PermissionInsertVO vo){
        permissionService.add(BeanUtil.copyProperties(vo,Permission.class));
        return SaResult.ok();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @SaCheckPermission(value = "permission:delete",orRole = "admin")
    @DeleteMapping
    public SaResult deleteByIds(Integer ... ids){
        permissionService.deleteById(ids);
        return SaResult.ok();
    }

    @GetMapping("/{id}")
    @SaCheckPermission(value =  "permission:get",orRole = "admin")
    public SaResult findById(Integer id){
        Permission byId = permissionService.findById(id);
        return SaResult.ok().setData(byId);
    }
    @PostMapping("/{id}")
    @SaCheckPermission(value = "permission:update",orRole = "admin")
    public SaResult updateById(@PathVariable Integer id,Permission permission){
        permission.setId(id);
        permissionService.updateById(permission);
        return SaResult.ok();
    }
}