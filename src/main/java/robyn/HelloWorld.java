package robyn;

import java.util.Arrays;

public class HelloWorld {
    public static void main(String[] args) {
        Arrays.stream(args).forEach(System.out::println);
        System.out.println("Hello World!");
    }
}
