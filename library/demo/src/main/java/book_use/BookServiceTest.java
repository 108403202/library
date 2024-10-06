package book_use;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = BookServiceTest.class)
public class BookServiceTest {

    @MockBean
    private BookDao bookDao;

    @Test
    public void getBookById() throws Exception {
        Book mockBook = new Book(); // 創建一個 mock 的 Book 对象
        mockBook.setBookId(200); // 設置 mock 的 bookId
        mockBook.setTitle("Harry Potter"); // 設置 mock 的 title
        Mockito.when(bookDao.getBookById(3)).thenReturn(mockBook);

        // 返回的會是名字為I'm mock 3的user對象
        Book book = bookDao.getBookById(3);

        Assert.assertNotNull(book);
        Assert.assertEquals(book.getBookId(),Integer.valueOf(200));
        Assert.assertEquals(book.getTitle(), "Harry Potter");
    }


}
