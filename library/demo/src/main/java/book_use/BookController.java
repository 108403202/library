package book_use;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated//數據驗證
public class BookController {//轉發Http request

    @Autowired
    private BookService bookService;
    //private GlobalExceptionHandler globalExceptionHandler;
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable @Valid int bookId) {
        //ResponseEntity<Book> 制从控制器返回的 HTTP 响应
        Book book = bookService.getBookById(bookId);

        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);//.body(book) return book
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookRequest bookRequest) {
        Integer bookId = bookService.createBook(bookRequest);

        Book book = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable @Valid Integer bookId,
                                           @RequestBody @Valid BookRequest bookRequest) {
        // 檢查 book 是否存在
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改 Book 的數據
        bookService.updateBook(bookId, bookRequest);

        Book updatedBook = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable @Valid Integer bookId) {
        bookService.deleteBookById(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
