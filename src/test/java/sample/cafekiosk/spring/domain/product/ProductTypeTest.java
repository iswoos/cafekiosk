package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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