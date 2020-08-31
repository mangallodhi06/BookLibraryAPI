package com.library.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.library.model.Book;

@FeignClient(name="Book-Service")
public interface RemoteCallService {
	
	@RequestMapping(method=RequestMethod.GET, value="/allbook")
	public List<Book> getBookData();
	

	@RequestMapping(method=RequestMethod.GET, value="/book/{b_id}")
	public Book getBook(String b_id);

}