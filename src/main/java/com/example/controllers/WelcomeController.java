package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import restmodels.Restatementjobs;
import restmodels.UserShort;

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
    
    @RequestMapping(value="/ui/login", method=RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value="/ui/restatement_jobs", method=RequestMethod.GET)
    public String restatementjobsList(ServletRequest request, Model model) {
    	
    	
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
        
        String hardcodedUser = "1";
        String hardcodedStore = "1";
        String url = "http://localhost:8080/api/getAllRestatementJobsForStoreAndUser/" + hardcodedStore + "/" + hardcodedUser;
        //Restatementjob[] body  = restTemplate.getForObject(url, Restatementjob[].class);
        ResponseEntity<Restatementjob[]> body  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Restatementjob[].class);
        
        model.addAttribute("jobs", body.getBody());

        return "restatementjoblist";
    }

    @RequestMapping(value = "/ui/restatement_job/{id}", method = RequestMethod.GET, produces = "application/json")
    public String readRestatementJob(@PathVariable("id") Long id, Model model)
    {
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

        String hardcodedStore = "1";
        String url = "http://localhost:8080/api/getRestatementJob/" + id;
        Restatementjob body  = restTemplate.getForObject(url, Restatementjob.class);
        model.addAttribute("job", body);

        return "viewjob";
    }

    @RequestMapping("/ui/add_restatement_job")
    public String addRestatementJob(Model model)
    {
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

        String hardcodedStore = "1";
        String url = "http://localhost:8080/api/getusers/" + hardcodedStore + "/active";
        UserShort[] body  = restTemplate.getForObject(url, UserShort[].class);
        model.addAttribute("jobs", body);

        url = "http://localhost:8080/api/getusers/" + hardcodedStore + "/active";
        UserShort[] usersList  = restTemplate.getForObject(url, UserShort[].class);
        model.addAttribute("users", usersList);
        
        return "addrestatementjob";
    }


}