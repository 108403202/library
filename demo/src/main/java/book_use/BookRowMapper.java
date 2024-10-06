package book_use;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {//將資料庫查詢出來的數據 轉換成是 Java object

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {//spring 自動調用
        Book book = new Book();

        book.setBookId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setImageUrl(resultSet.getString("image_url"));
        book.setPrice(resultSet.getInt("price"));
        book.setPublishedDate(resultSet.getTimestamp("published_date"));
        book.setCreatedDate(resultSet.getTimestamp("created_date"));
        book.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return book;
    }

}
