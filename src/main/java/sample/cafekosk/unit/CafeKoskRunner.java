package sample.cafekosk.unit;

import sample.cafekosk.unit.beverage.Americano;
import sample.cafekosk.unit.beverage.Latte;

import java.time.LocalDateTime;

public class CafeKoskRunner {

    public static void main(String[] args) {
        CafeKosk cafeKosk = new CafeKosk();
        cafeKosk.add(new Americano());
        System.out.println("아메리카노 추가");
        cafeKosk.add(new Latte());
        System.out.println("라떼 추가");

        int totalPrice = cafeKosk.calculateTotalPrice();
        System.out.println("totalPrice = " + totalPrice);

        cafeKosk.createOrder(LocalDateTime.now());

    }
}
