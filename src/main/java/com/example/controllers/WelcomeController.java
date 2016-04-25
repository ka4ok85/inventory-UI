package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import restmodels.Restatementjob;
import restmodels.Restatementjobs;

import com.example.InventoryUiApplication;

@Controller
public class WelcomeController {

	private static final Logger log = LoggerFactory.getLogger(InventoryUiApplication.class);

    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public String welcome(Model model) {
        log.debug("welcome");
        return "welcome";
    }

    @RequestMapping(value="/ui", method=RequestMethod.GET)
    public String home(Model model) {

        return "index";
    }

    @RequestMapping(value="/ui/restatement_jobs", method=RequestMethod.GET)
    public String restatementjobsList(Model model) {

    	//Iterable<Planet> planets = planetRepository.findAll();
        //model.addAttribute("planets", planets);

    	//ArrayList<> planets = new ArrayList<>()
    	
    	
    	RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();

        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        supportedMediaTypes.add(new MediaType("text", "html", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                jsonConverter.setSupportedMediaTypes(
                		supportedMediaTypes
                );
            }
        }
String url = "http://localhost:8080/api/getAllRestatementJobs";
Restatementjob[] body  = restTemplate.getForObject(url, Restatementjob[].class);

/*
        ResponseEntity<Restatementjobs> responseEntity = restTemplate.getForEntity(url, Restatementjobs.class);
        Restatementjobs body = responseEntity.getBody();
    	System.out.println(body);
*/
    	
    	model.addAttribute("jobs", body);
        return "restatementjoblist";
    }


}