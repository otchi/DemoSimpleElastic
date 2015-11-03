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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.application.SearchInElasctic;
import com.edifixio.simplElastic.application.elasticResults.ApplicationReturn;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/simpleSearch")
public class SearchDemoController {

	@Autowired
	@Qualifier("simple_search_demo")
	private SearchInElasctic sie;

	private JsonObject joMatchAll;
	private JsonObject joSearch;

	private ApplicationReturn ro;
	private SimpleSearchBean ssb;

	/**************************************************************************************************/
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
		File f;
		f = new File(this.getClass().getClassLoader().getResource("jsquery/simple_search_voiture_1.json").getPath());
		joSearch = JsonHandleUtil.jsonFile(f).getAsJsonObject();

		f = new File(
				this.getClass().getClassLoader().getResource("jsquery/match_all_pagination_voiture.json").getPath());
		joMatchAll = JsonHandleUtil.jsonFile(f).getAsJsonObject();

		JsonObject localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());

		return whiteSeach(model, localQuery, 10);
	}

	/**************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView indexPost(WebRequest webRequest, ModelMap model,
			@ModelAttribute("SpringWeb") SimplePageResponse spr) {

		if (webRequest.getParameter("next") != null) {
			return surf(spr, model, true);
		}

		if (webRequest.getParameter("last") != null) {
			return surf(spr, model, false);
		}

		if (webRequest.getParameter("send") != null) {

			JsonObject localQuery;

			if (spr == null) {
				localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());
				return whiteSeach(model, localQuery, 10);
			}

			if (spr.getSearch()==null || spr.getSearch().equals("")) {
				localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());
				return whiteSeach(model, localQuery, spr.getSize());
			}
			
			this.ssb=new SimpleSearchBean(spr.getSearch());
			this.ssb.setSize(spr.getSize());
			
			localQuery=JsonHandleUtil.jsonString(JsonObject.class,
			joSearch.toString()); ro=sie.search(localQuery, ssb);
			
			this.ssb.setTotal(ro.getReturnMetas().getMetaHits().getTotal().intValue());
			
			model.addAttribute("results", ro.getHitObjectList()); 
			pagination(model);
			
			return new ModelAndView("simple_search", "command",spr);

		}

		return new ModelAndView("test");
	}

	/***********************************************************************************************/
	public ModelAndView whiteSeach(ModelMap model, JsonObject localQuery, int size) {
		this.ssb = new SimpleSearchBean();
		this.ssb.setSize(size);

		ro = sie.search(localQuery, ssb);

		this.ssb.setTotal(ro.getReturnMetas().getMetaHits().getTotal());
		model.addAttribute("results", ro.getHitObjectList());

		SimplePageResponse spr = new SimplePageResponse();
		spr.setSize(size);
		
		pagination(model);
		
		return new ModelAndView("simple_search", "command", spr);

	}

	/****************************************************************************************************/
	public ModelAndView surf(SimplePageResponse spr, ModelMap model, boolean next) {
		JsonObject localQuery;
		if (spr == null) {
			localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());
			return whiteSeach(model, localQuery, 10);
		}

		if (spr.getSearch() == null || spr.getSearch().equals("")) {
			localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());
			return iteratedQuery(localQuery, spr, model, next);

		}

		localQuery = JsonHandleUtil.jsonString(JsonObject.class, joSearch.toString());
		return iteratedQuery(localQuery, spr, model, next);

	}

	/*************************************************
	 * iterated query
	 ******************************************/
	public ModelAndView iteratedQuery(JsonObject localQuery, SimplePageResponse spr, ModelMap model, boolean next) {

		if (spr.getSize() != ssb.getSize()) {
			ssb.setSize(spr.getSize());
		} else {

			if (next)
				ssb.next();
			else
				ssb.last();
		}

		ro = sie.search(localQuery, ssb);
		
		model.addAttribute("results", ro.getHitObjectList());
		pagination(model);

		return new ModelAndView("simple_search", "command", spr);

	}
	
	public void pagination(ModelMap model){
		int page=ssb.getFrom()/ssb.getSize()+1;
		int totalPage=ssb.getTotal()/ssb.getSize();
		model.addAttribute("pagination", (page)+"/"+totalPage);
	}

}
