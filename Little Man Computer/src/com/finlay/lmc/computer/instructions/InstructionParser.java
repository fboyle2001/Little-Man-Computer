package com.finlay.lmc.computer.instructions;

import static com.finlay.lmc.computer.Memory.MAX_MEMORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.finlay.lmc.computer.Memory;

public class InstructionParser {
	
	public static Memory parse(List<String> code) throws InstructionParserException {
		boolean strict = false; //If true, ACC is not allowed.
		ArrayList<String[]> parts = new ArrayList<>(code.size());
		
		if(code.get(0).equalsIgnoreCase("STRICT")) {
			strict = true;
		}
		
		for(String line : code) {
			if(line.startsWith("//") || line.startsWith("#")) {
				continue;
			}
			
			line = line.replaceAll("\\bHLT\\b", "HLT 0");
			line = line.replaceAll("\\bINP\\b", "INP 1");
			line = line.replaceAll("\\bOUT\\b", "OUT 2");
			line = line.replaceAll("\\bOTC\\b", "OTC 22");
			
			String[] split = line.split(" ");
			
			if(strict) {
				if(split.length == 2) {
					if(split[1].equalsIgnoreCase("ACC")) {
						System.out.println("ACC not allowed in strict mode.");
						throw new InstructionParserException("ACC is not allowed in strict mode.");
					}
				} else if(split.length == 3) {
					if(split[2].equalsIgnoreCase("ACC")) {
						System.out.println("ACC not allowed in strict mode.");
						throw new InstructionParserException("ACC is not allowed in strict mode.");
					}
				}
			}
			
			parts.add(line.split(" "));
		}
		
		//parts.forEach(x -> System.out.println(Arrays.toString(x)));
		
		Memory memory = new Memory();
		HashMap<String, Integer> labels = compileLabelAddresses(parts); // i
		HashMap<String, Integer[]> variables = compileLabelVariables(parts); // 99 - i
		
		if(!strict) {
			labels.put("ACC", MAX_MEMORY / 2);
		}
		
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
						if(variables.containsKey(line[1])) {
							address = variables.get(line[1])[0];
						} else {
							throw new InstructionParserException("Unknown variable " + line[1] + " on line " + (i + 1));
						}
						
						break;
					case BRA:
					case BRP:
					case BRZ:
						if(labels.containsKey(line[1])) {
							address = labels.get(line[1]);
						} else {
							throw new InstructionParserException("Unknown label " + line[1] + " on line " + (i + 1));
						}
						
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
						if(variables.containsKey(line[2])) {
							address = variables.get(line[2])[0];
						} else {
							throw new InstructionParserException("Unknown variable " + line[2] + " on line " + (i + 1));
						}
						
						break;
					case BRA:
					case BRP:
					case BRZ:
						if(labels.containsKey(line[2])) {
							address = labels.get(line[2]);
						} else {
							throw new InstructionParserException("Unknown label " + line[2] + " on line " + (i + 1));
						}
						
						break;
					default:
						address = 0;
						break;
					}
				}
				
				instruction = new Instruction(line[0], opcode, address);
			}
			
			if(instruction == null) {
				throw new InstructionParserException("Unknown instruction '" + Arrays.toString(line) + "' on line " + (i + 1));
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
