package com.finlay.lmc.computer;

import java.util.Scanner;

public class LittleManComputer {

	private Memory memory;
	private Scanner scanner;
	private boolean active;
	
	public LittleManComputer(Memory memory) {
		this.memory = memory;
		this.scanner = new Scanner(System.in);
		this.active = false;
	}
	
	public void halt() {
		this.active = false;
		System.out.println();
		System.out.println("HALTED");
	}

	public int requestInput() {
		System.out.println("Input requested: ");
		int integer = 0;
		
		while(true) {
			try {
				integer = Integer.parseInt(scanner.next());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Must enter an integer.");
			}
		}
		
		return integer;
	}
	
	public void start() {
		if(active) {
			return;
		}
		
		this.active = true;
		Processor processor = new Processor(this);
		
		while(active) {
			try {
				processor.executeInstruction();
			} catch (ProcessorException e) {
				System.out.println(e.getMessage());
				this.active = false;
			}
		}
	}
	
	public Memory getMemory() {
		return memory;
	}
	
}
