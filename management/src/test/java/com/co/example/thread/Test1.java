	package com.co.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test1 {
	public static void main(String[] args) {
		ExecutorService  executor = null;
		for (int k = 0; k <= 2; k++) {
			final int m = k; 
			executor = Executors.newCachedThreadPool();// 启用多线程
			for (int i = 0; i <= 3; i++) {
				final int j = i; // 关键是这一句代码，将 i 转化为 j，这样j 还是final类型的参与线程
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							System.out.println("k="+m+" j="+j);
						} catch (Exception e) {
	
						}
					}
				});
			}
			// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。  
			executor.shutdown();  
		  
		    try {  
		      // 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行  
		      // 设置最长等待10秒  
		    	executor.awaitTermination(10, TimeUnit.SECONDS);  
		    } catch (InterruptedException e) {  
		      //  
		      e.printStackTrace();  
		    }  
			System.out.println("k="+k);
			
		}
	}
}