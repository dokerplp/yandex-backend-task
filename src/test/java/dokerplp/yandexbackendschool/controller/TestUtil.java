package dokerplp.yandexbackendschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestUtil {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static final String host = "localhost";
    public static final long port = 8080;

    static {
        mapper.findAndRegisterModules();
    }

    public static CloseableHttpResponse nodesSendRequest(UUID id) throws IOException {
        HttpGet request = new HttpGet(String.format("http://%s:%s/nodes/%s", TestUtil.host, TestUtil.port, id.toString()));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return HttpClientBuilder.create().build().execute(request);
    }

    public static CloseableHttpResponse importSendRequest(ShopUnitImportRequest suir) throws IOException {
        HttpPost request = new HttpPost(String.format("http://%s:%s/imports", TestUtil.host, TestUtil.port));
        StringEntity entity = new StringEntity(mapper.writeValueAsString(suir));
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return HttpClientBuilder.create().build().execute(request);
    }

    public static CloseableHttpResponse deleteSendRequest(UUID id) throws IOException {
        HttpDelete request = new HttpDelete(String.format("http://%s:%s/delete/%s", TestUtil.host, TestUtil.port, id.toString()));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return HttpClientBuilder.create().build().execute(request);
    }

    public static CloseableHttpResponse salesSendRequest(LocalDateTime dateTime) throws IOException, URISyntaxException {

        URIBuilder builder = new URIBuilder(String.format("http://%s:%s/sales", TestUtil.host, TestUtil.port));
        builder.setParameter("date", dateTime.toString());

        HttpGet request = new HttpGet(builder.build());

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return HttpClientBuilder.create().build().execute(request);
    }

}
