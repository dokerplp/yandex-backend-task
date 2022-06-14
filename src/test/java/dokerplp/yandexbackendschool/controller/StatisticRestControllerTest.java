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

public class StatisticRestControllerTest {

    @Test
    public void nothingFoundTest() throws IOException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(UUID.randomUUID(), now.minusDays(2), now.plusDays(2))) {
            assertEquals(response.getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void simpleOfferTest() throws IOException, URISyntaxException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", null, ShopUnitType.OFFER, 39990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        LocalDateTime now = LocalDateTime.now();

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(iphone7.getId(), now.minusDays(2), now.plusDays(2))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu = new ShopUnitStatisticUnit(iphone7.getId(), "IPhone 7", null, ShopUnitType.OFFER, 39990L, now);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }
    }


    @Test
    public void offerTest() throws IOException, URISyntaxException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", null, ShopUnitType.OFFER, 39990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        LocalDateTime now1 = LocalDateTime.now();

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now1))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(iphone7.getId(), now1.minusDays(2), now1.plusDays(2))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu = new ShopUnitStatisticUnit(iphone7.getId(), "IPhone 7", null, ShopUnitType.OFFER, 39990L, now1);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }

        iphone7 = new ShopUnitImport(iphone7.getId(), "IPhone 7", null, ShopUnitType.OFFER, 49990L);
        importList.clear();
        importList.add(iphone7);

        LocalDateTime now2 = LocalDateTime.now();

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now2))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        iphone7 = new ShopUnitImport(iphone7.getId(), "IPhone 7 Pro Max", null, ShopUnitType.OFFER, 99990L);
        importList.clear();
        importList.add(iphone7);

        LocalDateTime now3 = LocalDateTime.now();

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now3))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(iphone7.getId(), now3.minusDays(2), now3.plusDays(2))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu1 = new ShopUnitStatisticUnit(iphone7.getId(), "IPhone 7", null, ShopUnitType.OFFER, 39990L, now1);
            ShopUnitStatisticUnit susu2 = new ShopUnitStatisticUnit(iphone7.getId(), "IPhone 7", null, ShopUnitType.OFFER, 49990L, now2);
            ShopUnitStatisticUnit susu3 = new ShopUnitStatisticUnit(iphone7.getId(), "IPhone 7 Pro Max", null, ShopUnitType.OFFER, 99990L, now3);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu1);
            items.add(susu2);
            items.add(susu3);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void categoryTest() throws IOException, URISyntaxException {
        ShopUnitImport phones = new ShopUnitImport(UUID.randomUUID(), "Phones", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", phones.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.randomUUID(), "IPhone 6", phones.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.randomUUID(), "IPhone 5", phones.getId(), ShopUnitType.OFFER, 19990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(phones);
        importList.add(iphone5);
        importList.add(iphone6);
        importList.add(iphone7);

        LocalDateTime now1 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now1))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        iphone5.setPrice(9990L);
        importList.clear();
        importList.add(iphone5);

        LocalDateTime now2 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now2))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitImport iphone13proMax = new ShopUnitImport(UUID.randomUUID(), "IPhone 13 Pro Max", phones.getId(), ShopUnitType.OFFER, 179990L);
        importList.clear();
        importList.add(iphone13proMax);

        LocalDateTime now3 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now3))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(phones.getId(), now3.minusDays(2), now3.plusDays(2))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu1 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 29990L, now1);
            ShopUnitStatisticUnit susu2 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 26656L, now2);
            ShopUnitStatisticUnit susu3 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 64990L, now3);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu1);
            items.add(susu2);
            items.add(susu3);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void timeTest() throws IOException, URISyntaxException {
        ShopUnitImport phones = new ShopUnitImport(UUID.randomUUID(), "Phones", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", phones.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.randomUUID(), "IPhone 6", phones.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.randomUUID(), "IPhone 5", phones.getId(), ShopUnitType.OFFER, 19990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(phones);
        importList.add(iphone5);
        importList.add(iphone6);
        importList.add(iphone7);

        LocalDateTime now1 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now1))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        iphone5.setPrice(9990L);
        importList.clear();
        importList.add(iphone5);

        LocalDateTime now2 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now2))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitImport iphone13proMax = new ShopUnitImport(UUID.randomUUID(), "IPhone 13 Pro Max", phones.getId(), ShopUnitType.OFFER, 179990L);
        importList.clear();
        importList.add(iphone13proMax);

        LocalDateTime now3 = LocalDateTime.now();
        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, now3))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(phones.getId(), now1, now1)) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu1 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 29990L, now1);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu1);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(phones.getId(), now1.plusDays(1), now1.plusDays(1))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(phones.getId(), now2, now3)) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu2 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 26656L, now2);
            ShopUnitStatisticUnit susu3 = new ShopUnitStatisticUnit(phones.getId(), "Phones", null, ShopUnitType.CATEGORY, 64990L, now3);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu2);
            items.add(susu3);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void mainStatisticTest() throws IOException {
        ShopUnitImport products = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a111"), "Products", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport appliances = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a112"), "Appliances", products.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport washingMachines = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a113"), "Washing Machines", appliances.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport dishwashes = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a114"), "Dishwashes", appliances.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport bosch = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a115"), "Bosch", dishwashes.getId(), ShopUnitType.OFFER, 199990L);
        ShopUnitImport tech = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a116"), "Tech", products.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport phones = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a117"), "Phones", tech.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport tablets = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a118"), "Tablets", tech.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport apple = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a119"), "Apple", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport android = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a120"), "Android", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", apple.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", apple.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", apple.getId(), ShopUnitType.OFFER, 19990L);
        ShopUnitImport xiaomi = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", android.getId(), ShopUnitType.OFFER, 13990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(products);
        importList.add(appliances);
        importList.add(washingMachines);
        importList.add(dishwashes);
        importList.add(bosch);
        importList.add(tech);
        importList.add(phones);
        importList.add(tablets);
        importList.add(apple);
        importList.add(android);
        importList.add(iphone7);
        importList.add(iphone6);
        importList.add(iphone5);
        importList.add(xiaomi);

        LocalDateTime now = LocalDateTime.now();

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, now);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(apple.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(dishwashes.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.statisticSendRequest(products.getId(), now.minusDays(2), now.plusDays(2))) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            ShopUnitStatisticUnit susu1 = new ShopUnitStatisticUnit(products.getId(), "Products", null, ShopUnitType.CATEGORY, 60790L, now);
            ShopUnitStatisticUnit susu2 = new ShopUnitStatisticUnit(products.getId(), "Products", null, ShopUnitType.CATEGORY, 106990L, now);
            ShopUnitStatisticUnit susu3 = new ShopUnitStatisticUnit(products.getId(), "Products", null, ShopUnitType.CATEGORY, 13990L, now);
            List<ShopUnitStatisticUnit> items = new ArrayList<>();
            items.add(susu1);
            items.add(susu2);
            items.add(susu3);
            ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
            susr.setItems(items);

            String json = TestUtil.mapper.writeValueAsString(susr);
            assertEquals(json, responseBody);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
