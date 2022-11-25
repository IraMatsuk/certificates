//package com.epam.esm.repository;
//
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class GiftCertificateRepositoryTest {
//    private final GiftCertificate giftCertificateOne = GiftCertificate.builder()
//                .id(1L)
//                .name("certificateOne")
//                .description("descriptionOne")
//                .price(BigDecimal.valueOf(1000.50))
//                .duration(10)
//                .createDate(LocalDateTime.of(2022, 11, 20, 8, 44, 42, 8))
//                .lastUpdateDate(LocalDateTime.of(2022, 11, 20, 8, 44, 42, 8))
//                .tags(Set.of(new Tag("tag")))
//                .build();
//    private final GiftCertificate giftCertificateTwo = GiftCertificate.builder()
//                .id(2L)
//                .name("two certificate")
//                .description("two description")
//                .price(BigDecimal.valueOf(2000.50))
//                .duration(20)
//                .createDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
//                .lastUpdateDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
//                .tags(Set.of(new Tag("twoTag")))
//                .build();
//
//    private final GiftCertificate giftCertificateThree = GiftCertificate.builder()
//                .id(3L)
//                .name("one more certificate")
//                .description("more description")
//                .price(BigDecimal.valueOf(3000.50))
//                .duration(30)
//                .createDate(LocalDateTime.of(2022, 11, 23, 8, 44, 42, 8))
//                .lastUpdateDate(LocalDateTime.of(2022, 11, 23, 8, 44, 42, 8))
//                .tags(Set.of(new Tag("fiveTag")))
//                .build();
//    private GiftCertificateRepository certificateRepository;
//
//    @Autowired
//    public GiftCertificateRepositoryTest(GiftCertificateRepository certificateRepository) {
//        this.certificateRepository = certificateRepository;
//    }
//
//    @Test
//    void testFindAllCertificates() {
//        List<GiftCertificate> giftCertificateList = certificateRepository.findAll();
//        int expected = 3;
//        int actual = giftCertificateList.size();
//        assertEquals(expected, actual);
//    }
//
//
//}