package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitStatisticResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesRestControllerTest {

    @Test
    public void emptyListTest() throws IOException, URISyntaxException {
        try (CloseableHttpResponse response = TestUtil.salesSendRequest(LocalDateTime.now().minusDays(100))){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(new ShopUnitStatisticResponse(null));
            assertEquals(json, responseBody);
        }
    }

}
