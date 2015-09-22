package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.amine.application.ResultObject;
import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@Controller(value="/")
public class VoitureController {
	@Autowired
	private SearchInElasctic sie;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException{
		//File f=new File()
		File f=new File(this.getClass().getClassLoader().getResource("jsquery/match_all_voiture_1.json").getPath());
		System.out.println(f.exists());
		JsonObject  jo=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		ResultObject ro=sie.search(jo);
		model.addAttribute("results", ro.getResultList());
		model.addAttribute("facets", ro.getFacets());
		
		
		return new ModelAndView("home", "command", ro);
	}
	
}
