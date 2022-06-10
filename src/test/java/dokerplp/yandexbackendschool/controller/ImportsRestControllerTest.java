package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitImport;
import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


class ImportsRestControllerTest {
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

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }

    @Test
    public void simpleCategoryTest() throws IOException {
        ShopUnitImport phones = new ShopUnitImport();
        phones.setId(UUID.randomUUID());
        phones.setName("Phones");
        phones.setPrice(null);
        phones.setType(ShopUnitType.CATEGORY);
        phones.setParentId(null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(phones);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 200);
        }
    }

    @Test
    public void offerIsNotParentTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport();
        iphone7.setId(UUID.randomUUID());
        iphone7.setName("IPhone 7");
        iphone7.setPrice(39990L);
        iphone7.setType(ShopUnitType.OFFER);
        iphone7.setParentId(UUID.randomUUID());

        ShopUnitImport iphone6 = new ShopUnitImport();
        iphone6.setId(UUID.randomUUID());
        iphone6.setName("Iphone 6");
        iphone6.setPrice(29990L);
        iphone6.setType(ShopUnitType.OFFER);
        iphone6.setParentId(iphone7.getId());

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);
        importList.add(iphone6);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }

    @Test
    public void offerHasNoChildrenTest() throws IOException {
        ShopUnitImport iphone7 = new ShopUnitImport();
        iphone7.setId(UUID.randomUUID());
        iphone7.setName("IPhone 7");
        iphone7.setPrice(39990L);
        iphone7.setType(ShopUnitType.OFFER);
        iphone7.setParentId(UUID.randomUUID());

        ShopUnitImport iphone6 = new ShopUnitImport();
        iphone6.setId(iphone7.getParentId());
        iphone6.setName("Iphone 6");
        iphone6.setPrice(29990L);
        iphone6.setType(ShopUnitType.OFFER);
        iphone6.setParentId(null);

        List<ShopUnitImport> importList = new ArrayList<>();
        importList.add(iphone7);
        importList.add(iphone6);

        try (CloseableHttpResponse response = TestUtil.importSendRequest(new ShopUnitImportRequest(importList, LocalDateTime.now()))) {
            assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }



    @Test
    public void categoryTest() throws IOException {

        ShopUnitImport object = new ShopUnitImport();
        object.setId(UUID.randomUUID());
        object.setName("Object");
        object.setPrice(null);
        object.setType(ShopUnitType.CATEGORY);
        object.setParentId(null);

        ShopUnitImport phones = new ShopUnitImport();
        phones.setId(UUID.randomUUID());
        phones.setName("Phones");
        phones.setPrice(null);
        phones.setType(ShopUnitType.CATEGORY);
        phones.setParentId(object.getId());

        ShopUnitImport iphone7 = new ShopUnitImport();
        iphone7.setId(UUID.randomUUID());
        iphone7.setName("IPhone 7");
        iphone7.setPrice(39990L);
        iphone7.setType(ShopUnitType.OFFER);
        iphone7.setParentId(phones.getId());

        ShopUnitImport iphone6 = new ShopUnitImport();
        iphone6.setId(UUID.randomUUID());
        iphone6.setName("Iphone 6");
        iphone6.setPrice(29990L);
        iphone6.setType(ShopUnitType.OFFER);
        iphone6.setParentId(phones.getId());

        ShopUnitImport watches = new ShopUnitImport();
        watches.setId(UUID.randomUUID());
        watches.setName("Watches");
        watches.setPrice(null);
        watches.setType(ShopUnitType.CATEGORY);
        watches.setParentId(object.getId());

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
        ShopUnitImport object = new ShopUnitImport();
        object.setId(null);
        object.setName("Object");
        object.setPrice(null);
        object.setType(ShopUnitType.CATEGORY);
        object.setParentId(null);

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
    }

    @Test
    public void wrongOfferTest() throws IOException {
        ShopUnitImport object = new ShopUnitImport();
        object.setId(UUID.randomUUID());
        object.setName("Object");
        object.setPrice(null);
        object.setType(ShopUnitType.OFFER);
        object.setParentId(null);

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
}