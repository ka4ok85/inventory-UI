package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import restmodels.Login;
import restmodels.LoginBackend;
import restmodels.Restatementjob;
import restmodels.RestatementjobAdd;
import restmodels.StoreShort;
import restmodels.Token;
import restmodels.UserShort;

import com.example.exception.ResourceNotFoundException;


@RestController
public class NoUIController {

    @RequestMapping(value="/ui/add_restatement_job_process", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
    //public void addRestatementJobProcess(@RequestBody RestatementjobAdd restatementjobAdd, ServletRequest request, Model model) {
    public void addRestatementJobProcess(@RequestBody String restatementjobAdd, ServletRequest request, Model model) {
        String authToken = ((HttpServletRequest) request).getHeader("Authorization");
    	System.out.println("UI Get Header: " + authToken);
        System.out.println(restatementjobAdd);      
        HttpEntity<String> httpEntity = HelperController.getPostHttpEntity(restatementjobAdd.toString(), authToken);
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/addRestatementJob/";


        try {
        	//Restatementjob restatementjobAdded = restTemplate.postForObject(url, restatementjob, Restatementjob.class);
        	ResponseEntity<Restatementjob> body = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Restatementjob.class);
        	Restatementjob restatementjobAdded = body.getBody();
        	System.out.println(restatementjobAdded);
        	// Restatementjob restatementjobAdded
		} catch (Exception e) {
			throw new ResourceNotFoundException();
		}
    }
/*
// set headers
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

// send request and parse result
ResponseEntity<String> loginResponse = restTemplate
  .exchange(urlString, HttpMethod.POST, entity, String.class);
if (loginResponse.getStatusCode() == HttpStatus.OK) {
  JSONObject userJson = new JSONObject(loginResponse.getBody());
} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
  // nono... bad credentials
}
 * 
 */
    @RequestMapping(value="/ui/process_login", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Token loginProcess(@RequestBody Login login, Model model) {
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

        System.out.println(login);
        
        LoginBackend loginBackend = new LoginBackend();
        loginBackend.setUsername(login.getLogin());
        loginBackend.setPassword(login.getPassword());
        loginBackend.setStoreId(login.getStore());
        System.out.println(loginBackend);
     
        String url = "http://localhost:8080/auth";
        Token token;
        try {
        	token = restTemplate.postForObject(url, loginBackend, Token.class);
        	System.out.println(token);
        	if (token.getToken() == null) {
            	System.out.println("Error Message" + token.getErrorMessage());
        	}
        	//token = restTemplate.getForObject(url, Token.class, loginBackend);
        	System.out.println(token.getToken());
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			//System.out.println(e.getClass());

			throw new ResourceNotFoundException();
		}

        return token;
    }
    
    
    
    @RequestMapping(value="/store_list", method=RequestMethod.GET)
    public StoreShort[] shoreStoreList(Model model) {
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


        String url = "http://localhost:8080/api/getstores/short";
        StoreShort[] body  = restTemplate.getForObject(url, StoreShort[].class);
        System.out.println(body);
        //model.addAttribute("jobs", body);

        return body;
    }
}
