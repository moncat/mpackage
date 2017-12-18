package com.co.example.controller.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.log.aide.TBrLogUserQuery;

/**
 * 用户操作日志
 * @author zyl
 *
 */
@Controller
@RequestMapping("loguser")
public class LogUserController extends BaseControllerHandler<TBrLogUserQuery> {

}
