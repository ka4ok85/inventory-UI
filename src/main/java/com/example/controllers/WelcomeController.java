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

import restmodels.Product;
import restmodels.ProductShort;
import restmodels.Restatementjob;
import restmodels.Restatementjobs;
import restmodels.Storelocation;
import restmodels.UserShort;

import com.example.InventoryUiApplication;

@Controller
public class WelcomeController {

	private static final Logger log = LoggerFactory.getLogger(InventoryUiApplication.class);

    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public String welcome(Model model) {
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
    	// get token from Request Header
        String authToken = ((HttpServletRequest) request).getHeader("Authorization");
    	System.out.println("UI Get Header: " + authToken);

    	// attach token to API Request Header
        HttpEntity<?> httpEntity = HelperController.getHttpEntity(authToken);    	

        // call API
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/getAllRestatementJobsForStoreAndUser";
        ResponseEntity<Restatementjob[]> body  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Restatementjob[].class);

        // return results to the View
        model.addAttribute("jobs", body.getBody());

        return "restatementjoblist";
    }

    @RequestMapping(value = "/ui/view_restatement_job/{id}", method = RequestMethod.GET)
    public String readRestatementJob(@PathVariable("id") Long id, ServletRequest request, Model model)
    {
    	/*
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

        String url = "http://localhost:8080/api/getRestatementJob/" + id;
        Restatementjob body  = restTemplate.getForObject(url, Restatementjob.class);
        model.addAttribute("job", body);
*/
        String authToken = ((HttpServletRequest) request).getHeader("Authorization");
     	System.out.println("UI Get Header : " + authToken);
     	
        HttpEntity<?> httpEntity = HelperController.getHttpEntity(authToken);    	
         
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("1");
        String url = "http://localhost:8080/api/getRestatementJob/" + id;
        ResponseEntity<Restatementjob> bodyJob  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Restatementjob.class);
        System.out.println("2");
        model.addAttribute("job", bodyJob.getBody());   	
        return "viewjob";
    }

    @RequestMapping("/ui/add_restatement_job")
    public String addRestatementJob(ServletRequest request, Model model)
    {
        String authToken = ((HttpServletRequest) request).getHeader("Authorization");
    	System.out.println("UI Get Header: " + authToken);
    	
        HttpEntity<?> httpEntity = HelperController.getHttpEntity(authToken);    	
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/getusers/active";
        ResponseEntity<UserShort[]> bodyUsers  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserShort[].class);
        model.addAttribute("users", bodyUsers.getBody());
        
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/api/getproductsbystore";
        ResponseEntity<ProductShort[]> bodyProducts  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, ProductShort[].class);
        model.addAttribute("products", bodyProducts.getBody());

        restTemplate = new RestTemplate();
        url = "http://localhost:8080/api/getstorelocationsbystore";
        ResponseEntity<Storelocation[]> bodyStorelocations  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Storelocation[].class);
        model.addAttribute("storelocations", bodyStorelocations.getBody());

        return "addrestatementjob";
    }

/**************************************PRODUCTS**********************************/
    
    @RequestMapping(value="/ui/products", method=RequestMethod.GET)
    public String productsList(ServletRequest request, Model model) {
    	// get token from Request Header
        String authToken = ((HttpServletRequest) request).getHeader("Authorization");
    	System.out.println("UI Get Header: " + authToken);

    	// attach token to API Request Header
        HttpEntity<?> httpEntity = HelperController.getHttpEntity(authToken);    	

        // call API
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/getproductsbystore";
        ResponseEntity<Product[]> body  = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Product[].class);

        // return results to the View
        model.addAttribute("products", body.getBody());

        return "productlist";
    }
}