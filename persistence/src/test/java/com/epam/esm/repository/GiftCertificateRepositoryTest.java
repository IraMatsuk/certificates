//package com.epam.esm.repository;
//
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.repository.GiftCertificateRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//class GiftCertificateRepositoryTest {
//
//    @Autowired
//    private GiftCertificateRepository certificateRepository;
//
//    private GiftCertificate certificate;
//
//    @BeforeEach
//    public void setUp() {
//        Tag tag = new Tag("fiveTag");
//        ReflectionTestUtils.setField(tag,"id",5L);
//
//        certificate = GiftCertificate.builder()
//                .id(4L)
//                .name("certificate for update")
//                .description("description for update")
//                .price(BigDecimal.valueOf(4000.50))
//                .duration(20)
//                .createDate(LocalDateTime.of(2022, 11, 25, 8, 44, 42, 8))
//                .lastUpdateDate(LocalDateTime.of(2022, 11, 25, 8, 44, 42, 8))
//                .tags(Set.of(tag))
//                .build();
//    }
//
//    @Test
//    void injectedComponentsAreNotNull(){
//        assertThat(certificateRepository).isNotNull();
//    }
//
//    @Test
//    void testFindCertificateById() {
//        Optional<GiftCertificate> giftCertificate = certificateRepository.findById(1L);
//        assertNotNull(giftCertificate);
//    }
//
//    @Test
//    void testFindAllCertificates() {
//        List<GiftCertificate> giftCertificateList = certificateRepository.findAll();
//        int expected = 2;
//        int actual = giftCertificateList.size();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testFindByTagName() {
//        Set<GiftCertificate> giftCertificateList = certificateRepository.findByTagNames(List.of("fourTag"), 1);
//        int expected = 2;
//        int actual = giftCertificateList.size();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void givenCertificateObject_whenSave_thanReturnSavedCertificate() {
//        // given - precondition or setup
//        GiftCertificate certificate = GiftCertificate.builder()
//                .id(3L)
//                .name("new certificate")
//                .description("descriptionTwo")
//                .price(BigDecimal.valueOf(2000.50))
//                .duration(30)
//                .createDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
//                .lastUpdateDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
//                .tags(Set.of(new Tag("fourTag")))
//                .build();
//
//        // when - action or the behaviour that we are going test
//        GiftCertificate createdCertificate = certificateRepository.save(certificate);
//
//        // then - verify the output
//        assertThat(createdCertificate).isNotNull();
//        assertThat(createdCertificate.getId()).isEqualTo(3L);
//    }
//
//    @Test
//    public void givenCertificateObject_whenUpdateCertificate_thenReturnUpdatedCertificate(){
//        // given - precondition or setup
//        GiftCertificate createdCertificate = certificateRepository.save(certificate);
//
//        // when -  action or the behaviour that we are going test
//        GiftCertificate savedCertificate = certificateRepository.findById(createdCertificate.getId()).get();
//        savedCertificate.setName("updated Certificate");
//        savedCertificate.setDescription("updated description");
//        GiftCertificate updatedCertificate =  certificateRepository.save(savedCertificate);
//
//        // then - verify the output
//        assertThat(updatedCertificate.getName()).isEqualTo("updated Certificate");
//        assertThat(updatedCertificate.getDescription()).isEqualTo("updated description");
//    }
//
//    @Test
//    public void givenCertificateObject_whenDelete_thenRemoveCertificate(){
//        // given - precondition or setup
//        GiftCertificate createdCertificate = certificateRepository.save(certificate);
//
//        // when -  action or the behaviour that we are going test
//        certificateRepository.deleteById(createdCertificate.getId());
//        Optional<GiftCertificate> employeeOptional = certificateRepository.findById(certificate.getId());
//
//        // then - verify the output
//        assertThat(employeeOptional).isEmpty();
//    }
//}