package com.mightyjava.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mightyjava.domain.Book;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends JpaRepository<Book, UUID> {

	Book findByTitle(@Param("title") String title);

	Book findByAuthor(@Param("author") String author);

	Book findByIsbnNumber(@Param("isbnNumber") Long isbnNumber);

	List<Book> findByLanguage(@Param("language") String language);

	Book findByPrice(@Param("price") Double price);
}
