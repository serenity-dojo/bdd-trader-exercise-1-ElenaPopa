package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class WhenGettingCompanyDetails {

    @Before
    public void set_rest_baseuri(){
        //localhost
        baseURI = "http://localhost:9000/api";

        //public API
        //RestAssured.baseURI = "https://bddtrader.herokuapp.com/api";
    }
    @Test
    public void should_return_name_and_sector() {

        get("/stock/aapl/company")
            .then()
            .body("companyName", equalTo("Apple, Inc."))
            .body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_name_and_sector_using_path_param() {
        given().pathParam("symbol", "aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_news_for_a_requested_company() {
        given().queryParam("symbols", "fb,aapl")
                .when()
                .get("/news")
                .then()
                .body("related", everyItem(anyOf(containsString("FB"), containsString("AAPL"))));
    }
}
