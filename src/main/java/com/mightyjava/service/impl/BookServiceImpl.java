package com.mightyjava.service.impl;

import java.util.Collection;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mightyjava.domain.Book;
import com.mightyjava.repository.BookRepository;
import com.mightyjava.service.IService;

@Service
public class BookServiceImpl implements IService<Book> {
	
	@Autowired
	private BookRepository bookRepository;

//	private Long bookId = 100L;
//	private Map<Long, Book> bookMap = new HashMap<Long, Book>();
//
//	{
//		Book book = new Book();
//		book.setId(bookId);
//		book.setTitle("Spring Microservices in Action");
//		book.setAuthor("John Carnell");
//		book.setCoverPhotoURL(
//				"https://images-na.ssl-images-amazon.com/images/I/417zLTa1uqL._SX397_BO1,204,203,200_.jpg");
//		book.setIsbnNumber(1617293989L);
//		book.setPrice(2776.00);
//		book.setLanguage("English");
//		bookMap.put(book.getId(), book);
//	}

	@Override
	public Collection<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public Book saveOrUpdate(Book book) {
		return bookRepository.saveAndFlush(book);
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			bookRepository.deleteById(id);
			jsonObject.put("message", "Book deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

}
