package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import restmodels.Restatementjob;

public class HelperController {

	
/*
	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader("Authorization");
    	System.out.println("UI Get Header: " + authToken);
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", authToken);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        
        String url = "http://localhost:8080/api/getAllRestatementJobsForStoreAndUser/" + storeId.toString() + "/" + userId.toString();
        //Restatementjob[] body  = restTemplate.getForObject(url, Restatementjob[].class);
        ResponseEntity<Restatementjob[]> body  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Restatementjob[].class);
        
        model.addAttribute("jobs", body.getBody());

        return "restatementjoblist";
 */
	
	public static HttpEntity<?> getHttpEntity (String authToken) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", authToken);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        
        return httpEntity;
	}

	public static HttpEntity<String> getPostHttpEntity (String json, String authToken) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", authToken);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, requestHeaders);
        //HttpEntity<String> httpEntity = new 
        
        return httpEntity;
	}
}
