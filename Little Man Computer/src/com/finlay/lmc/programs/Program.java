package com.finlay.lmc.programs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.finlay.lmc.computer.LittleManComputer;
import com.finlay.lmc.computer.instructions.InstructionParser;

import lib.finlay.core.io.SerialFileHandler;

public class Program {

	private SerialFileHandler handler;
	private boolean dumpMemory;
	
	public Program(File file) {
		this(file, false);
	}
	
	public Program(File file, boolean dumpMemory) {
		try {
			this.handler = new SerialFileHandler(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.dumpMemory = dumpMemory;
	}
	
	public void setDumpMemory(boolean dumpMemory) {
		this.dumpMemory = dumpMemory;
	}
	
	public void execute() throws IOException {
		List<String> contents = handler.getContents(true);
		List<String> ready = new ArrayList<>(contents.size());
		
		contents.forEach(line -> {
			ready.add(line.toUpperCase().trim().replaceAll("\t+", " ").replaceAll(" +", " "));
		});
		
		//ready.forEach(System.out::println);
		
		LittleManComputer computer = new LittleManComputer(InstructionParser.parse(ready));
		computer.start();
		
		if(dumpMemory) {
			computer.getMemory().print();
		}
	}
	
}
