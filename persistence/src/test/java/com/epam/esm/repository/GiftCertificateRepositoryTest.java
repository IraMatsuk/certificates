//package com.epam.esm.repository;
//
//import com.epam.esm.entity.GiftCertificate;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class GiftCertificateRepositoryTest {
//
//    @Autowired
//    private GiftCertificateRepository certificateRepository;
//
//    @Test
//    void injectedComponentsAreNotNull(){
//        assertThat(certificateRepository).isNotNull();
//    }
//
////    @Autowired
////    public GiftCertificateRepositoryTest(GiftCertificateRepository certificateRepository) {
////        this.certificateRepository = certificateRepository;
////    }
//
//    @Test
//    void testFindAllCertificates() {
//        List<GiftCertificate> giftCertificateList = certificateRepository.findAll();
//        int expected = 3;
//        int actual = giftCertificateList.size();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testFindCertificateById() {
//        Optional<GiftCertificate> giftCertificate = certificateRepository.findById(1L);
//        assertNotNull(giftCertificate);
//    }
//
//}