package com.gyojincompay.MVCBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gyojincompay.MVCBoard.command.BCommand;
import com.gyojincompay.MVCBoard.command.BListCommand;
import com.gyojincompay.MVCBoard.command.BWriteCommand;

@Controller
public class BController {
	
	BCommand command = null;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		command = new BListCommand();
		command.excute(model);
		
		return "list";
	}
	
	@RequestMapping("/write")
	public String write(Model model) {
		
		command = new BWriteCommand();
		command.excute(model);
		
		return "write";
	}
	
}
