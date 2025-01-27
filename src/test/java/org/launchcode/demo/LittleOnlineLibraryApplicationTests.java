package org.launchcode.demo;

import org.junit.jupiter.api.Test;
import org.launchcode.demo.models.ApiActions;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class LittleOnlineLibraryApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void apiQuery() throws IOException {
        String SearchResult = ApiActions.ApiSearch("Dune");
        System.out.println(ApiActions.ParseResults(SearchResult));
    }
}