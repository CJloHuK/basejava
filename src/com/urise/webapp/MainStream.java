package com.urise.webapp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {

        System.out.println(minValue(new int[]{1 ,2, 3, 5, 5}));
        System.out.println(minValue(new int[]{1 ,2}));
        System.out.println(oddOrEven(Arrays.asList(1,2,3,4,5)));
    }

    public static int minValue(int[] values) {

       // AtomicInteger result = new AtomicInteger();
        AtomicInteger i = new AtomicInteger(10);
//        Arrays.stream(values).distinct().boxed().sorted(Collections.reverseOrder()).forEach(a -> {
//            result.addAndGet(a * i.get());
//            i.updateAndGet(v -> v * 10);
//        });
        return Arrays.stream(values).distinct().boxed().sorted(Collections.reverseOrder()).reduce((a1, a2) -> {
            int res = a1 + a2 * i.get();
            i.updateAndGet(v -> v * 10);
            return res;
        }).orElse(0);
       // return result.get();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {

        if(integers.stream().reduce(Integer::sum).orElse(0)%2 == 0) {
            return integers.stream().filter(o -> o%2 != 0).collect(Collectors.toList());
        }
        else {
            return integers.stream().filter(o -> o%2 == 0).collect(Collectors.toList());
        }
    }
}
