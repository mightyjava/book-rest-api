# GET
	http://localhost:8081/rest/books
# GET By ID
	http://localhost:8081/rest/books/cff6c1c3-be92-4cb3-9c79-5a67f63a3d61
# POST
	http://localhost:8081/rest/books
# PUT
	http://localhost:8081/rest/books
# PATCH
	http://localhost:8081/rest/books/cff6c1c3-be92-4cb3-9c79-5a67f63a3d61
# DELETE
	http://localhost:8081/rest/books/cff6c1c3-be92-4cb3-9c79-5a67f63a3d61
# Image QR Code
	http://localhost:8081/rest/books/generateImageQRCode/{bookId}
# Byte QR Code
	http://localhost:8081/rest/books/generateByteQRCode/{bookId}	
	
<hr/>

# After Spring Data Rest	
# Book
	http://localhost:8081/rest/book
# Search
	http://localhost:8081/rest/book/search		
# Find By Title
	http://localhost:8081/rest/book/search/findByTitle?title=Spring%20Microservices%20in%20Action
# Find By Author
	http://localhost:8081/rest/book/search/findByAuthor?author=John%20Carnell
# Find By ISBN Number
	http://localhost:8081/rest/book/search/findByIsbnNumber?isbnNumber=9351199193
# Find By Language
	http://localhost:8081/rest/book/search/findByLanguage?language=English
# Find By Price
	http://localhost:8081/rest/book/search/findByPrice?price=2776