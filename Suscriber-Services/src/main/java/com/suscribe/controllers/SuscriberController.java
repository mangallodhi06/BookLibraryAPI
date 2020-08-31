package com.suscribe.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.suscribe.model.Suscriber;
import com.suscribe.services.SuscriberServiceImpl;

@RestController
public class SuscriberController {

	@Autowired
	SuscriberServiceImpl serviceImpl;
	
	
	@RequestMapping("/allSuscribers")
	public List<Suscriber> findAllSuscribers() {
		return serviceImpl.findAll();
	}
	
	@RequestMapping("suscriber/{s_name}")
	public Suscriber findByName(@PathVariable String s_name) {
		return serviceImpl.findByName(s_name);
	}
	

	@RequestMapping(value="/addSuscriber", method = RequestMethod.POST )
	public ResponseEntity<Suscriber> postSubscription(@RequestBody Suscriber suscriber){

		HttpStatus str = serviceImpl.updateAvailableCopies(suscriber).getStatusCode();
		//System.out.println("========str value ================"+str);
		if(str !=null && str.equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(serviceImpl.save(suscriber), HttpStatus.CREATED);
	}
}
