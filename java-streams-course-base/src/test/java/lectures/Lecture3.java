package lectures;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class Lecture3 {

	@Test
	public void min() throws Exception {
		final List<Integer> numbers = ImmutableList.of(1, 2, 3, 100, 23, 93, 99);

		Integer integer = numbers.stream().min(Comparator.naturalOrder()).get();

		assertThat(integer).isEqualTo(1);
		System.out.println("Min is " + integer);
	}

	@Test
	public void max() throws Exception {
		final List<Integer> numbers = ImmutableList.of(1, 2, 3, 100, 23, 93, 99);
		Integer integer = numbers.stream().max(Comparator.naturalOrder()).get();
		System.out.println("Max is " + integer);
	}
}
