package com.finlay.lmc.computer.instructions;

import static com.finlay.lmc.computer.Memory.MAX_MEMORY;

public class Instruction {

	public static Instruction fromInteger(int instruction) {
		int opcode = instruction / MAX_MEMORY;
		int address = instruction % MAX_MEMORY;
		
		OpCode realOp = OpCode.fromLong(opcode);
		return new Instruction(null, realOp, address);
	}
	
	private final String label;
	private final OpCode opcode;
	private final int address;
	
	public Instruction(String label, OpCode opcode, int address) {
		this.label = label;
		this.opcode = opcode;
		this.address = address;
	}
	
	public int getAddress() {
		return address;
	}
	
	public String getLabel() {
		return label;
	}
	
	public OpCode getOpcode() {
		return opcode;
	}
	
	public int toInteger() {
		return opcode.getCode() * MAX_MEMORY + address;
	}
	
	@Override
	public String toString() {
		if(label != null) {
			return label + " " + opcode.name() + " " + address;
		}
		
		return opcode.name() + " " + address;
	}
	
}
