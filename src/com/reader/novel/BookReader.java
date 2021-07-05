package com.reader.novel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BookReader implements Runnable {

	private Thread thread;
	private boolean pause = true;
	
	Window window;
	NovelFileReader nfr;
	
	Runtime runtime;
	Process process;
	
	public BookReader() {
		window = new Window("Java Book Reader", 900, 700, this);
		runtime = Runtime.getRuntime();
	}
	
	public void CopyNovel(String command) {
		try {
			process = runtime.exec(command);
			
			Start();
			
			process.waitFor();
		} 
		catch (Exception ee) {
			process.getErrorStream();
			ee.printStackTrace();
			window.AlertMessage("Error Copying Novel");
		}
	}
	
	public synchronized void Start() {
		pause = false;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void Stop() {
		try {
			pause = true;
			thread.join();
		}
		catch(Exception ee) {
			window.AlertMessage("Error Stopping Thread! Force Exit");
			ee.printStackTrace();
			System.exit(0);
		}
	}
	
	public void run() {
		if(!pause) {
			boolean condition = true;
			while(condition) {
				try {
					BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null; 
					while((line = input.readLine()) != null) {
						System.out.println(line);
						if(line.equals("END")) {
							System.out.println("MEOWWW");
							condition = false;
						}
					}
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
			}
		}
	}
}
