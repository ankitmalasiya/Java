package lectures;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class Lecture11 {

	@Test
	public void joiningStrings() throws Exception {
		List<String> names = ImmutableList.of("anna", "john", "marcos", "helena", "yasmin");
		StringBuilder output = new StringBuilder();
		for (String string : names) {
			output.append(string).append(',');
		}
		System.out.println(output);
	}

	@Test
	public void joiningStringsWithStream() throws Exception {
		List<String> names = ImmutableList.of("anna", "john", "marcos", "helena", "yasmin");
		String collect = names.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
		System.out.println(collect);
	}
}
