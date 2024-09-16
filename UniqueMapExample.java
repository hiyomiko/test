package com.example.helloworld;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Sale {
    private String shopId;
    private String date;
    private double amount;

    // コンストラクタ、ゲッター、セッター
    public Sale(String shopId, String date, double amount) {
        this.shopId = shopId;
        this.date = date;
        this.amount = amount;
    }

    public String getShopId() {
        return shopId;
    }

    public String getDate() {
        return date;
    }
}

class Cancellation {
    private String shopId;
    private String date;
    private double cancelAmount;

    // コンストラクタ、ゲッター、セッター
    public Cancellation(String shopId, String date, double cancelAmount) {
        this.shopId = shopId;
        this.date = date;
        this.cancelAmount = cancelAmount;
    }

    public String getShopId() {
        return shopId;
    }

    public String getDate() {
        return date;
    }
}

class Result {
    private String shopId;
    private String date;

    // コンストラクタ、ゲッター、セッター
    public Result(String shopId, String date) {
        this.shopId = shopId;
        this.date = date;
    }

    public String getShopId() {
        return shopId;
    }

    public String getDate() {
        return date;
    }
}

public class UniqueMapExample {
    public static void main(String[] args) {
        List<Sale> listA = Arrays.asList(
        	new Sale("1", "2024-01-03", 2000),
            new Sale("1", "2024-01-01", 1000),
            new Sale("2", "2024-01-02", 1500),
            new Sale("2", "2024-01-02", 2500),
            new Sale("1", "2024-01-01", 1000) // 重複
        );

        List<Cancellation> listB = Arrays.asList(
            new Cancellation("1", "2024-01-01", 200),
            new Cancellation("3", "2024-01-03", 500),
            new Cancellation("1", "2024-01-05", 1000)
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
        uniqueMap.forEach(s -> System.out.println("Shop ID: " + s.getShopId() + ", Date: " + s.getDate()));
    }
}
