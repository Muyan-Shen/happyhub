package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.shenmuyan.bean.Role;
import cn.shenmuyan.service.RoleService;
import cn.shenmuyan.vo.RoleInsertVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色Controller
 *
 * @Title: RoleController
 * @Author ShenMuyan
 * @Package cn.shenmuyan.controller
 * @Date 2023/12/5 21:01
 */

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 查询
     *
     * @param name
     * @param code
     * @param page
     * @param limit
     * @return
     */
    @GetMapping
    @SaCheckPermission(value = "role:list", orRole = "admin")
    public SaResult list(String name,
                         String code,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<Role> list = roleService.findAll(name, code);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return SaResult.ok()
                .setData(pageInfo.getList())
                .set("count", pageInfo.getTotal());
    }

    @PutMapping
    @SaCheckPermission(value = "role::add", orRole = "admin")
    public SaResult add(@RequestBody @Validated RoleInsertVO vo) {
        if(!roleService.add(vo)){
            return SaResult.error().setMsg("新增失败");
        }
        return SaResult.ok().setMsg("success");
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission(value = "role::delete", orRole = "admin")
    @DeleteMapping
    public SaResult deleteByIds(Integer... ids) {
        roleService.deleteById(ids);
        return SaResult.ok();
    }

    @GetMapping("/{id}")
    @SaCheckPermission(value = "role::get", orRole = "admin")
    public SaResult findById(Integer id) {
        Role byId = roleService.findById(id);
        return SaResult.ok().setData(byId);
    }

    @PostMapping("/{id}")
    @SaCheckPermission(value = "role::update", orRole = "admin")
    public SaResult updateById(@PathVariable Integer id, Role role) {
        role.setId(id);
        roleService.updateById(role);
        return SaResult.ok();
    }

    //关联权限
    @PostMapping("/{roleId}/permission")
    @SaCheckPermission(value = "role::permission", orRole = "admin")
    public SaResult permission(@PathVariable Integer roleId,
                               @RequestParam Integer[] permissionIds) {
        roleService.associationPermission(roleId, permissionIds);
        return SaResult.ok();
    }
}
