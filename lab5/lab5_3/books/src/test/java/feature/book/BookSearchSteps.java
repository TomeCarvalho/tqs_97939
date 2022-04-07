package feature.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cucumber.book.Book;
import cucumber.book.Library;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    private Date convertToDate(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
    }

    @Given("a book with the title {string}, category {string}, written by {string}, published in {iso8601Date}")
    public void a_book_with_the_title_written_by_published_in(final String title, final String category, final String author, final LocalDateTime published) {
        Book book = new Book(title, author, category, convertToDate(published));
        library.addBook(book);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void the_customer_searches_for_books_published_between_and(final LocalDateTime from, final LocalDateTime to) {
        result = library.findBooks(convertToDate(from), convertToDate(to));
    }

    @Then("{int} books should have been found")
    public void books_should_have_been_found(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book {int} should have the title {string}")
    public void book_should_have_the_title(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }

    @When("the customer searches for books published by {string}")
    public void theCustomerSearchesForBooksPublishedBy(String author) {
        result = library.findBooksByAuthor(author);
    }

    @When("the customer searches for books with the category {string}")
    public void theCustomerSearchesForBooksWithTheCategory(String category) {
        result = library.findBooksByCategory(category);
    }

    @When("the customer searches for books with the title {string}")
    public void theCustomerSearchesForBooksWithTheTitle(String title) {
        result = library.findBooksByTitle(title);
    }

    @Given("I have the following books in the store")
    public void iHaveTheFollowingBooksInTheStore(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {

            Date date = convertToDate(LocalDateTime.parse(columns.get("published") + "T00:00:00"));
            library.addBook(new Book(columns.get("title"), columns.get("author"), columns.get("category"), date));
        }
    }
}
