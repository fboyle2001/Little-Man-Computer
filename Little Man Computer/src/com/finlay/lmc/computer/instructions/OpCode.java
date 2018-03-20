package com.finlay.lmc.computer.instructions;

public enum OpCode {

	HLT(0), ADD(1), SUB(2), STO(3), STA(3), LDA(5), BRA(6), BRZ(7), BRP(8), INP(9), OUT(9), OTC(9);
	
	private int code;
	
	private OpCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static OpCode fromLong(int code) {
		for(OpCode op : OpCode.values()) {
			if(op.getCode() == code) {
				return op;
			}
		}
		
		return null;
	}
	
}
