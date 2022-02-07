package com.mightyjava.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

	@Override
	public Collection<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findById(UUID id) {
		return bookRepository.findById(id);
	}

	@Override
	public Book saveOrUpdate(Book book) {
		return bookRepository.saveAndFlush(book);
	}

	@Override
	public String deleteById(UUID id) {
		JSONObject jsonObject = new JSONObject();
		try {
			bookRepository.deleteById(id);
			jsonObject.put("message", "Book deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@Override
	public List<Book> saveAll(List<Book> books) {
		return bookRepository.saveAll(books);
	}

}
