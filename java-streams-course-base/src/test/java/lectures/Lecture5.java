package lectures;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import beans.Car;
import beans.Person;
import beans.PersonDTO;
import mockdata.MockData;

public class Lecture5 {

	@Test
	public void understandingFilter() throws Exception {

		Predicate<Car> predCar = car -> car.getPrice() < 10000;
		ImmutableList<Car> cars = MockData.getCars();

		// cars.stream().filter(predCar).collect(Collectors.toList()).forEach(System.out::print);

	}

	@Test
	public void ourFirstMapping() throws Exception {
		// transform from one data type to another
		List<Person> people = MockData.getPeople();

		Function<Person, PersonDTO> mapPersonDtofromPerson = person -> new PersonDTO(person.getId(),
				person.getFirstName(), person.getAge());

		List<PersonDTO> collect = people.stream().map(mapPersonDtofromPerson).collect(Collectors.toList());

		collect.forEach(System.out::print);
	}

	@Test
	public void averageCarPrice() throws Exception {
		// calculate average of car prices
		ImmutableList<Car> cars = MockData.getCars();
		double asDouble = cars.stream().mapToDouble(car -> car.getPrice()).average().getAsDouble();
		System.out.println(asDouble);
	}

	@Test
	public void test() throws Exception {

	}
}
