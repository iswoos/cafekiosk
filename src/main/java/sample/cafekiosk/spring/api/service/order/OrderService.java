package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        List<Product> products = findProductsBy(productNumbers);

        //Order
        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        //Product
        // 중복제거된 Products가 조회됨
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

//        아래 코드와 동일하게 해석되는 것임
//        Map<String, Product> productMap = products.stream()
//                .collect(Collectors.toMap(Product::getProductNumber, p -> p));
        // 프로덕트 넘버를 기반으로 프로덕트를 찾을 수 있는 맵을 만듦
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(product -> product.getProductNumber(), p -> p));


//        해당 코드도 아래것과 동일
//        List<Product> duplicateProducts = productNumbers.stream()
//                .map(productMap::get)
//                .collect(Collectors.toList());
        // 프로덕트 넘버를 순회하면서 프로덕트 객체를 조회함
        return productNumbers.stream()
                .map(productNumber -> productMap.get(productNumber))
                .collect(Collectors.toList());
    }
}
