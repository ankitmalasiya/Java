package com.java8Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExample {

	public static void main(String[] args) {

		// 1. IntStream to print using forEach terminal operation
		IntStream.range(1, 10).forEach(System.out::print);

		// 2. IntStream to print using lambda and skip() element intermediate operation
		IntStream.range(1, 10).skip(5).forEach(a -> System.out.println(a));

		// 3. IntStream with SUM
		System.out.println(IntStream.range(1, 10).sum());

		// 4. Stream.Of method to create a Stream, Sort it and Find First element and
		// print
		Stream.of("Anvi", "Akanksha", "Ankit").sorted().findFirst().ifPresent(System.out::print);

		// 5. Stream from Array, Sort, Filter and Print
		String[] names = { "Ankit", "Akanksha", "Vidu", "Anvi", "Papa", "Dada", "Nana", "Akoji" };
		Arrays.stream(names).sorted().filter(x -> x.startsWith("A")).forEach(y -> System.out.println(y));

		// 6. Average of squares of int array
		int[] numbers = { 1, 3, 5, 7, 9 };
		Arrays.stream(numbers).map(x -> x * x).average().ifPresent(System.out::print);

		// 7. Stream from List, sort, filter and print
		List<String> people = Arrays.asList("Ankit", "Akanksha", "Vidu", "Anvi", "Papa", "Dada", "Nana", "Akoji");
		people.stream().sorted().map(String::toUpperCase).filter(a -> a.startsWith("V")).forEach(System.out::print);
	}

}
