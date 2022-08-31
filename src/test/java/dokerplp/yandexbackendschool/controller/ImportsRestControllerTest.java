package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitImport;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ImportsRestControllerTest {
    @Test
    public void simpleOfferTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", null, ShopUnitType.OFFER, 39990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }

    @Test
    public void simpleCategoryTest() throws IOException {
        ShopUnitImport phones = new ShopUnitImport(UUID.randomUUID(), "Phones", null, ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(phones);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }

    @Test
    public void offerIsNotParentTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", UUID.randomUUID(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.randomUUID(), "IPhone 6", iphone7.getId(), ShopUnitType.OFFER, 29990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);
        importList.add(iphone6);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }

    @Test
    public void offerHasNoChildrenTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", UUID.randomUUID(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(iphone7.getParentId(), "IPhone 6", null, ShopUnitType.OFFER, 29990L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);
        importList.add(iphone6);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }


    @Test
    public void categoryTest() throws IOException {

        ShopUnitImport object = new ShopUnitImport(UUID.randomUUID(), "Object", null, ShopUnitType.CATEGORY, null);
        ShopUnitImport phones = new ShopUnitImport(UUID.randomUUID(), "Phones", object.getId(), ShopUnitType.CATEGORY, null);
        ShopUnitImport iphone7 = new ShopUnitImport(UUID.randomUUID(), "IPhone 7", phones.getId(), ShopUnitType.OFFER, 39990L);
        ShopUnitImport iphone6 = new ShopUnitImport(UUID.randomUUID(), "IPhone 6", phones.getId(), ShopUnitType.OFFER, 29990L);
        ShopUnitImport watches = new ShopUnitImport(UUID.randomUUID(), "Watches", object.getId(), ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(watches);
        importList.add(iphone7);
        importList.add(object);
        importList.add(iphone6);
        importList.add(phones);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }

    @Test
    public void wrongCategoryTest() throws IOException {
        ShopUnitImport object = new ShopUnitImport(null, "Object", null, ShopUnitType.CATEGORY, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(object);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

        object.setId(UUID.randomUUID());
        object.setName(null);

        importList.clear();
        importList.add(object);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

        object.setName("Object");
        object.setType(null);

        importList.clear();
        importList.add(object);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

        object.setType(ShopUnitType.CATEGORY);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }

        object.setPrice(10L);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }

    @Test
    public void wrongOfferTest() throws IOException {
        ShopUnitImport object = new ShopUnitImport(UUID.randomUUID(), "Object", null, ShopUnitType.OFFER, null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(object);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

        object.setPrice(-1L);

        importList.clear();
        importList.add(object);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

    }

    @Test
    void duplicatesTest() throws IOException {
        ShopUnitImport object1 = new ShopUnitImport(UUID.randomUUID(), "Object1", null, ShopUnitType.OFFER, 12314324L);
        ShopUnitImport object2 = new ShopUnitImport(object1.getId(), "Object2", null, ShopUnitType.OFFER, 4134342L);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(object1);
        importList.add(object2);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }

        object2.setId(UUID.randomUUID());

        importList.clear();
        importList.add(object1);
        importList.add(object2);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }
}