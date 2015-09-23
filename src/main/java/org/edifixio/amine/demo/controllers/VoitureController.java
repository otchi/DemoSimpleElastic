package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.elasticResults.AggrsResultObject;
import com.edifixio.amine.application.elasticResults.ResultObject;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@Controller(value = "/")
public class VoitureController {
	@Autowired
	private SearchInElasctic sie;
	JsonObject jo;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
		// File f=new File()
		File f = new File(this.getClass().getClassLoader().getResource("jsquery/match_all_voiture_1.json").getPath());
		System.out.println(f.exists());
		jo=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, jo.toString());
		
		ResultObject ro = sie.search(localQuery);
		
		
		
		
		model.addAttribute("results", ro.getResultList());
		model.addAttribute("facets", ro.getAggsresult().getFacets());
		
	//ro.getAggsresult().getFacets().get("test").getBuckets().get("us").getAggregations().getFacetableAggrs().get("cyl").getBuckets().get("8").setIsChecked(false);;
		return new ModelAndView("home", "command",ro.getAggsresult());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView indexPost(@ModelAttribute("SpringWeb") AggrsResultObject  aro, ModelMap model)
			throws FileNotFoundException, IOException {

		System.out.println("--------->"+aro);
		model.addAttribute("facets", aro.getFacets());
		return new ModelAndView("home", "command",aro);
	}

}
