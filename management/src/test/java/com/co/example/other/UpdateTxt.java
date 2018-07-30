package com.co.example.other;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Files;

public class UpdateTxt {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		File file = new File("D:/Workspaces2/package/mWebConfig/src/main/resources/UpdateTxt.txt");
		try {
			String str = Files.toString(file, StandardCharsets.UTF_8);
			str = str.replace("INSERT INTO `t_ztree` VALUES ", "tx.executeSql(\"INSERT OR IGNORE  INTO t_chat VALUES ");
			str = str.replace(");", ")\");");
			str = str.replace("'", "@");
			str = str.replace("\"", "'");
			str = str.replace("@", "\"");
			Files.write(str.getBytes(), file);
			System.out.println("over...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
