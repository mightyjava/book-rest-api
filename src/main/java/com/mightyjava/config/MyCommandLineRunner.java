package com.mightyjava.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mightyjava.data.BookData;
import com.mightyjava.domain.Book;
import com.mightyjava.service.IService;

@Configuration
public class MyCommandLineRunner implements CommandLineRunner {

	private final String BOOKS_JSON = "/data/books.json";

	@Autowired
	private IService<Book> service;

	@Override
	public void run(String... args) throws Exception {
		if (service.findAll().size() == 0) {
			try {
				TypeReference<List<BookData>> typeReference = new TypeReference<List<BookData>>() {
				};
				InputStream inputStream = TypeReference.class.getResourceAsStream(BOOKS_JSON);
				List<BookData> books = new ObjectMapper().readValue(inputStream, typeReference);
				if (books != null && !books.isEmpty()) {
					List<Book> bookList = new ArrayList<>();
					books.forEach(book -> bookList.add(new Book(book.getTitle(), book.getAuthor(),
							book.getCoverPhotoURL(), book.getIsbnNumber(), book.getPrice(), book.getLanguage())));
					List<Book> savedBookList = service.saveAll(bookList);
					System.out.println(savedBookList.size());
				}
			} catch (Exception ex) {
				System.out.println("Unable to save books " + ex.getMessage());
			}
		}
	}

}
