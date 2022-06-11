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
    public void nothingFoundTest() throws IOException {
        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(UUID.randomUUID())) {
            assertEquals(response.getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void simpleOfferTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", null, ShopUnitType.OFFER, 39990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(iphone7.getId())){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
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

    @Test
    public void simpleCategoryTest() throws IOException {

        ShopUnitImport phones = new ShopUnitImport(UUID.randomUUID(), "Phones", null, ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(phones);

        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, LocalDateTime.now());

        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(phones.getId())){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            ShopUnit unit = new ShopUnit.ShopUnitBuilder()
                    .setId(phones.getId())
                    .setName(phones.getName())
                    .setParentId(phones.getParentId())
                    .setType(phones.getType())
                    .setPrice(phones.getPrice())
                    .setDate(suir.getUpdateDate())
                    .build();
            String json = TestUtil.mapper.writeValueAsString(unit);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void mainNodesTest() throws IOException {

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
        ShopUnit xiaomiUnit = new ShopUnit(xiaomi.getId(), "Xiaomi", now, android.getId(), ShopUnitType.OFFER, 13990L, null, -1, -1);

        List<ShopUnit> xiaomiList = new ArrayList<>();
        xiaomiList.add(xiaomiUnit);

        ShopUnit iphone5Unit = new ShopUnit(iphone5.getId(), "IPhone 5", now, apple.getId(), ShopUnitType.OFFER, 19990L, null, -1, -1);
        ShopUnit iphone6Unit = new ShopUnit(iphone6.getId(), "IPhone 6", now, apple.getId(), ShopUnitType.OFFER, 29990L, null, -1, -1);
        ShopUnit iphone7Unit = new ShopUnit(iphone7.getId(), "IPhone 7", now, apple.getId(), ShopUnitType.OFFER, 39990L, null, -1, -1);

        List<ShopUnit> iphoneList = new ArrayList<>();
        iphoneList.add(iphone7Unit);
        iphoneList.add(iphone6Unit);
        iphoneList.add(iphone5Unit);

        ShopUnit androidUnit = new ShopUnit(android.getId(), "Android", now, phones.getId(), ShopUnitType.CATEGORY, 13990L, xiaomiList, -1, -1);
        ShopUnit appleUnit = new ShopUnit(apple.getId(), "Apple", now, phones.getId(), ShopUnitType.CATEGORY, 29990L, iphoneList, -1, -1);

        List<ShopUnit> phonesList = new ArrayList<>();
        phonesList.add(appleUnit);
        phonesList.add(androidUnit);

        ShopUnit tabletsUnit = new ShopUnit(tablets.getId(), "Tablets", now,  tech.getId(), ShopUnitType.CATEGORY, null, null, -1, -1);
        ShopUnit phonesUnit = new ShopUnit(phones.getId(), "Phones", now, tech.getId(), ShopUnitType.CATEGORY, 25990L, phonesList, -1, -1);

        List<ShopUnit> techList = new ArrayList<>();
        techList.add(phonesUnit);
        techList.add(tabletsUnit);

        ShopUnit techUnit = new ShopUnit(tech.getId(), "Tech", now, products.getId(), ShopUnitType.CATEGORY, 25990L, techList, -1, -1);

        ShopUnit boschUnit = new ShopUnit(bosch.getId(), "Bosch", now, dishwashes.getId(), ShopUnitType.OFFER, 199990L, null, -1, -1);

        List<ShopUnit> boschList = new ArrayList<>();
        boschList.add(boschUnit);

        ShopUnit dishwashesUnit = new ShopUnit(dishwashes.getId(), "Dishwashes", now, appliances.getId(), ShopUnitType.CATEGORY, 199990L, boschList, -1, -1);

        ShopUnit washingMachinesUnit = new ShopUnit(washingMachines.getId(), "Washing Machines", now, appliances.getId(), ShopUnitType.CATEGORY, null, null, -1, -1);

        List<ShopUnit> appliancesList = new ArrayList<>();
        appliancesList.add(washingMachinesUnit);
        appliancesList.add(dishwashesUnit);

        ShopUnit appliancesUnit = new ShopUnit(appliances.getId(), "Appliances", now, products.getId(), ShopUnitType.CATEGORY, 199990L, appliancesList, -1, -1);

        List<ShopUnit> productsList = new ArrayList<>();
        productsList.add(appliancesUnit);
        productsList.add(techUnit);

        ShopUnit productsUnit = new ShopUnit(products.getId(), "Products", now, null, ShopUnitType.CATEGORY, 60790L, productsList, -1, -1);

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

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(products.getId())){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(productsUnit);
            assertEquals(json, responseBody);
        }
    }

    @Test
    public void updateDateTest() throws IOException {

        LocalDateTime time1 = LocalDateTime.now().minusDays(1).minusHours(10);
        LocalDateTime time2 = LocalDateTime.now().minusDays(1).minusHours(5);
        LocalDateTime time3 = LocalDateTime.now().minusDays(5);

        ShopUnitImport products = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a111"), "Products", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport appliances = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a112"), "Appliances", products.getId(), ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(products);
        importList.add(appliances);
        ShopUnitImportRequest suir = new ShopUnitImportRequest(importList, time3);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitImport washingMachines = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a113"), "Washing Machines", appliances.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport dishwashes = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a114"), "Dishwashes", appliances.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport bosch = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a115"), "Bosch", dishwashes.getId(), ShopUnitType.OFFER, 199990L);
        ShopUnitImport tech = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a116"), "Tech", products.getId(), ShopUnitType.CATEGORY, null);

        importList = new ArrayList<>();
        importList.add(washingMachines);
        importList.add(dishwashes);
        importList.add(bosch);
        importList.add(tech);
        suir = new ShopUnitImportRequest(importList, LocalDateTime.now().minusDays(3));
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitImport phones = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a117"), "Phones", tech.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport tablets = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a118"), "Tablets", tech.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport apple = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a119"), "Apple", phones.getId(), ShopUnitType.CATEGORY, null);

        importList = new ArrayList<>();
        importList.add(phones);
        importList.add(tablets);
        importList.add(apple);
        suir = new ShopUnitImportRequest(importList, LocalDateTime.now().minusDays(2));
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        ShopUnitImport android = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a120"), "Android", phones.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a121"), "IPhone 7", apple.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a122"), "IPhone 6", apple.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport iphone5 = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a123"), "IPhone 5", apple.getId(), ShopUnitType.OFFER, 19990L);
        ShopUnitImport xiaomi = new ShopUnitImport(UUID.fromString("3dc1c8ff-4ba5-4a06-9bf0-239bd516a124"), "Xiaomi", android.getId(), ShopUnitType.OFFER, 13990L);

        importList = new ArrayList<>();
        importList.add(android);
        importList.add(iphone7);
        importList.add(iphone6);
        importList.add(iphone5);
        importList.add(xiaomi);
        suir = new ShopUnitImportRequest(importList, LocalDateTime.now().minusDays(2).minusHours(5));
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }


        importList = new ArrayList<>();
        importList.add(tablets);
        importList.add(xiaomi);
        suir = new ShopUnitImportRequest(importList, time1);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        importList = new ArrayList<>();
        importList.add(iphone5);
        importList.add(iphone6);
        importList.add(iphone7);
        suir = new ShopUnitImportRequest(importList, time2);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        importList = new ArrayList<>();
        importList.add(washingMachines);
        importList.add(bosch);
        suir = new ShopUnitImportRequest(importList, time3);
        try (CloseableHttpResponse response = TestUtil.importSendRequest(suir)) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }


        ShopUnit xiaomiUnit = new ShopUnit(xiaomi.getId(), "Xiaomi", time1, android.getId(), ShopUnitType.OFFER, 13990L, null, -1, -1);

        List<ShopUnit> xiaomiList = new ArrayList<>();
        xiaomiList.add(xiaomiUnit);

        ShopUnit iphone5Unit = new ShopUnit(iphone5.getId(), "IPhone 5", time2, apple.getId(), ShopUnitType.OFFER, 19990L, null, -1, -1);
        ShopUnit iphone6Unit = new ShopUnit(iphone6.getId(), "IPhone 6", time2, apple.getId(), ShopUnitType.OFFER, 29990L, null, -1, -1);
        ShopUnit iphone7Unit = new ShopUnit(iphone7.getId(), "IPhone 7", time2, apple.getId(), ShopUnitType.OFFER, 39990L, null, -1, -1);

        List<ShopUnit> iphoneList = new ArrayList<>();
        iphoneList.add(iphone5Unit);
        iphoneList.add(iphone6Unit);
        iphoneList.add(iphone7Unit);

        ShopUnit androidUnit = new ShopUnit(android.getId(), "Android", time1, phones.getId(), ShopUnitType.CATEGORY, 13990L, xiaomiList, -1, -1);
        ShopUnit appleUnit = new ShopUnit(apple.getId(), "Apple", time2, phones.getId(), ShopUnitType.CATEGORY, 29990L, iphoneList, -1, -1);

        List<ShopUnit> phonesList = new ArrayList<>();
        phonesList.add(androidUnit);
        phonesList.add(appleUnit);

        ShopUnit tabletsUnit = new ShopUnit(tablets.getId(), "Tablets", time1,  tech.getId(), ShopUnitType.CATEGORY, null, null, -1, -1);
        ShopUnit phonesUnit = new ShopUnit(phones.getId(), "Phones", time2, tech.getId(), ShopUnitType.CATEGORY, 25990L, phonesList, -1, -1);

        List<ShopUnit> techList = new ArrayList<>();
        techList.add(tabletsUnit);
        techList.add(phonesUnit);

        ShopUnit techUnit = new ShopUnit(tech.getId(), "Tech", time2, products.getId(), ShopUnitType.CATEGORY, 25990L, techList, -1, -1);

        ShopUnit boschUnit = new ShopUnit(bosch.getId(), "Bosch", time3, dishwashes.getId(), ShopUnitType.OFFER, 199990L, null, -1, -1);

        List<ShopUnit> boschList = new ArrayList<>();
        boschList.add(boschUnit);

        ShopUnit dishwashesUnit = new ShopUnit(dishwashes.getId(), "Dishwashes", time3, appliances.getId(), ShopUnitType.CATEGORY, 199990L, boschList, -1, -1);

        ShopUnit washingMachinesUnit = new ShopUnit(washingMachines.getId(), "Washing Machines", time3, appliances.getId(), ShopUnitType.CATEGORY, null, null, -1, -1);

        List<ShopUnit> appliancesList = new ArrayList<>();
        appliancesList.add(washingMachinesUnit);
        appliancesList.add(dishwashesUnit);

        ShopUnit appliancesUnit = new ShopUnit(appliances.getId(), "Appliances", time3, products.getId(), ShopUnitType.CATEGORY, 199990L, appliancesList, -1, -1);

        List<ShopUnit> productsList = new ArrayList<>();
        productsList.add(techUnit);
        productsList.add(appliancesUnit);

        ShopUnit productsUnit = new ShopUnit(products.getId(), "Products", time3, null, ShopUnitType.CATEGORY, 60790L, productsList, -1, -1);

        try (CloseableHttpResponse response = TestUtil.nodesSendRequest(products.getId())){
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String json = TestUtil.mapper.writeValueAsString(productsUnit);
            assertEquals(json, responseBody);
        }
    }


}
