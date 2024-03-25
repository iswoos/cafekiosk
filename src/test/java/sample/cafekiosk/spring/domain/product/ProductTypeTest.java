package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    // 아래에 있는 containsStockType, containsStockType2 테스트를 이 1개 테스트로 통합할 수 있음
    // 하지만, 이런 구조의 테스트 코드는 무엇을 테스트하고자 하는지 명확하게 파악하기 어렵다 (if와 for문같은 별도의 논리구조가 들어가기 때문에)
    // 그러니 이런 구조는 지양하는 것이 좋다. 아래에 있는 containsStockType, containsStockType2 특정 케이스를 나눠서 적는 게 좋다.
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeEx() {
        // given
        ProductType[] productTypes = ProductType.values();
        for (ProductType productType : productTypes) {
            if (productType == ProductType.HANDMADE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // then
                assertThat(result).isFalse();
            }

            if (productType == ProductType.BAKERY || productType == ProductType.BOTTLE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // when
                assertThat(result).isTrue();
            }
        }
    }

    // 아래 코드는 모든 경우의 수에 대해 테스트하는 것이나, 보기에 좋지가 않다. @ParameterizedTest를 사용한 버전으로 수정한 버전을 아래에 두겠다.
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containStockType3() {
        // given
        ProductType givenType1 = ProductType.HANDMADE;
        ProductType givenType2 = ProductType.BOTTLE;
        ProductType givenType3 = ProductType.BAKERY;

        // when
        boolean result1 = ProductType.containsStockType(givenType1);
        boolean result2 = ProductType.containsStockType(givenType2);
        boolean result3 = ProductType.containsStockType(givenType3);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    // 해당 테스트로 다양한 경우의 수에 대해 테스트해볼 수 있다.
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"}) // 소스를 주는 방식은 많으나, 예제에서는 우선 Csv형식으로 하였음
    @ParameterizedTest
    void containStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    // given절에 속하기 때문에 아래 코드의 위에 명시
    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }

    // 소스주는 방식 변환한 코드
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containStockType5(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }
}