package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.edifixio.amine.demo.entites.Ecrivain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SearchInElasctic;
import com.edifixio.simplElastic.application.elasticResults.ApplicationReturn;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/complexData")
public class ComplexObjectController {
	
	@Autowired
	@Qualifier("complex_data_demo")
	private SearchInElasctic sie;

	private ApplicationReturn ro;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
	
		File f= new File(this.getClass().getClassLoader().getResource("jsquery/match_all.json").getPath());
		JsonObject localQuery = JsonHandleUtil.jsonFile(f).getAsJsonObject();
		ro = sie.search(localQuery);
		model.addAttribute("results", ro.getHitObjectList());
		System.out.println(ro);
		return new ModelAndView("perso_main_page");

	}
	
	@RequestMapping(value="/{index}" ,method=RequestMethod.GET)
	public ModelAndView detail(@PathVariable("index") Integer index,
			ModelMap model) throws FileNotFoundException, IOException {
		Ecrivain ecrivain=(Ecrivain)(ro.getHitObjectList().get(index).getSourceObject());
		System.out.println(ecrivain.getDetail());
		model.addAttribute("detail",ecrivain.getDetail());
		return new ModelAndView("detail");

	}

}
