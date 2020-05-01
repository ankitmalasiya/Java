package lectures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Lecture10 {

	private static final List<ArrayList<String>> arrayListOfNames = Lists.newArrayList(
			Lists.newArrayList("Mariam", "Alex", "Ismail"), Lists.newArrayList("John", "Alesha", "Andre"),
			Lists.newArrayList("Susy", "Ali"));

	@Before
	public void setUp() {
		// System.out.println(arrayListOfNames);
	}

	@Test
	public void withoutFlatMap() throws Exception {
		// [Mariam, Alex, Ismail, John, Alesha, Andre, Susy, Ali]

		List<String> names = Lists.newArrayList();
		for (ArrayList<String> arrayList : arrayListOfNames) {
			for (String string : arrayList) {
				names.add(string);
			}
		}
		System.out.println(names);
	}

	@Test
	public void withFlatMap() throws Exception {
		// [Mariam, Alex, Ismail, John, Alesha, Andre, Susy, Ali]
		List<String> collect = arrayListOfNames.stream().flatMap(a -> a.stream()).collect(Collectors.toList());
		System.out.println(collect);
	}

}
