package com.co.example.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.user.TOrg;
import com.co.example.entity.user.aide.TOrgQuery;
import com.co.example.service.user.TOrgService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private TOrgService tOrgService;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) throws Exception{
        model.addAttribute("user", user);
        return "department/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Integer id) {
        TOrg department = tOrgService.queryById(id);
        model.addAttribute("department",department);
        return "department/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<TOrg> getList(TOrgQuery tOrgQuery) {
    	   	
    	Pageable pageable = new PageReq();
    	Page<TOrg> page = tOrgService.queryPageList(tOrgQuery, pageable);
		return page;
    	
    }

    @RequestMapping("/new")
    public String create(){
        return "department/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(TOrg department) throws Exception{
    	tOrgService.add(department);
        logger.info("新增->ID="+department.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Integer id){
    	TOrg department = tOrgService.queryById(id);
        model.addAttribute("department",department);
        return "department/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(TOrg department) throws Exception{
    	tOrgService.updateByIdSelective(department);
        logger.info("修改->ID="+department.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Integer id) throws Exception{
    	tOrgService.deleteById(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
