package com.mightyjava.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IService<T> {
	Collection<T> findAll();
	
	Optional<T> findById(UUID id);
	
	T saveOrUpdate(T t);
	
	List<T> saveAll(List<T> t);
	
	String deleteById(UUID id);
}
