package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JSONPathAssertionExercise {
    @Before
    public void set_baseURI() {
        baseURI = "http://localhost:9000/api";
    }

    /**
     * Write a test to read and verify the "industry" field for AAPL.
     * The result should be 'Telecommunications Equipment'
     */
    @Test
    public void find_a_simple_field_value() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/company")
                .then().body("industry", equalTo("Telecommunications Equipment"));
    }

    /**
     * Write a test that reads and verifies the "description" field for AAPL
     * The result should contain the string 'smartphones'
     */
    @Test
    public void check_that_a_field_value_contains_a_given_string() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/company")
                .then().body("description", containsString("smartphones"));
    }

    /**
     * Read the 'symbol' field inside the 'quote' entry in the Apple stock book.
     * The result should be 'AAPL'
     */
    @Test
    public void find_a_nested_field_value() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("quote.symbol", equalTo("AAPL"));
    }

    /**
     * Find the list of symbols recently traded
     * and check that the list contains "PTN", "PINE" and "TRS"
     */
    @Test
    public void find_a_list_of_values() {
        when().get("/tops/last")
                .then().body("symbol", hasItems("PTN", "PINE", "TRS"));
    }

    /**
     * Check that there is at least one price that is greater than 100.
     */

    @Test
    public void check_at_least_one_items_matches_condition() {
        when().get("/tops/last")
                .then().body("price", hasItems(greaterThanOrEqualTo(100.0f)));
    }

    /**
     * Check that price of the first trade in the Apple stock book is 319.59
     */
    @Test
    public void find_a_field_of_an_element_in_a_list() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[0].price", equalTo(319.59f));
    }

    /**
     * Check that price of the last trade in the Apple stock book is 319.54
     */
    @Test
    public void find_a_field_of_the_last_element_in_a_list() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[-1].price", equalTo(319.54f));
    }

    /**
     * Check that precisely 20 trades are returned per query.
     */
    @Test
    public void find_the_number_of_trades() {
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.size()", equalTo(20));
    }

    /**
     * Check that the minimum price of any trade in the Apple stock book is 319.38
     */
    @Test
    public void find_the_minimum_trade_price() {
       given().pathParam("symbol", "aapl")
               .when().get("/stock/{symbol}/book")
               .then().body("trades.price.min()", equalTo(319.38f));
    }

    /**
     * Check that there are 13 trades with prices greater than 319.50
     */
    @Test
    public void find_the_number_of_trades_with_a_price_greater_than_some_value(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.findAll{t -> t.price > 319.50}.size()", equalTo(13));
    }

    /**
     * Check that the volume of the trade with the minimum price is 100
     */
    @Test
    public void find_the_size_of_the_trade_with_the_minimum_trade_price(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.min{t -> t.price}.size", equalTo(100.0f));
    }
}