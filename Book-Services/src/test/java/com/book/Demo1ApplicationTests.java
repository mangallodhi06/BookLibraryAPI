package com.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.book.controllers.BookController;
import com.book.model.Book;

@SpringBootTest
class Demo1ApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired 
	BookController controller;
	
	@Test
	@Order(1)
	public void testfindAllBook() {
		List<Book> books = this.controller.findAllBook();
		assertThat(books).isNotNull();
	}
	
	@Test
	@Order(2)
	public void testfindByID() {
		Book book = this.controller.findByID("B121");
		assertThat(book).isNotNull();
	}

}
