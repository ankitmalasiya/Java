package lectures;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import beans.Person;
import mockdata.MockData;

public class Lecture2 {

	@Test
	public void range() throws Exception {

//		IntStream.range(1, 10).forEach(System.out::print);
	}

	@Test
	public void rangeIteratingLists() throws Exception {
		List<Person> people = MockData.getPeople();
		people.forEach(System.out::println);
	}

	@Test
	public void intStreamIterate() throws Exception {
		IntStream.iterate(0, operand -> operand + 1).filter(number -> number % 2 == 0).limit(20)
				.forEach(System.out::println);
	}
}
