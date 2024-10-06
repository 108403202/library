package book_use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {//與資料庫進行溝通

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    //Spring JDBC 自動幫我們生成的 Bean 負責去處理和資料庫溝通的所有事項
    @Override
    public Book getBookById(Integer bookId){

        String sql = "SELECT book_id, title, author, image_url, price, published_date, created_date, last_modified_date " +
                "FROM book WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<String, Object>();//放 sql 語法裡面的變數的值
        map.put("bookId", bookId);

        List<Book> bookList = namedParameterJdbcTemplate.query(sql, map, new BookRowMapper());

        if(bookList.size() > 0){
            return bookList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createBook(BookRequest bookRequest){

        String sql = "INSERT INTO book(title, author, image_url, price, published_date, created_date, last_modified_date) " +
                "VALUES (:title, :author, :imageUrl, :price, :publishedDate, :createdDate, :lastModifiedDate) RETURNING book_id";

        Map<String, Object> map = new HashMap<>();
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("publishedDate", bookRequest.getPublishedDate());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();//抓自動生成的主鍵

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        //MapSqlParameterSource parameterSource = new MapSqlParameterSource(map);上面用法
        //將 Map<String, Object> 轉換為 SqlParameterSource

        int bookId = keyHolder.getKey().intValue();

        return bookId;
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest){

        String sql = "UPDATE book SET title = :title, author = :author, image_url = :imageUrl, " +
                "price = :price, published_date = :publishedDate, last_modified_date = :lastModifiedDate" +
                " WHERE book_id = :bookId ";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("publishedDate", bookRequest.getPublishedDate());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        String sql = "DELETE FROM book WHERE book_id = :bookId ";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        namedParameterJdbcTemplate.update(sql, map);
    }

}
