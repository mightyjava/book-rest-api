package com.mightyjava.resource.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mightyjava.domain.Book;
import com.mightyjava.exception.ApplicationException;
import com.mightyjava.exception.BookNotFoundException;
import com.mightyjava.resource.Resource;
import com.mightyjava.service.IService;
import com.mightyjava.util.MethodUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookResourceImpl implements Resource<Book> {

	@Autowired
	private IService<Book> bookService;

	private final String imagePath = "./src/main/resources/qrcodes/QRCode.png";

	@GetMapping("/generateByteQRCode/{bookId}")
	public ResponseEntity<Book> generateByteQRCode(@PathVariable("bookId") UUID bookId) {
		log.info("BookResourceImpl - generateByteQRCode");
		Book bookObject = null;
		Optional<Book> book = bookService.findById(bookId);
		if (!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		} else {
			bookObject = book.get();
			bookObject.setBase64QRCode(MethodUtils.generateByteQRCode(bookObject.getCoverPhotoURL(), 250, 250));
			bookObject.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
		}
		return new ResponseEntity<>(bookObject, HttpStatus.OK);
	}

	@GetMapping("/generateImageQRCode/{bookId}")
	public ResponseEntity<Book> generateImageQRCode(@PathVariable("bookId") UUID bookId) {
		log.info("BookResourceImpl - generateImageQRCode");
		Book bookObject = null;
		Optional<Book> book = bookService.findById(bookId);
		if (!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		} else {
			bookObject = book.get();
			MethodUtils.generateImageQRCode(bookObject.getCoverPhotoURL(), 250, 250, imagePath);
			bookObject.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
		}
		return new ResponseEntity<>(bookObject, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Collection<Book>> findAll() {
		log.info("BookResourceImpl - findAll");
		Collection<Book> books = bookService.findAll();
		List<Book> response = new ArrayList<>();
		books.forEach(book -> {
			book.add(linkTo(methodOn(BookResourceImpl.class).findById(book.getId())).withSelfRel());
			response.add(book);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> findById(UUID id) {
		log.info("BookResourceImpl - findById");
		Book bookObject = null;
		Optional<Book> book = bookService.findById(id);
		if (!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		} else {
			bookObject = book.get();
			bookObject.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
		}
		return new ResponseEntity<>(bookObject, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> save(Book book) {
		log.info("BookResourceImpl - save");
		if (book.getId() != null) {
			throw new ApplicationException("Book ID found, ID is not required for save the data");
		} else {
			Book savedBook = bookService.saveOrUpdate(book);
			savedBook.add(linkTo(methodOn(BookResourceImpl.class).findById(savedBook.getId())).withSelfRel());
			savedBook.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
			return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<Book> update(Book book) {
		log.info("BookResourceImpl - update");
		if (book.getId() == null) {
			throw new ApplicationException("Book ID not found, ID is required for update the data");
		} else {
			Book updatedBook = bookService.saveOrUpdate(book);
			updatedBook.add(linkTo(methodOn(BookResourceImpl.class).findById(updatedBook.getId())).withSelfRel());
			updatedBook.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
			return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Book> patch(UUID id, Map<Object, Object> fields) {
		Optional<Book> book = bookService.findById(id);
		if (book.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Book.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, book.get(), value);
			});
			Book updatedBook = bookService.saveOrUpdate(book.get());
			updatedBook.add(linkTo(methodOn(BookResourceImpl.class).findById(updatedBook.getId())).withSelfRel());
			updatedBook.add(linkTo(methodOn(BookResourceImpl.class).findAll()).withSelfRel());
			return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		}
		return null;
	}

	@Override
	public ResponseEntity<String> deleteById(UUID id) {
		log.info("BookResourceImpl - deleteById");
		Optional<Book> book = bookService.findById(id);
		if (!book.isPresent()) {
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
