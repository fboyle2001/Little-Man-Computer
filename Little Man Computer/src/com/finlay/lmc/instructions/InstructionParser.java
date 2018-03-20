package com.finlay.lmc.instructions;

import static com.finlay.lmc.computer.Memory.MAX_MEMORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.finlay.lmc.computer.Memory;

public class InstructionParser {
	
	public static Memory parse(List<String> code) {
		ArrayList<String[]> parts = new ArrayList<>(code.size());
		
		code.forEach(line -> {
			line = line.replaceAll("HLT", "HLT 0");
			line = line.replaceAll("INP", "INP 1");
			line = line.replaceAll("OUT", "OUT 2");
			line = line.replaceAll("OTC", "OTC 22");
			parts.add(line.split(" "));
		});
		
		Memory memory = new Memory();
		HashMap<String, Integer> labels = compileLabelAddresses(parts); // i
		HashMap<String, Integer[]> variables = compileLabelVariables(parts); // 99 - i
		
		for(int i = 0; i < parts.size(); i++) {
			String[] line = parts.get(i);
			
			Instruction instruction = null;
			
			if(line.length == 2) {
				OpCode opcode = OpCode.valueOf(line[0]);
				int address;
				
				try {
					address = Integer.parseInt(line[1]);
				} catch (NumberFormatException e) {
					switch(opcode) {
					case ADD:
					case LDA:
					case SUB:
					case STA:
					case STO:
						System.out.println(line[1]);
						address = variables.get(line[1])[0];
						break;
					case BRA:
					case BRP:
					case BRZ:
						address = labels.get(line[1]);
						break;
					default:
						address = 0;
						break;
					}
				}
				
				instruction = new Instruction(null, opcode, address);
			} else if (line.length == 3) {
				if(line[1].equalsIgnoreCase("DAT")) {
					continue;
				}
				
				OpCode opcode = OpCode.valueOf(line[1]);
				int address;
				
				try {
					address = Integer.parseInt(line[2]);
				} catch (NumberFormatException e) {
					switch(opcode) {
					case ADD:
					case LDA:
					case SUB:
					case STA:
					case STO:
						address = variables.get(line[2])[0];
						break;
					case BRA:
					case BRP:
					case BRZ:
						address = labels.get(line[2]);
						break;
					default:
						address = 0;
						break;
					}
				}
				
				instruction = new Instruction(line[0], opcode, address);
			}
			
			if(instruction == null) {
				System.out.println(Arrays.toString(line));
				System.out.println("Null instruction");
				return null;
			}
			
			memory.set(i, instruction.toInteger());
			
		}
		
		for(Entry<String, Integer[]> entry : variables.entrySet()) {
			memory.set(entry.getValue()[0], entry.getValue()[1]);
		}
		
		return memory;
	}
	
	private static HashMap<String, Integer> compileLabelAddresses(List<String[]> code) {
		HashMap<String, Integer> labels = new HashMap<>();
		
		for(int i = 0; i < code.size(); i++) {
			String[] parts = code.get(i);
			
			if(parts.length == 3) {
				if(!parts[1].equalsIgnoreCase("DAT")) {
					labels.put(parts[0], i);
				}
			}
		}
		
		return labels;
	}
	
	private static HashMap<String, Integer[]> compileLabelVariables(List<String[]> code) {
		HashMap<String, Integer[]> labels = new HashMap<>();
		
		for(int i = 0; i < code.size(); i++) {
			String[] parts = code.get(i);
			
			if(parts.length == 3) {
				if(parts[1].equalsIgnoreCase("DAT")) {
					labels.put(parts[0], new Integer[] {MAX_MEMORY - 1 - labels.size(), Integer.parseInt(parts[2])});
				}
			}
		}
		
		return labels;
	}
	
}
