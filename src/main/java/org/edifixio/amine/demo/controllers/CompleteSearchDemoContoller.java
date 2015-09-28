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

import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.elasticResults.AggrsReturnObject;
import com.edifixio.amine.application.elasticResults.ApplicationReturn;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/completeSearch")
public class CompleteSearchDemoContoller {

	@Autowired
	@Qualifier("complete_demo")
	private SearchInElasctic sie;

	private JsonObject joMatchAll;
	private JsonObject joSearch;

	private ApplicationReturn ro;
	private SimpleSearchBean ssb;

	/**************************************************************************************************/
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
		File f;
		f = new File(this.getClass().getClassLoader().getResource("jsquery/complete_search_voiture.json").getPath());
		joSearch = JsonHandleUtil.jsonFile(f).getAsJsonObject();

		f = new File(
				this.getClass().getClassLoader().getResource("jsquery/complete_match_all_voiture.json").getPath());
		joMatchAll = JsonHandleUtil.jsonFile(f).getAsJsonObject();

		JsonObject localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());

		return firstSeach(model, localQuery, 10);
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
				return firstSeach(model, localQuery, 10);
			}

			localQuery = (spr.getSearch() == null || spr.getSearch().equals("")) ? 
				 JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString()) :
					 JsonHandleUtil.jsonString(JsonObject.class, joSearch.toString());
			

			this.ssb = new SimpleSearchBean(spr.getSearch());
			this.ssb.setSize(spr.getSize());
			this.ro.getAggrs().update(spr.getAro());
			ro = sie.search(localQuery, ssb);

			this.ssb.setTotal(ro.getReturnMetas().getMetaHits().getTotal().intValue());

			model.addAttribute("results", ro.getHitObjectList());
			pagination(model);
			
			ro.setAggrs(this.ro.getAggrs());
			model.addAttribute("facets",this.ro.getAggrs().getFacets());

			return new ModelAndView("complete_search", "command", spr);

		}

		return new ModelAndView("test");
	}

	/***********************************************************************************************/
	public ModelAndView firstSeach(ModelMap model, JsonObject localQuery, int size) {
		this.ssb = new SimpleSearchBean();
		this.ssb.setSize(size);

		ro = sie.search(localQuery, ssb);

		this.ssb.setTotal(ro.getReturnMetas().getMetaHits().getTotal());
		
		AggrsReturnObject aro=ro.getAggrs();

		model.addAttribute("facets", aro.getFacets());
		model.addAttribute("results", ro.getHitObjectList());

		SimplePageResponse spr = new SimplePageResponse(aro.getCopy());
		
		spr.setSize(size);

		pagination(model);

		return new ModelAndView("complete_search", "command", spr);

	}
	
//	public ModelAndView whiteSeach(){
//		return null;
//		
//	}

	/****************************************************************************************************/
	public ModelAndView surf(SimplePageResponse spr, ModelMap model, boolean next) {
		JsonObject localQuery;
		if (spr == null) {
			localQuery = JsonHandleUtil.jsonString(JsonObject.class, joMatchAll.toString());
			return firstSeach(model, localQuery, 10);
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
		this.ro.getAggrs().update(spr.getAro());
		ro = sie.search(localQuery, ssb);

		model.addAttribute("results", ro.getHitObjectList());
		pagination(model);
		
		ro.setAggrs(this.ro.getAggrs());
		model.addAttribute("facets",this.ro.getAggrs().getFacets());

		return new ModelAndView("complete_search", "command", spr);

	}

	public void pagination(ModelMap model) {
		int page = ssb.getFrom() / ssb.getSize() + 1;
		Double totalPage = (double) ((double)ssb.getTotal() / (double) ssb.getSize());
		Integer intTotalPage = totalPage.intValue();
		if(totalPage-intTotalPage>0) intTotalPage++;
		model.addAttribute("pagination", (page) + "/" + intTotalPage);
	}

}
