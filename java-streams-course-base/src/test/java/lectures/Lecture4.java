package lectures;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class Lecture4 {

	@Test
	public void distinct() throws Exception {
		final List<Integer> numbers = ImmutableList.of(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9);
		numbers.stream().distinct().collect(Collectors.toList()).forEach(System.out::print);
	}

	@Test
	public void distinctWithSet() throws Exception {
		final List<Integer> numbers = ImmutableList.of(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9);
		numbers.stream().collect(Collectors.toSet()).forEach(System.out::print);

	}
}
