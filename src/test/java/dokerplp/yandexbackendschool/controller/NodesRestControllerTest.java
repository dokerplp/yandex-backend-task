package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitImport;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodesRestControllerTest {

    @Test
    public void simpleOfferTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport();
        iphone7.setId(UUID.randomUUID());
        iphone7.setName("IPhone 7");
        iphone7.setPrice(39990L);
        iphone7.setType(ShopUnitType.OFFER);
        iphone7.setParentId(null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(iphone7.getId())){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println(responseBody);
            ShopUnit unit = new ShopUnit.ShopUnitBuilder()
                    .setId(iphone7.getId())
                    .setName(iphone7.getName())
                    .setParentId(iphone7.getParentId())
                    .setType(iphone7.getType())
                    .setPrice(iphone7.getPrice())
                    .setDate(suir.getUpdateDate())
                    .build();
            String json = TestUtil.mapper.writeValueAsString(unit);
            assertEquals(json, responseBody);
        }
    }

}
