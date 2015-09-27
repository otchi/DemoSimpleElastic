package org.edifixio.amine.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/simplesearch")
public class SearchDemoController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(){
		return "test";
	}

}
