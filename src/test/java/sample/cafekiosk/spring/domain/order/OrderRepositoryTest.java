package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.order.OrderStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

// 자동 롤백해주니깐 @AfterEach 사용해서 삭제하지 않아도 될 듯
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("특정 날에 해당하는 결제완료된 주문목록을 가져온다.")
    @Test
    void findOrdersByOnDate() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 3, 23, 0,0,0);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 22, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 23, 00, 00, 00), products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 23, 23, 59, 59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 24, 00, 00, 00), products);
        List<Order> orderList = List.of(order1, order2, order3, order4);
        orderRepository.saveAll(orderList);

        // when
        List<Order> orders = orderRepository.findOrdersBy(dateTime, dateTime.plusDays(1), PAYMENT_COMPLETED);

        // then
        // extracting에서는 인수가 1개일 경우 리스트로 감싸지않음(당연함), 그런 이유로 PAYMENT_COMPLETED를 할때 tuple로 습관적으로 감싸면 안된다.
        assertThat(orders).hasSize(2)
                .extracting("orderStatus")
                .containsExactlyInAnyOrder(
                        PAYMENT_COMPLETED,
                        PAYMENT_COMPLETED
                );
    }

    public Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .build();
    }
    public Order createPaymentCompletedOrder(LocalDateTime localDateTime, List<Product> products) {
        return Order.builder()
                .orderStatus(PAYMENT_COMPLETED)
                .registeredDateTime(localDateTime)
                .products(products)
                .build();
    }
}