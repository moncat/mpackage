package com.co.example.java8.t2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class T2 {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(names, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return b.compareTo(a);
		    }
		});

		
		Collections.sort(names, (String a, String b) -> {
		    return b.compareTo(a);
		});

		Collections.sort(names, (String a, String b) -> b.compareTo(a));
		
		Collections.sort(names, (a, b) -> b.compareTo(a));
	}
	
	
	@FunctionalInterface
	interface Converter<F, T> {
	    T convert(F from);
	}
	
	private void test3() {
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted);    // 123

	}

	private void test4() {
		Converter<String, Integer> converter = Integer::valueOf;
		Integer converted = converter.convert("123");
		System.out.println(converted);   // 123

	}
	//******************************
	
	class Person {
	    String firstName;
	    String lastName;

	    Person() {}

	    Person(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }
	}

	interface PersonFactory<P extends Person> {
	    P create(String firstName, String lastName);
	}

	private void test5() {
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Peter", "Parker");

	}
	//******************************
	private void test6() {
		int num = 1;
		Converter<Integer, String> stringConverter =
		        (from) -> String.valueOf(from + num);

		stringConverter.convert(2);     // 3
		//num = 3;
	}
	
	//**************
	private void test7() {
		Predicate<String> predicate = (s) -> s.length() > 0;

		predicate.test("foo");              // true
		predicate.negate().test("foo");     // false

		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull = Objects::isNull;

		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();

		
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		backToString.apply("123");     // "123"


		Supplier<Person> personSupplier = Person::new;
		Person person = personSupplier.get();   // new Person


		Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new Person("Luke", "Skywalker"));


		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");

		comparator.compare(p1, p2);             // > 0
		comparator.reversed().compare(p1, p2);  // < 0



		Optional<String> optional = Optional.of("bam");

		optional.isPresent();           // true
		optional.get();                 // "bam"
		optional.orElse("fallback");    // "bam"

		optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"


		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");

		stringCollection
	    .stream()
	    .sorted()
	    .filter((s) -> s.startsWith("a"))
	    .forEach(System.out::println);

	//  "aaa1","aaa2"

		stringCollection
	    .stream()
	    .map(String::toUpperCase)
	    .sorted((a, b) -> b.compareTo(a))
	    .forEach(System.out::println);

	// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"

		boolean anyStartsWithA = 
			    stringCollection
			        .stream()
			        .anyMatch((s) -> s.startsWith("a"));

			System.out.println(anyStartsWithA);      // true

			boolean allStartsWithA = 
			    stringCollection
			        .stream()
			        .allMatch((s) -> s.startsWith("a"));

			System.out.println(allStartsWithA);      // false

			boolean noneStartsWithZ = 
			    stringCollection
			        .stream()
			        .noneMatch((s) -> s.startsWith("z"));

			System.out.println(noneStartsWithZ);      // true


			
			long startsWithB = 
				    stringCollection
				        .stream()
				        .filter((s) -> s.startsWith("b"))
				        .count();
				System.out.println(startsWithB);    // 3


				Optional<String> reduced =
					    stringCollection
					        .stream()
					        .sorted()
					        .reduce((s1, s2) -> s1 + "#" + s2);

					reduced.ifPresent(System.out::println);
					// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

					
					
				int max = 1000000;
				List<String> values = new ArrayList<>(max);
				for (int i = 0; i < max; i++) {
				    UUID uuid = UUID.randomUUID();
				    values.add(uuid.toString());
				}

				
				long t0 = System.nanoTime();
				long count = values.stream().sorted().count();
				System.out.println(count);
				long t1 = System.nanoTime();
				long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
				System.out.println(String.format("sequential sort took: %d ms", millis));

		
				Map<Integer, String> map = new HashMap<>();

				for (int i = 0; i < 10; i++) {
				    map.putIfAbsent(i, "val" + i);
				}

				
				map.computeIfPresent(3, (num, val) -> val + num);
				map.get(3);             // val33
				map.computeIfPresent(9, (num, val) -> null);
				map.containsKey(9);     // false
				map.computeIfAbsent(23, num -> "val" + num);
				map.containsKey(23);    // true
				map.computeIfAbsent(3, num -> "bam");
				map.get(3);

				//...

	}
	


	
	
	
	
	
	
	
	
}
