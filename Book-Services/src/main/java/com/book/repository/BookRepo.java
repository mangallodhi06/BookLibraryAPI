package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.Book;
@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

}
