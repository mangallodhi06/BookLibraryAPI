package com.suscribe.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.suscribe.model.Book;
import com.suscribe.model.Suscriber;
import com.suscribe.repository.SuscriberRepository12;

@Service

public class SuscriberServiceImpl {

	@Autowired
	SuscriberRepository12 suscriberRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Suscriber save(Suscriber suscriber) {

		return suscriberRepo.save(suscriber);
	}
	
	public List<Suscriber> findAll() {
		return suscriberRepo.findAll();
	}
	
	public Suscriber findByName(String s_name) {
		List<Suscriber> suscribers=findAll();
		for (Suscriber suscriber : suscribers) {
			if(suscriber.getS_name().equalsIgnoreCase(s_name))
				return suscriber;
		}
		return null;
	}
	public int getAvailableCopies(Suscriber suscriber) {
	
		Book book=(Book)restTemplate.getForObject("http://localhost:8080/book/" + suscriber.getB_id(), Book.class);
		return book.getAvailable_copies();
	}
	public ResponseEntity<String> updateAvailableCopies(Suscriber suscriber) {
			int remainingCopies = getAvailableCopies(suscriber);
			int updatedremainingcopies = 0;
			if(remainingCopies <= 0 && suscriber.getReturn_date() == null ) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Book copies are not available for suscriber") ;
			}
			
			if (remainingCopies >= 0) {
				if (suscriber.getReturn_date() == null) {
					updatedremainingcopies = remainingCopies - 1;
				} else {
					updatedremainingcopies = remainingCopies + 1;
				}
				restTemplate.put("http://localhost:8080/books/"+suscriber.getB_id(), updatedremainingcopies);
				return ResponseEntity.ok(suscriber.toString());
				
			}
			
			return ResponseEntity.ok(null);
		}
}
