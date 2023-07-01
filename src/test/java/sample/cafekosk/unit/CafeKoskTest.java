package sample.cafekosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekosk.unit.beverage.Americano;
import sample.cafekosk.unit.beverage.Latte;
import sample.cafekosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CafeKoskTest {

    @Test
    @DisplayName("음료 1개 추가하면 주문 목록에 담긴다.")
    void add() {
        CafeKosk cafeKosk = new CafeKosk();
        cafeKosk.add(new Americano());

        Assertions.assertThat(cafeKosk.getBeverages()).hasSize(1);
        Assertions.assertThat(cafeKosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    @Test
    void addSeveralBeverages() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        cafeKosk.add(americano, 2);

        Assertions.assertThat(cafeKosk.getBeverages().get(0)).isEqualTo(americano);
        Assertions.assertThat(cafeKosk.getBeverages().get(1)).isEqualTo(americano);
        Assertions.assertThat(cafeKosk.getBeverages()).hasSize(2);
        Assertions.assertThat(cafeKosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addZeroBeverages() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();

        Assertions.assertThatThrownBy(() -> cafeKosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }


    @Test
    void remove() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        cafeKosk.add(americano);
        Assertions.assertThat(cafeKosk.getBeverages()).hasSize(1);

        cafeKosk.remove(americano);
        Assertions.assertThat(cafeKosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKosk.add(americano);
        cafeKosk.add(latte);
        Assertions.assertThat(cafeKosk.getBeverages()).hasSize(2);

        cafeKosk.clear();
        Assertions.assertThat(cafeKosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산 할 수 있다.")
    void calculateTotalPrice() {

        //given
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKosk.add(americano);
        cafeKosk.add(latte);

        //when
        int totalPrice = cafeKosk.calculateTotalPrice();

        //then
        Assertions.assertThat(totalPrice).isEqualTo(8500);
    }


    @Test
    void createOrder() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        cafeKosk.add(americano);
        Order order = cafeKosk.createOrder(LocalDateTime.of(2023,7,01,10,0));

        Assertions.assertThat(order.getBeverages()).hasSize(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    @Test
    void createOrderOutsideOpenTime() {
        CafeKosk cafeKosk = new CafeKosk();
        Americano americano = new Americano();
        cafeKosk.add(americano);

        Assertions.assertThatThrownBy(() -> cafeKosk.createOrder(LocalDateTime.of(2023,7,01,9,0))).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}