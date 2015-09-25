package org.edifixio.amine.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.edifixio.amine.demo.entites.SimplePageResponse;
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
	private JsonObject jo;
	private ResultObject ro;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) throws FileNotFoundException, IOException {
		// File f=new File()
		File f = new File(this.getClass().getClassLoader().getResource("jsquery/match_all_voiture_1.json").getPath());
		System.out.println(f.exists());
		jo=JsonHandleUtil.jsonFile(f).getAsJsonObject();
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, jo.toString());
		
		ro = sie.search(localQuery);
		System.out.println(ro);
		AggrsResultObject aro=ro.getAggsresult();
		AggrsResultObject cpAro=aro.getCopy();
		
		model.addAttribute("results", ro.getResultList());
		model.addAttribute("facets", aro.getFacets());
		
	//ro.getAggsresult().getFacets().get("test").getBuckets().get("us").getAggregations().getFacetableAggrs().get("cyl").getBuckets().get("8").setIsChecked(false);;
		return new ModelAndView("home", "command",new SimplePageResponse("",cpAro));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView indexPost(@ModelAttribute("SpringWeb") SimplePageResponse  pbr, ModelMap model)
			throws FileNotFoundException, IOException {
		System.out.println("/************************************************************************"
				+ "**************************************************/");
		System.out.println("cp : --------->"+pbr.getAro());
		this.ro.getAggsresult().update(pbr.getAro());
		System.out.println("origin : --------->"+this.ro.getAggsresult());
		System.out.println("jo----->"+jo);
		JsonObject localQuery=JsonHandleUtil.jsonString(JsonObject.class, jo.toString());
		ro = sie.search(localQuery);
		//System.out.println(ro);
		ro.setAggsresult(this.ro.getAggsresult());
		model.addAttribute("facets",this.ro.getAggsresult().getFacets());
		model.addAttribute("results",this.ro.getResultList());
				
		//pbr.setAro(this.ro.getAggsresult().getCopy());
		//System.out.println("cp : --------->"+pbr.getAro());
		System.out.println("/************************************************************************"
				+ "**************************************************/");
		return new ModelAndView("home", "command",pbr);
	
	}

}
