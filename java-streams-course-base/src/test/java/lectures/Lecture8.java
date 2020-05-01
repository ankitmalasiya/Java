package lectures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.Test;

import beans.Car;
import mockdata.MockData;

public class Lecture8 {

	@Test
	public void simpleGrouping() throws Exception {
		Map<String, List<Car>> collect = MockData.getCars().stream().collect(Collectors.groupingBy(Car::getColor));
		collect.forEach((make, cars) -> {
			System.out.println(make);
			cars.forEach(System.out::print);
		});
	}

	@Test
	public void groupingAndCounting() throws Exception {
		ArrayList<String> names = Lists.newArrayList("John", "John", "Mariam", "Alex", "Mohammado", "Mohammado",
				"Vincent", "Alex", "Alex");

		Map<String, Long> collect = names.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		collect.forEach((name, count) -> System.out.println(name + " > " + count));
	}

}