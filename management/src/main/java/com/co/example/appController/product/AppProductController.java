package com.co.example.appController.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.product.aide.TBrProductQuery;


@Controller
@RequestMapping("app/product")
public class AppProductController extends  BaseRestControllerHandler<TBrProductQuery> {

}
