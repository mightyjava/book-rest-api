package com.mightyjava.resource.impl;

import java.util.Collection;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mightyjava.domain.Book;
import com.mightyjava.exception.ApplicationException;
import com.mightyjava.exception.BookNotFoundException;
import com.mightyjava.resource.Resource;
import com.mightyjava.service.IService;

@RestController
@RequestMapping("/books")
public class BookResourceImpl implements Resource<Book> {
	
	private static Logger log = LoggerFactory.getLogger(BookResourceImpl.class);
	
	@Autowired
	private IService<Book> bookService;

	@Override
	public ResponseEntity<Collection<Book>> findAll() {
		log.info("BookResourceImpl - findAll");
		return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> findById(Long id) {
		log.info("BookResourceImpl - findById");
		Optional<Book> book = bookService.findById(id);
		if(!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		}
		return new ResponseEntity<>(book.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> save(Book book) {
		log.info("BookResourceImpl - save");
		if(book.getId() != null) {
			throw new ApplicationException("Book ID found, ID is not required for save the data");
		}
		return new ResponseEntity<>(bookService.saveOrUpdate(book), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Book> update(Book book) {
		log.info("BookResourceImpl - update");
		if(book.getId() == null) {
			throw new ApplicationException("Book ID not found, ID is required for update the data");
		}
		return new ResponseEntity<>(bookService.saveOrUpdate(book), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		log.info("BookResourceImpl - deleteById");
		Optional<Book> book = bookService.findById(id);
		if(!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		}
		return new ResponseEntity<>(bookService.deleteById(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> invalid() {
		log.info("BookResourceImpl - invalid");
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", "something is missing, please check everything before sending the request!!!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

}
