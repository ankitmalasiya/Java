package lectures;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.junit.Test;

import beans.Car;
import mockdata.MockData;

public class Lecture7 {

	@Test
	public void count() throws Exception {
		List<Car> cars = MockData.getCars();
		long count = cars.stream().filter(car -> car.getColor().equalsIgnoreCase("red")).count();

		System.out.println("Count of Cars is " + count);

	}

	@Test
	public void min() throws Exception {
		List<Car> cars = MockData.getCars();
		double min = cars.stream().mapToDouble(car -> car.getPrice()).min().orElse(0);

		System.out.println("Minimum Price of Car is " + min);
	}

	@Test
	public void max() throws Exception {
		List<Car> cars = MockData.getCars();
		double max = cars.stream().mapToDouble(car -> car.getPrice()).max().orElse(0);

		System.out.println("Maximum Price of Car is " + max);
	}

	@Test
	public void average() throws Exception {
		List<Car> cars = MockData.getCars();
		double average = cars.stream().mapToDouble(car -> car.getPrice()).average().orElse(0);
		System.out.println("Average price is " + average);
	}

	@Test
	public void sum() throws Exception {
		List<Car> cars = MockData.getCars();
		double sum = cars.stream().mapToDouble(Car::getPrice).sum();
		BigDecimal bigDecimalSum = BigDecimal.valueOf(sum);
		System.out.println(sum);
		System.out.println(bigDecimalSum);

	}

	@Test
	public void statistics() throws Exception {
		List<Car> cars = MockData.getCars();
		DoubleSummaryStatistics statistics = cars.stream().mapToDouble(Car::getPrice).summaryStatistics();
		System.out.println(statistics);
		System.out.println(statistics.getAverage());
		System.out.println(statistics.getCount());
		System.out.println(statistics.getMax());
		System.out.println(statistics.getMin());
		System.out.println(statistics.getSum());
	}

}