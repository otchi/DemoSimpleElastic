package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.edifixio.amine.demo.entites.SimplePageResponse;
import org.edifixio.amine.demo.entites.SimpleSearchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.elasticResults.ApplicationReturn;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/simpleSearch")
public class SearchDemoController {
	
	@Autowired
	@Qualifier("simple_search_demo")
	private SearchInElasctic sie;
	
	private JsonObject joMatchAll;
	private JsonObject joSearch;
	
	private ApplicationReturn ro;
	private SimpleSearchBean ssb;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException{
		
		File f = new File(this.getClass().getClassLoader().getResource("jsquery/match_all_voiture_1.json").getPath());
		joMatchAll=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, 
				joMatchAll.toString());
		
		f = new File(this.getClass().getClassLoader().getResource("jsquery/simple_search_1.json").getPath());
		joSearch=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		
		return whiteSeach(model, localQuery);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView indexPost(@ModelAttribute("SpringWeb") SimplePageResponse  spr,ModelMap model){
		System.out.println(spr.getSearch());
		if(spr==null  || spr.getSearch()==null || spr.getSearch().equals("")){
			JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, 
					joMatchAll.toString());
			return whiteSeach(model, localQuery);
		}
		
		this.ssb=new SimpleSearchBean(spr.getSearch());
		this.ssb.setSize(spr.getSize());
		
		
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, 
				joSearch.toString());
		ro=sie.search(localQuery, ssb);
		this.ssb.setTotal(ro.getReturnMetas().getMetaHits().getTotal().intValue());

		model.addAttribute("results", ro.getHitObjectList());
		return new ModelAndView("simple_search", "command",spr);
	}
	
		
	public ModelAndView whiteSeach(ModelMap model, JsonObject localQuery){
		ro = sie.search(localQuery);
		model.addAttribute("results", ro.getHitObjectList());
		return new ModelAndView("simple_search", "command",new SimplePageResponse());
		
	}
	
	@RequestMapping(value="/next",method=RequestMethod.POST)
	public ModelAndView next(@ModelAttribute("SpringWeb") SimplePageResponse  spr,ModelMap model){
		
		JsonObject localQuery=JsonHandleUtil.jsonString(
									JsonObject.class, 
									joSearch.toString());
		ssb.next();
		//System.out.println("---------------------->"+ssb);
		ro=sie.search(localQuery, ssb);
		
		model.addAttribute("results", ro.getHitObjectList());
		return new ModelAndView("simple_search", "command",spr);
		
	} 
	
	@RequestMapping(value="/last",method=RequestMethod.POST)
	public ModelAndView last(@ModelAttribute("SpringWeb") SimplePageResponse  spr,ModelMap model){
		
		JsonObject localQuery=JsonHandleUtil.jsonString(
									JsonObject.class, 
									joSearch.toString());
		ssb.last();
		System.out.println("---------------------->"+ssb);
		ro=sie.search(localQuery, ssb);
		
		model.addAttribute("results", ro.getHitObjectList());
		return new ModelAndView("simple_search", "command",spr);
		
	} 
	
}











