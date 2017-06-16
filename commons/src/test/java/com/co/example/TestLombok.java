package com.co.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data

@RequiredArgsConstructor(staticName="sssss")
@AllArgsConstructor
public class TestLombok {

	 String name;
	 Integer id;

	void testMethod(@NonNull String info) {

	}

	public static void main(String[] args) {
		TestLombok user = new TestLombok();
		user.setId(11);
		user.setName("zyl");
		System.out.println(user.toString());
		log.debug("");
	}

	public void stream(String args[]) throws IOException {
		@Cleanup
		InputStream in = new FileInputStream(args[0]);
		@Cleanup
		OutputStream out = new FileOutputStream(args[1]);
		byte[] b = new byte[10000];
		while (true) {
			int r = in.read(b);
			if (r == -1)
				break;
			out.write(b, 0, r);
		}
	}
	
	@SneakyThrows(Exception.class)
	public void mathTest(){
		Long data = 0l;
		data = 8/0l;
	}
	
	@Synchronized()
	public void foo() {
	  System.out.println("bar");
	}

}
