package com.co.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.user.TOrg;
import com.co.example.entity.user.TRole;
import com.co.example.entity.user.TUserRole;
import com.co.example.entity.user.TUsers;
import com.co.example.entity.user.aide.TUserRoleQuery;
import com.co.example.entity.user.aide.TUsersQuery;
import com.co.example.service.user.TOrgService;
import com.co.example.service.user.TRoleService;
import com.co.example.service.user.TUserRoleService;
import com.co.example.service.user.TUsersService;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TUsersService tUsersService;
    @Autowired
    private TOrgService tOrgService;
    @Autowired
    private TRoleService tRoleService;
    @Autowired
    private TUserRoleService tUserRoleService;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) throws Exception{
        model.addAttribute("user", user);
        return "user/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Integer id) {
        TUsers user = tUsersService.queryById(id);
        model.addAttribute("user",user);
        return "user/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<TUsers> getList(TUsersQuery tUsersQuery) {
    	PageReq pageReq = new PageReq();
    	Page<TUsers> page = tUsersService.queryPageList(tUsersQuery, pageReq);
		return page;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,TUsers user){
        List<TOrg> departments = tOrgService.queryList();
        List<TRole> roles = tRoleService.queryList();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "user/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(TUsers user) throws Exception{
        user.setCreateTime(new Date());
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        user.setUserPassword(bpe.encode(user.getUserPassword()));
        tUsersService.add(user);
        logger.info("新增->ID="+user.getUserId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Integer id){
        TUsers user = tUsersService.queryById(id);
        List<TOrg> departments = tOrgService.queryList();
        List<TRole> roles = tRoleService.queryList();
        
        List<Integer> rids = new ArrayList<Integer>();
        TUserRoleQuery tUserRoleQuery = new TUserRoleQuery();
        tUserRoleQuery.setUserId(id);
        List<TUserRole> tUserRoles = tUserRoleService.queryList(tUserRoleQuery);
        for (TUserRole tUserRole : tUserRoles) {
        	rids.add(tUserRole.getRoleId());
		}

        model.addAttribute("user",user);
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "user/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(TUsers user) throws Exception{
    	tUsersService.add(user);
        logger.info("修改->ID="+user.getUserId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Integer id) throws Exception{
    	tUsersService.deleteById(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
