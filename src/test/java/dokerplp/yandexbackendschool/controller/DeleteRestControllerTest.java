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

public class DeleteRestControllerTest {

    @Test
    public void nothingFoundTest() throws IOException {
        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(UUID.randomUUID())) {
            assertEquals(response.getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void simpleDeleteTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", null, ShopUnitType.OFFER, 39990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(iphone7.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(iphone7.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(iphone7.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void mainDeleteTest() throws IOException {

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

        LocalDateTime now = LocalDateTime.now();
        ShopUnit xiaomiUnit = new ShopUnit(xiaomi.getId(), "Xiaomi", now, android.getId(), ShopUnitType.OFFER, 13990L, null);

        List<ShopUnit> xiaomiList = new ArrayList<>();
        xiaomiList.add(xiaomiUnit);

        ShopUnit androidUnit = new ShopUnit(android.getId(), "Android", now, phones.getId(), ShopUnitType.CATEGORY, 13990L, xiaomiList);

        List<ShopUnit> phonesList = new ArrayList<>();
        phonesList.add(androidUnit);

        ShopUnit tabletsUnit = new ShopUnit(tablets.getId(), "Tablets", now, tech.getId(), ShopUnitType.CATEGORY, null, null);
        ShopUnit phonesUnit = new ShopUnit(phones.getId(), "Phones", now, tech.getId(), ShopUnitType.CATEGORY, 13990L, phonesList);

        List<ShopUnit> techList = new ArrayList<>();
        techList.add(phonesUnit);
        techList.add(tabletsUnit);

        ShopUnit techUnit = new ShopUnit(tech.getId(), "Tech", now, products.getId(), ShopUnitType.CATEGORY, 13990L, techList);

        ShopUnit washingMachinesUnit = new ShopUnit(washingMachines.getId(), "Washing Machines", now, appliances.getId(), ShopUnitType.CATEGORY, null, null);

        List<ShopUnit> appliancesList = new ArrayList<>();
        appliancesList.add(washingMachinesUnit);

        ShopUnit appliancesUnit = new ShopUnit(appliances.getId(), "Appliances", now, products.getId(), ShopUnitType.CATEGORY, null, appliancesList);

        List<ShopUnit> productsList = new ArrayList<>();
        productsList.add(appliancesUnit);
        productsList.add(techUnit);

        ShopUnit productsUnit = new ShopUnit(products.getId(), "Products", now, null, ShopUnitType.CATEGORY, 13990L, productsList);

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

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(apple.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(dishwashes.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(products.getId())) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(productsUnit);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void deleteChildrenTest() throws IOException {

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

        LocalDateTime now = LocalDateTime.now();
        ShopUnit productsUnit = new ShopUnit(products.getId(), "Products", now, null, ShopUnitType.CATEGORY, null, null);

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

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(tech.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(appliances.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(products.getId())) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(productsUnit);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void deleteOfferTest() throws IOException {
        ShopUnitImport apple = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a119"), "Apple", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", apple.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", apple.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", apple.getId(), ShopUnitType.OFFER, 19990L);

        LocalDateTime now = LocalDateTime.now();

        ShopUnit iphone6Unit = new ShopUnit(iphone6.getId(), "IPhone 6", now, apple.getId(), ShopUnitType.OFFER, 29990L, null);
        ShopUnit iphone7Unit = new ShopUnit(iphone7.getId(), "IPhone 7", now, apple.getId(), ShopUnitType.OFFER, 39990L, null);

        List<ShopUnit> iphoneList = new ArrayList<>();
        iphoneList.add(iphone7Unit);
        iphoneList.add(iphone6Unit);

        ShopUnit appleUnit = new ShopUnit(apple.getId(), "Apple", now, null, ShopUnitType.CATEGORY, 34990L, iphoneList);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(apple);
        importList.add(iphone7);
        importList.add(iphone6);
        importList.add(iphone5);

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.deleteSendRequest(iphone5.getId())) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(apple.getId())) {
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(appleUnit);
            assertEquals(json, responseBody);
        }
    }


}
