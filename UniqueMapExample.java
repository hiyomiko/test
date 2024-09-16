package com.example.helloworld;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Sale {
    private String shopId;
    private LocalDate date;
    private double amount;

    // コンストラクタ、ゲッター、セッター
    public Sale(String shopId, LocalDate date, double amount) {
        this.shopId = shopId;
        this.date = date;
        this.amount = amount;
    }

    public String getShopId() {
        return shopId;
    }

    public LocalDate getDate() {
        return date;
    }
}

class Cancellation {
    private String shopId;
    private LocalDate date;
    private double cancelAmount;

    // コンストラクタ、ゲッター、セッター
    public Cancellation(String shopId, LocalDate date, double cancelAmount) {
        this.shopId = shopId;
        this.date = date;
        this.cancelAmount = cancelAmount;
    }

    public String getShopId() {
        return shopId;
    }

    public LocalDate getDate() {
        return date;
    }
}

class Result {
    private String shopId;
    private LocalDate date;

    // コンストラクタ、ゲッター、セッター
    public Result(String shopId, LocalDate date) {
        this.shopId = shopId;
        this.date = date;
    }

    public String getShopId() {
        return shopId;
    }

    public LocalDate getDate() {
        return date;
    }
}

public class UniqueMapExample {
    public static void main(String[] args) {
        List<Sale> listA = Arrays.asList(
        	new Sale("1", LocalDate.of(2024, 1, 3), 2000),
            new Sale("1", LocalDate.of(2024, 1, 1), 1000),
            new Sale("2", LocalDate.of(2024, 1, 2), 1500),
            new Sale("2", LocalDate.of(2024, 1, 2), 2500),
            new Sale("1", LocalDate.of(2024, 1, 1), 1000) // 重複
        );

        List<Cancellation> listB = Arrays.asList(
            new Cancellation("1", LocalDate.of(2024, 1, 1), 200),
            new Cancellation("3", LocalDate.of(2024, 1, 3), 500),
            new Cancellation("1", LocalDate.of(2024, 1, 5), 1000)
        );
        
        Comparator<Result> comparator =
        		  Comparator.comparing(Result::getShopId).thenComparing(Result::getDate);

        // ユニークなショップIDと発生日のペアを作成
        List<Result> uniqueMap = Stream.concat(
                listA.stream().map(sale -> new Result(sale.getShopId(), sale.getDate())),
                listB.stream().map(cancellation -> new Result(cancellation.getShopId(), cancellation.getDate()))
            )
        	.sorted(comparator)
            .collect(Collectors.toList());
        
        HashSet<String> seen = new HashSet<>();
        uniqueMap.removeIf(p->!seen.add(p.getShopId() + p.getDate()));

        // 結果を表示
        System.out.println("重複削除後");
        uniqueMap.forEach(s -> System.out.println("Shop ID: " + s.getShopId() + ", Date: " + s.getDate()));
        
        List<Sale> saleList = listA.stream().filter(s-> s.getShopId().equals("1") && s.getDate().equals(LocalDate.of(2024, 1, 1))).collect(Collectors.toList());
        
        System.out.println("フィルター後");
        saleList.forEach(s -> System.out.println("Shop ID: " + s.getShopId() + ", Date: " + s.getDate()));
    }
}
