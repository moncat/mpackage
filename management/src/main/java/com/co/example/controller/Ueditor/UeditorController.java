package com.co.example.controller.Ueditor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONException;
import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("ueditor")
public class UeditorController {
	
	@Value("${ueditor.savePath}")
	String savepath;
	
	@Value("${ueditor.readpath}")
	String readPath;
	
	
	@RequestMapping(value = "/add", method = { RequestMethod.GET,RequestMethod.POST })
	public void add(Model model,HttpServletRequest request, HttpServletResponse response  )throws Exception {
		request.setCharacterEncoding( "utf-8" );
		response.setHeader("Content-Type" , "text/html");
        try {  
            String exec = new ActionEnter(request, savepath,readPath).exec();  
            PrintWriter writer = response.getWriter();  
            writer.write(exec);  
            writer.flush();  
            writer.close();  
        } catch (IOException | JSONException e) {  
            e.printStackTrace();  
        }  
	}
}
