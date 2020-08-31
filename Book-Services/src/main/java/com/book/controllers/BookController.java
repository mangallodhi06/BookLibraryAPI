package com.book.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.book.model.Book;
import com.book.services.BookServiceImpl;

@RestController
public class BookController {

	@Autowired
	BookServiceImpl serviceImpl;
	
	@RequestMapping("/allbook")
	public List<Book> findAllBook() {
		return serviceImpl.findAll();
	}
	
	@RequestMapping("book/{b_id}")
	public Book findByID(@PathVariable String b_id) {
		return serviceImpl.findByID(b_id);
	}
	@RequestMapping("books/{b_id}")
	public void findByID(@PathVariable String b_id,@RequestBody Integer remainingCopies) {
		serviceImpl.updateCopiesAvailable(b_id,remainingCopies);
	}
	
}
