package com.book.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.Book;
import com.book.repository.BookRepo;

@Service
public class BookServiceImpl {
	
	@Autowired
	BookRepo bookRepo;
	
	public void save(Book book) {
		bookRepo.save(book);
	}

	public List<Book> findAll() {
		return bookRepo.findAll();
	}

	public Book findByID(String b_id) {
		
		List<Book> books=findAll();
		for (Book book : books) {
			if(book.getB_id().equals(b_id))
				return book;
		}
		return null;
	}
	
	public Book updateCopiesAvailable(String bookId,Integer remainingCopies) {
		Book book = findByID(bookId);
		
		if(book.equals(null)) {
			//do nothing
		} else {
			book.setAvailable_copies(remainingCopies);
			
		}
		return bookRepo.save(book);
	}

}
