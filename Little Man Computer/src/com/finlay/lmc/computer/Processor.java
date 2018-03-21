package com.finlay.lmc.computer;

import com.finlay.lmc.computer.instructions.Instruction;
import static com.finlay.lmc.computer.Memory.MAX_MEMORY;

public class Processor {

	private final LittleManComputer computer;
	
	private int accumulator;
	private int programCounter;
	private int accAddress;
	
	public Processor(LittleManComputer computer) {
		this.computer = computer;
		this.accumulator = 0;
		this.programCounter = 0;
		this.accAddress = MAX_MEMORY / 2;
	}
	
	public void executeInstruction() throws ProcessorException {
		Instruction instruction = computer.getMemory().getInstructionAt(programCounter);
		
		if(instruction == null) {
			throw new ProcessorException("No instruction found at memory location [" + programCounter + "].");
		}
		
		boolean incremented = false;
		
		switch(instruction.getOpcode()) {
		case ADD:
			accumulator += computer.getMemory().get(instruction.getAddress());
			break;
		case BRA:
			this.programCounter = instruction.getAddress() == accAddress ? this.accumulator : instruction.getAddress();
			incremented = true;
			break;
		case BRP:
			if(accumulator >= 0) {
				this.programCounter = instruction.getAddress() == accAddress ? this.accumulator : instruction.getAddress();
				incremented = true;
			}
			break;
		case BRZ:
			if(accumulator == 0) {
				this.programCounter = instruction.getAddress() == accAddress ? this.accumulator : instruction.getAddress();
				incremented = true;
			}
			break;
		case HLT:
			computer.halt();
			return;
		case OTC:
		case OUT:
		case INP:
			if(instruction.getAddress() == 1) {
				this.accumulator = computer.requestInput();
			} else if(instruction.getAddress() == 2) {
				System.out.print(this.accumulator);
			} else {
				System.out.print((char) this.accumulator);
			}
			
			break;
		case LDA:
			this.accumulator = computer.getMemory().get(instruction.getAddress());
			break;
		case STA:
		case STO:
			computer.getMemory().set(instruction.getAddress(), accumulator);
			break;
		case SUB:
			accumulator -= computer.getMemory().get(instruction.getAddress());
			break;
		default:
		}
		
		if(!incremented) {
			this.programCounter += 1;
		}
	}
	
}
