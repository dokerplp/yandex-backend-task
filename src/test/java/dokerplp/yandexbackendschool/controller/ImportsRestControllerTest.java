package dokerplp.yandexbackendschool.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dokerplp.yandexbackendschool.dto.ShopUnitImport;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



class ImportsRestControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    {
        mapper.findAndRegisterModules();
    }

    @Test
    public void imports() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport();
        iphone7.setId(UUID.randomUUID());
        iphone7.setName("IPhone 7");
        iphone7.setPrice(39990L);
        iphone7.setType(ShopUnitType.OFFER);
        iphone7.setParentId(null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);
        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        HttpPost request = new HttpPost("http://localhost:8080/imports");
        StringEntity entity = new StringEntity(mapper.writeValueAsString(suir));
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = HttpClientBuilder.create().build().execute( request );
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }
}