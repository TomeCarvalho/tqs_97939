package cucumber.book;

import java.util.Date;

public class Book {
    private final String title;
    private final String author;
    private final String category;
    private final Date published;

    public Book(String title, String author, String category, Date published) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public Date getPublished() {
        return published;
    }
}