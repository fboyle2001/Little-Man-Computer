package com.finlay.lmc.computer;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.finlay.lmc.computer.instructions.Instruction;

public class Memory {

	public static final int MAX_MEMORY = 1000000;
	
	private LinkedHashMap<Integer, Integer> mappings;
	
	public Memory() {
		this.mappings = new LinkedHashMap<>(MAX_MEMORY);
		reset();
	}
	
	public void reset() {
		this.mappings.clear();
		
		for(int i = 0; i < MAX_MEMORY; i++) {
			mappings.put(i, 0);
		}
	}
	
	public void set(int address, int value) {
		this.mappings.put(address, value);
	}
	
	public int get(int address) {
		if(!this.mappings.containsKey(address)) {
			return -1;
		}
		
		return this.mappings.get(address);
	}
	
	public Instruction getInstructionAt(int programCounter) {
		int instruction = get(programCounter);
		
		if(instruction == -1) {
			return null;
		}
		
		return Instruction.fromInteger(instruction);
	}
	
	public void print() {
		for(Entry<Integer, Integer> entry: mappings.entrySet()) {
			System.out.println("[" + entry.getKey() + "] = " + entry.getValue());
		}
	}
	
}
