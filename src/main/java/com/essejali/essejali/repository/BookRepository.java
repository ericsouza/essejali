package com.essejali.essejali.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.essejali.essejali.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
}
