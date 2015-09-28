package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.edifixio.amine.demo.entites.SimplePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.elasticResults.AggrsReturnObject;
import com.edifixio.amine.application.elasticResults.ApplicationReturn;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/facets")
public class FacetsDemoController {
	@Autowired
	@Qualifier("facet_demo")
	private SearchInElasctic sie;
	
	private JsonObject jo;
	private ApplicationReturn ro;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
		
		File f = new File(this.getClass().getClassLoader().getResource("jsquery/match_all_voiture_1.json").getPath());
		jo=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, jo.toString());
		
		ro = sie.search(localQuery);
//		System.out.println(ro);
		AggrsReturnObject aro=ro.getAggrs();
		
		model.addAttribute("results", ro.getHitObjectList());
		model.addAttribute("facets", aro.getFacets());
		
		return new ModelAndView("home", "command",new SimplePageResponse(aro.getCopy()));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView indexPost(@ModelAttribute("SpringWeb") SimplePageResponse  spr, ModelMap model)
			throws FileNotFoundException, IOException {

//		System.out.println("cp : --------->"+spr.getAro());
		this.ro.getAggrs().update(spr.getAro());
//		System.out.println("origin : --------->"+this.ro.getAggrs());
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, jo.toString());
		ro = sie.search(localQuery);
		//System.out.println(ro);
		ro.setAggrs(this.ro.getAggrs());
		model.addAttribute("facets",this.ro.getAggrs().getFacets());
		model.addAttribute("results",this.ro.getHitObjectList());
				
		return new ModelAndView("home", "command",spr);
	
	}

}
