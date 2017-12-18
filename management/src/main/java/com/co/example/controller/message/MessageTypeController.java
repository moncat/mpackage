package com.co.example.controller.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.message.aide.TBrMessageTypeQuery;

@Controller
@RequestMapping("messagetype")
public class MessageTypeController extends  BaseControllerHandler<TBrMessageTypeQuery> {

}
