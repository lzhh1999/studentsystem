package com.lhb.studentsystem.controller;

import com.github.pagehelper.PageInfo;
import com.lhb.studentsystem.dto.AdminUserDTO;
import com.lhb.studentsystem.mapper.AdminMapper;
import com.lhb.studentsystem.model.*;
import com.lhb.studentsystem.result.LayuiResponse;
import com.lhb.studentsystem.result.ResponseResult;
import com.lhb.studentsystem.service.AdminService;
import com.lhb.studentsystem.service.HomeWorkService;
import com.lhb.studentsystem.service.RoleService;
import com.lhb.studentsystem.service.UserWorkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    HomeWorkService homeWorkService;
    @Autowired
    UserWorkService userWorkService;

    //查询所有学生
    @GetMapping("/list")
    public LayuiResponse getList(int page, int limit) {
        List<AdminUser> list = adminService.findAll(page, limit);

        List<AdminUserDTO> result = new ArrayList<>();
        for (AdminUser adminUser : list) {
            AdminUserDTO adminUserDTO = new AdminUserDTO();
            Integer roleId = adminUser.getRoleId();
            Role role = roleService.findById(roleId);
            String name = role.getName();
            BeanUtils.copyProperties(adminUser, adminUserDTO);
            adminUserDTO.setName(name);
            result.add(adminUserDTO);
        }
        PageInfo pageInfo = new PageInfo(result);
        LayuiResponse response = new LayuiResponse();
        response.setData(pageInfo.getList());
        response.setCount(pageInfo.getTotal());

        return response;
    }

    //查询所有角色
    @GetMapping("/roleList")
    public LayuiResponse getRoleList(int page, int limit) {
        List<Role> allRole = roleService.findAllRole(page, limit);
        LayuiResponse response = new LayuiResponse();
        PageInfo pageInfo = new PageInfo(allRole);
        response.setData(pageInfo.getList());
        response.setCount(pageInfo.getTotal());

        return response;
    }

    //查询所有作业
    @GetMapping("/homeworkList")
    public LayuiResponse getHomeWorkList(int page, int limit) {
        List<HomeWork> allHomeWork = homeWorkService.findAllHomeWork(page, limit);
        LayuiResponse response = new LayuiResponse();
        PageInfo pageInfo = new PageInfo(allHomeWork);
        response.setData(pageInfo.getList());
        response.setCount(pageInfo.getTotal());

        return response;
    }

    //管理员登录
    @PostMapping("/login")
    public ResponseResult login(String username, String password, HttpServletRequest request) {

        Admin result = adminMapper.findByUsernameAndPassword(username, password);

        if (result != null) {
            request.getSession().setAttribute("user", result.getName());
            request.getSession().setAttribute("username", result.getUsername());
            return ResponseResult.Success(200, "登录成功", result);
        } else {
            return ResponseResult.Error(500, "用户名或密码错误", null);
        }
    }

    //管理员退出登录
    @PostMapping("/logOut")
    public ResponseResult logOut(HttpServletRequest request) {
        //1.销毁session
        request.getSession().invalidate();
        return ResponseResult.Success(200, "退出成功", null);
    }

    //修改密码
    @PostMapping("/updatePassword")
    public ResponseResult updatePassword(String oldPassword, String repassword, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Admin admin = adminMapper.findByPassword(oldPassword);
        if (admin != null) {
            Integer result = adminMapper.updatePassword(repassword, username);
            if (result != 0) {
                return ResponseResult.Success(200, "修改成功", result);
            } else {
                return ResponseResult.Error(500, "修改失败", null);
            }
        }
        return ResponseResult.Error(500, "当前密码不正确", null);
    }

    //删除多个用户
    @PostMapping("/userDelete")
    public ResponseResult userDelete(@RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            int temp = Integer.parseInt(id);
            try {
                Integer userWorkByUserId = userWorkService.findUserWorkByUserId(temp);
                if (userWorkByUserId != 0) {
                    return new ResponseResult(500, "还有作业属于该学生，请先删除属于该学生的作业", null);
                } else {
                    adminService.deleteUser(temp);
                }
            } catch (Exception e) {
                adminService.deleteUser(temp);
            }
        }
        return new ResponseResult(200, "删除成功", null);
    }

    //删除一个用户
    @PostMapping("/userDeleteOne")
    public ResponseResult userDeleteOne(@RequestParam(value = "id") Integer id) {
        try {
            Integer userWorkByUserId = userWorkService.findUserWorkByUserId(id);
            System.out.println(userWorkByUserId);
            if (userWorkByUserId != 0) {
                return new ResponseResult(500, "还有作业属于该学生，请先删除属于该学生的作业", null);
            } else {
                adminService.deleteUser(id);
            }
        } catch (Exception e) {
            adminService.deleteUser(id);
        }
        return new ResponseResult(200, "删除成功", null);
    }

    //获取session中name的值
    @GetMapping("/getName")
    public ResponseResult getName(HttpSession session) {
        String user = (String) session.getAttribute("user");
        if(user!=null) {
            return new ResponseResult(200, "成功", user);
        }else {
            return new ResponseResult(400, "请先登录", null);
        }
    }

    //把user的id传进session里
    @GetMapping("/user_update")
    public ResponseResult UserUpdate(HttpServletRequest request, Integer id) {
        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        return new ResponseResult(200, "成功", null);
    }

    //把role的id传进session里
    @GetMapping("/role_update")
    public ResponseResult roleUpdate(HttpServletRequest request, Integer id) {
        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        return new ResponseResult(200, "成功", null);
    }

    //选择一个用户显示到layer
    @GetMapping("/{id}")
    public AdminUserDTO selectOne(@PathVariable("id") String id) {
        AdminUser adminUser = adminService.findWithId(id);

        AdminUserDTO adminUserDTO = new AdminUserDTO();
        Integer roleId = adminUser.getRoleId();
        Role role = roleService.findById(roleId);
        String name = role.getName();
        BeanUtils.copyProperties(adminUser, adminUserDTO);
        adminUserDTO.setName(name);

        return adminUserDTO;
    }

    //选择一个角色显示到layer
    @GetMapping("/roleId")
    public Role selectRoleOne(@RequestParam(value = "id") Integer id) {
        return roleService.findById(id);
    }

    //获取session中id的值
    @GetMapping("/getId")
    public Integer getId(HttpSession session) {
        return (Integer) session.getAttribute("id");
    }

    //更新用户
    @PostMapping("/userUpdate")
    public ResponseResult update(AdminUserDTO adminUserDTO) {
        String id = adminUserDTO.getId();
        String roleName = adminUserDTO.getName();
        try {
            Role byName = roleService.findByName(roleName);
            Integer roleId = byName.getId();
            Integer result = adminService.updateByRoleIdAndId(id, roleId);
            if (result != null) {
                return new ResponseResult(200, "修改成功", null);
            }
        } catch (Exception e) {
            return new ResponseResult(500, "该角色不存在，请先创建该角色", null);
        }
        return null;
    }

    //删除多个角色
    @PostMapping("/roleDelete")
    public ResponseResult roleDelete(@RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            int temp = Integer.parseInt(id);

                Integer byRoleId = adminService.findByRoleId(temp);
                if (byRoleId != 0) {
                    return new ResponseResult(500, "还有学生属于当前角色，请先删除属于该角色的学生", null);
                } else {
                    roleService.deleteRole(temp);
                }
        }
        return new ResponseResult(200, "删除成功", null);
    }

    //删除一个角色
    @PostMapping("/roleDeleteOne")
    public ResponseResult roleDeleteOne(@RequestParam(value = "id") Integer id) {

            Integer byRoleId = adminService.findByRoleId(id);
            if (byRoleId != 0) {
                return new ResponseResult(500, "还有学生属于当前角色，请先删除属于该角色的学生", null);
            } else {
                roleService.deleteRole(id);
            }
        return new ResponseResult(200, "删除成功", null);
    }

    //添加角色
    @PostMapping("/roleAdd")
    public ResponseResult roleAdd(Role role) {
        roleService.addRole(role);
        return new ResponseResult(200, "添加成功", null);
    }

    //更新用户
    @PostMapping("/roleUpdate")
    public ResponseResult roleUpdate(Role role) {
        roleService.roleUpdate(role);
        return new ResponseResult(200, "修改成功", null);
    }

    //查询所有学生作业
    @GetMapping("/UserWorkList")
    public LayuiResponse getUserWorkList(int page, int limit) {
        List<UserWork> allUserWork = userWorkService.findAllUserWork(page, limit);
        LayuiResponse response = new LayuiResponse();
        PageInfo pageInfo = new PageInfo(allUserWork);
        response.setData(pageInfo.getList());
        response.setCount(pageInfo.getTotal());
        return response;
    }

    //获得所有角色到select中
    @GetMapping("/getAllRole")
    public List<Role> getAllRole() {
        List<Role> allRole = roleService.getAllRole();
        return allRole;
    }

    //条件查询用户
    @GetMapping ("/findUser")
    public LayuiResponse getList(String id,String username ,Integer name,int page,int limit) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setUsername(username);
        adminUser.setRoleId(name);
        List<AdminUser> list = adminService.findUser(adminUser,page,limit);
        List<AdminUserDTO> result = new ArrayList<>();
        for (AdminUser user : list) {
            AdminUserDTO adminUserDTO = new AdminUserDTO();
            Integer Id = user.getRoleId();
            Role role = roleService.findById(Id);
            String roleName = role.getName();
            BeanUtils.copyProperties(user, adminUserDTO);
            adminUserDTO.setName(roleName);
            result.add(adminUserDTO);
        }
        PageInfo pageInfo = new PageInfo(result);
        LayuiResponse response = new LayuiResponse();
        response.setData(pageInfo.getList());
        response.setCount(pageInfo.getTotal());
        return response;
    }

    @PostMapping("/userWorkDelete")
    public ResponseResult userWorkDeleteOne(@RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            int temp = Integer.parseInt(id);
            userWorkService.userWorkDeleteOne(temp);
        }
        return new ResponseResult(200, "删除成功", null);
    }

    @PostMapping("/userWorkDeleteOne")
    public ResponseResult userWorkDeleteOne(Integer id) {
        userWorkService.userWorkDeleteOne(id);
        return new ResponseResult(200, "删除成功", null);
    }

    @PostMapping("/homeWorkDeleteOne")
    public ResponseResult homeWorkDeleteOne(Integer id) {

            Integer byHomeWorkId = userWorkService.findByHomeWorkId(id);

            if (byHomeWorkId != 0) {
                return new ResponseResult(500, "还有学生作业属于该作业，不可删除", null);
            }else{
                homeWorkService.deleteHomeWorkOne(id);
            }
        return new ResponseResult(200, "删除成功", null);
    }

    @PostMapping("/homeWorkDelete")
    public ResponseResult homeWorkDelete(@RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            int temp = Integer.parseInt(id);

            Integer byHomeWorkId = userWorkService.findByHomeWorkId(temp);
            if (byHomeWorkId != 0) {
                return new ResponseResult(500, "还有学生作业属于该作业，不可删除", null);
            } else {
                homeWorkService.deleteHomeWorkOne(temp);
            }

        }
        return new ResponseResult(200, "删除成功", null);
    }
}
