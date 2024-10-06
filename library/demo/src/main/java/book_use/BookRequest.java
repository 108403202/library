package book_use;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

public class BookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @URL(message = "URL 格式不正確")
    private String imageUrl;
    @PositiveOrZero(message = "price 格式不正確")
    @Max(value = 1000, message = "價格不能超過1000")
    private Integer price;
    private Date publishedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
