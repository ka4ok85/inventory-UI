package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import restmodels.Restatementjob;

import com.example.exception.ResourceNotFoundException;

@RestController
public class NoUIController {

    @RequestMapping(value="/ui/add_restatement_job_process", method=RequestMethod.POST)
    public void addRestatementJobProcess(Model model) {
        System.out.println("hit add");
        //throw new ResourceNotFoundException();

//return true;

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

        Restatementjob restatementjob = new Restatementjob();
        restatementjob.setExpectedQuantity("2");
        //String url = "http://localhost:8080/api/getAllRestatementJobsForStoreAndUser/" + hardcodedStore + "/" + hardcodedUser;
        String url = "http://localhost:8080/api/addRestatementJob/";
        try {
        	Restatementjob restatementjobAdded = restTemplate.postForObject(url, restatementjob, Restatementjob.class);			
		} catch (Exception e) {
			throw new ResourceNotFoundException();
		}
        

        
        String hardcodedUser = "1";
        String hardcodedStore = "1";
        //String url = "http://localhost:8080/api/getAllRestatementJobsForStoreAndUser/" + hardcodedStore + "/" + hardcodedUser;
        //Restatementjob[] body  = restTemplate.getForObject(url, Restatementjob[].class);
        //model.addAttribute("jobs", body);

        //return "restatementjoblist";

    	//return "wqq";
    }
	
}