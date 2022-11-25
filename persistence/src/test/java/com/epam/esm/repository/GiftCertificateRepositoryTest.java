//package com.epam.esm.repository;
//
//import com.epam.esm.entity.GiftCertificate;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = {GiftCertificateRepository.class})
//class GiftCertificateRepositoryTest {
//
//    @Autowired
//    private GiftCertificateRepository giftCertificateRepository;
//
//    @ParameterizedTest
//    @MethodSource("provideCreateGiftCertificateData")
//    void create(GiftCertificate giftCertificate) {
//        long expected = 3;
//        long actual = giftCertificateRepository.save(giftCertificate).getId();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    private static Object[][] provideCreateGiftCertificateData() {
//        return new Object[][]{
//                {GiftCertificate.builder()
//                        .name("Certificate From Test")
//                        .description("get a discount")
//                        .price(BigDecimal.valueOf(21000.00))
//                        .duration(21)
//                        .build()}
//        };
//    }
//
//}