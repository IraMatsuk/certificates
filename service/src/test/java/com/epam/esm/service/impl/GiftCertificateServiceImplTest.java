package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository certificateRepositoryMock;
    @Mock
    private TagRepository tagRepositoryMock;
    @Mock
    private GiftCertificateMapper certificateMapperMock;

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    @InjectMocks
    private Tag tag;

    private GiftCertificate giftCertificate;

    @BeforeEach
    void setUp() {
        tag = new Tag("tag");
        ReflectionTestUtils.setField(tag,"id",1L);
        giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("gift certificate")
                .description("description")
                .price(BigDecimal.valueOf(1000.50))
                .duration(10)
                .createDate(LocalDateTime.of(2022, 11, 20, 8, 44, 42, 8))
                .lastUpdateDate(LocalDateTime.of(2022, 11, 20, 8, 44, 42, 8))
                .tags(Set.of(tag))
                .build();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 15})
    void findAll(int page, int size) {
        ReflectionTestUtils.setField(certificateService, "maxResultAmount", 5);
        when(certificateRepositoryMock.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(getCertificateList(1)));
        when(certificateMapperMock.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());
        int expected = 1;
        List<GiftCertificateDto> certificates = certificateService.findAll(page, size);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 10})
    void findById(long id) {
        when(certificateRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new GiftCertificate()));
        when(certificateMapperMock.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());
        Optional<GiftCertificateDto> giftCertificateDto = certificateService.findById(id);
        assertTrue(giftCertificateDto.isPresent());
    }

    @Test
    void findByNonExistId() {
        Long id = 5L;
        when(certificateRepositoryMock.findById(id)).thenReturn(Optional.empty());
        Optional<GiftCertificateDto> giftCertificateDto = certificateService.findById(id);
        assertTrue(giftCertificateDto.isEmpty());
    }

    @DisplayName("JUnit test for getGiftCertificateById method")
    @Test
    void givenCertificateId_whenGetCertificateById_thenReturnCertificateObject() {
        // given - precondition or setup
        given(certificateRepositoryMock.findById(1L)).willReturn(Optional.of(giftCertificate));
        // when -  action or the behaviour that we are going test
        when(certificateMapperMock.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());
        Optional<GiftCertificateDto> savedCertificate = certificateService.findById(1L);
        assertThat(savedCertificate).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void create(GiftCertificateDto certificate) {
        when(certificateMapperMock.mapToEntity(any(GiftCertificateDto.class))).thenReturn(new GiftCertificate());
        when(certificateRepositoryMock.save(any(GiftCertificate.class))).thenReturn(any(GiftCertificate.class));
        GiftCertificateDto actual = certificateService.create(certificate);
        assertThat(actual);
    }

    @Test
    void givenTagName_whenFindCertificateByTagName_thenReturnCertificate() {
        Tag tag1 = new Tag("tagOne");
        Tag tag2 = new Tag("tagTwo");
        ReflectionTestUtils.setField(tag1,"id",2L);
        ReflectionTestUtils.setField(tag2,"id",3L);

        GiftCertificate certificate1 = GiftCertificate.builder()
                .id(2L)
                .name("new certificate")
                .description("descriptionTwo")
                .price(BigDecimal.valueOf(2000.50))
                .duration(30)
                .createDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
                .lastUpdateDate(LocalDateTime.of(2022, 11, 21, 8, 44, 42, 8))
                .tags(Set.of(tag1, tag2))
                .build();
        given(certificateRepositoryMock.findByTagNames(List.of(tag1.getName(), tag2.getName()), 2)).willReturn(Set.of(giftCertificate, certificate1));
        Set<GiftCertificateDto> giftCertificateList = certificateService.findByTagNames(List.of(tag1.getName(), tag2.getName()));
        assertThat(giftCertificateList).isNotEmpty();
        assertThat(giftCertificateList.size()).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void update(GiftCertificateDto certificate) {

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5, 31})
    void delete(long id) {
        when(certificateRepositoryMock.existsById(anyLong())).thenReturn(true);
        doNothing().when(certificateRepositoryMock).deleteById(anyLong());

        boolean actual = certificateService.delete(id);
        assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void sortCertificatesBySeveralParameters(int page, int size, List<String> sortTypes) {
        ReflectionTestUtils.setField(certificateService, "size", 5);
        when(certificateRepositoryMock.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(getCertificateList(1)));
        when(certificateMapperMock.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());
        int expected = 1;
        List<GiftCertificateDto> certificates = certificateService.sortCertificatesBySeveralParameters(page, size, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void updateCertificate() {

        when(certificateRepositoryMock.findById(1L)).thenReturn(Optional.of(giftCertificate));
        when(certificateMapperMock.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());
        Optional<GiftCertificateDto> giftCertificateDto = certificateService.findById(giftCertificate.getId());
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setId(1L);
        certificateDto.setName("Some");
        certificateDto.setDescription("Something");
        certificateDto.setPrice(BigDecimal.valueOf(130));
        certificateDto.setDuration(60);
        certificateDto.setTags(new LinkedHashSet<>() {{
            add(TagDto.builder()
                    .id(1L)
                    .name("cars")
                    .build());
        }});
        certificateService.update(certificateDto);
        assertNotSame(giftCertificateDto, certificateService.update(certificateDto));
        verify(certificateRepositoryMock, times(3)).findById(anyLong());
    }

    private List<GiftCertificate> getCertificateList(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(GiftCertificate::new)
                .collect(Collectors.toList());
    }

    private static Object[][] provideCertificateData() {
        return new Object[][]{
                {GiftCertificateDto.builder()
                        .id(4L)
                        .name("gift")
                        .description("discount")
                        .price(BigDecimal.valueOf(210))
                        .duration(20)
                        .tags(new LinkedHashSet<TagDto>() {{
                            add(TagDto.builder()
                                    .id(1L)
                                    .name("tagName")
                                    .build());
                        }}).build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {3, new ArrayList<String>() {
                    {
                        add("name");
                        add("desc");
                        add("date");
                        add("desc");
                    }
                }},
                {5, new ArrayList<String>() {
                    {
                        add("date");
                        add("asc");
                    }
                }}
        };
    }
}