package com.mightyjava.resource;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Resource<T> {
	@GetMapping
	ResponseEntity<Collection<T>> findAll();

	@GetMapping("{id}")
	ResponseEntity<T> findById(@PathVariable UUID id);

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<T> save(@RequestBody T t);

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<T> update(@RequestBody T t);

	@PatchMapping("{id}")
	ResponseEntity<T> patch(@PathVariable UUID id, @RequestBody Map<Object, Object> fields);

	@DeleteMapping("{id}")
	ResponseEntity<String> deleteById(@PathVariable UUID id);

	@GetMapping("/invalid")
	ResponseEntity<String> invalid();
}
