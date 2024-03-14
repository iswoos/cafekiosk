package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;

// 궁금사항 아래 테스트 2개를 동시에 돌릴 때, SpringBootTest로 하면 1개가 실패하고, DataJpaTest로 하면 모두 성공한다
// 이유
// DataJpaTest를 들어가보면, @Transactional이 선언되어 있다. 이로 인해 테스트가 종료되면 Rollback을 시켜준다.
// 그에 비해 SpringBootTest는 @Transactional이 없다. 그래서 Rollback이 없다.

// Spring에서 통합테스를 위해 제공하는 어노테이션
//@SpringBootTest
// test를 돌릴 때 사용할 Profiles를 지정 (application.yml의 on-profile:test로 설정된 값을 따름)
@ActiveProfiles("test")
// JPA관련된 Bean들만 주입을 해줌으로써, SpringbootTest비해 속도가 빠름
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));


        // then
        assertThat(products).hasSize(2)
                // 검증하고자 하는 필드만 추출할 수 있는 기능
                .extracting("productNumber", "name", "sellingStatus")
                // 순서상관없이 포함하고 있는지 검증
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));


        // then
        assertThat(products).hasSize(2)
                // 검증하고자 하는 필드만 추출할 수 있는 기능
                .extracting("productNumber", "name", "sellingStatus")
                // 순서상관없이 포함하고 있는지 검증
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );

    }

}