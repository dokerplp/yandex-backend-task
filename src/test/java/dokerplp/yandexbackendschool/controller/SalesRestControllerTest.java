package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitImport;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.dto.ShopUnitStatisticResponse;
import dokerplp.yandexbackendschool.dto.ShopUnitStatisticUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Test
    public void allOffersTest() throws IOException, URISyntaxException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", null, ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", null, ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", null, ShopUnitType.OFFER, 19990L);
        ShopUnitImport xiaomi = new ShopUnitImport(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", null, ShopUnitType.OFFER, 13990L);
        ShopUnitImport philips = new ShopUnitImport(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a125"), "Philips", null, ShopUnitType.OFFER, 125990L);

        List<ShopUnitImport> iphone = new ArrayList<>();
        iphone.add(iphone5);
        iphone.add(iphone6);
        iphone.add(iphone7);

        List<ShopUnitImport> android = new ArrayList<>();
        android.add(xiaomi);
        android.add(philips);

        LocalDateTime time1 = LocalDateTime.parse("2022-06-12T15:00:00");
        ShopUnitImportRequest suir = new ShopUnitImportRequest(iphone, time1);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        LocalDateTime time2 = LocalDateTime.parse("2022-06-12T20:00:00");
        suir = new ShopUnitImportRequest(android, time2);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitStatisticUnit iphone7stat = new ShopUnitStatisticUnit(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", null, ShopUnitType.OFFER, 39990L, time1);
        ShopUnitStatisticUnit iphone6stat = new ShopUnitStatisticUnit(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", null, ShopUnitType.OFFER, 29990L, time1);
        ShopUnitStatisticUnit iphone5stat = new ShopUnitStatisticUnit(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", null, ShopUnitType.OFFER, 19990L, time1);

        ShopUnitStatisticUnit xiaomiStat = new ShopUnitStatisticUnit(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", null, ShopUnitType.OFFER, 13990L, time2);
        ShopUnitStatisticUnit philipsStat = new ShopUnitStatisticUnit(UUID.fromString("7dc1c8ff-4ba5-4a06-9bf0-239bd516a125"), "Philips", null, ShopUnitType.OFFER, 125990L, time2);

        List<ShopUnitStatisticUnit> units1 = new ArrayList<>();
        units1.add(philipsStat);
        units1.add(iphone5stat);
        units1.add(iphone6stat);
        units1.add(iphone7stat);
        units1.add(xiaomiStat);
        ShopUnitStatisticResponse susr1 = new ShopUnitStatisticResponse(units1);

        LocalDateTime time3 = LocalDateTime.parse("2022-06-12T21:00:00");
        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time3)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(susr1);
            assertEquals(json, responseBody);
        }

        List<ShopUnitStatisticUnit> units2 = new ArrayList<>();
        units2.add(xiaomiStat);
        units2.add(philipsStat);
        ShopUnitStatisticResponse susr2 = new ShopUnitStatisticResponse(units2);

        LocalDateTime time4 = LocalDateTime.parse("2022-06-13T19:00:00");
        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time4)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(susr2);
            assertEquals(json, responseBody);
        }

        LocalDateTime time5 = LocalDateTime.parse("2022-06-13T20:00:00");
        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time5)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(susr2);
            assertEquals(json, responseBody);
        }

        LocalDateTime time6 = LocalDateTime.parse("2022-06-13T20:00:01");
        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time6)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(new ShopUnitStatisticResponse(null));
            assertEquals(json, responseBody);
        }

    }

    @Test
    public void categoryTest() throws IOException, URISyntaxException {
        ShopUnitImport phones = new ShopUnitImport(UUID.fromString("8dc1c8ff-4ba5-4a06-9bf0-239bd516a117"), "Phones", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport tablets = new ShopUnitImport(UUID.fromString("8dc1c8ff-4ba5-4a06-9bf0-239bd516a118"), "Tablets", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport apple = new ShopUnitImport(UUID.fromString("8dc1c8ff-4ba5-4a06-9bf0-239bd516a119"), "Apple", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport android = new ShopUnitImport(UUID.fromString("8dc1c8ff-4ba5-4a06-9bf0-239bd516a120"), "Android", phones.getId(), ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> items = new ArrayList<>();
        items.add(phones);
        items.add(tablets);
        items.add(apple);
        items.add(android);

        LocalDateTime time1 = LocalDateTime.parse("2022-08-12T15:00:00");
        ShopUnitImportRequest suir = new ShopUnitImportRequest(items, time1);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time1)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(new ShopUnitStatisticResponse(null));
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void categoryAndOfferTest() throws IOException, URISyntaxException {
        ShopUnitImport phones = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a117"), "Phones", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport tablets = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a118"), "Tablets", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport apple = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a119"), "Apple", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport android = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a120"), "Android", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", apple.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", apple.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", apple.getId(), ShopUnitType.OFFER, 19990L);
        ShopUnitImport xiaomi = new ShopUnitImport(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", android.getId(), ShopUnitType.OFFER, 13990L);

        List<ShopUnitImport> items = new ArrayList<>();
        items.add(phones);
        items.add(tablets);
        items.add(apple);
        items.add(android);
        items.add(iphone5);
        items.add(iphone6);
        items.add(iphone7);
        items.add(xiaomi);

        LocalDateTime time1 = LocalDateTime.parse("2022-10-12T15:00:00");
        ShopUnitImportRequest suir = new ShopUnitImportRequest(items, time1);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitStatisticUnit iphone7stat = new ShopUnitStatisticUnit(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", apple.getId(), ShopUnitType.OFFER, 39990L, time1);
        ShopUnitStatisticUnit iphone6stat = new ShopUnitStatisticUnit(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", apple.getId(), ShopUnitType.OFFER, 29990L, time1);
        ShopUnitStatisticUnit iphone5stat = new ShopUnitStatisticUnit(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", apple.getId(), ShopUnitType.OFFER, 19990L, time1);
        ShopUnitStatisticUnit xiaomiStat = new ShopUnitStatisticUnit(UUID.fromString("9dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", android.getId(), ShopUnitType.OFFER, 13990L, time1);

        List<ShopUnitStatisticUnit> units = new ArrayList<>();
        units.add(iphone5stat);
        units.add(iphone6stat);
        units.add(iphone7stat);
        units.add(xiaomiStat);
        ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse(units);

        try (CloseableHttpResponse response = TestUtil.salesSendRequest(time1)){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }

    }

}
