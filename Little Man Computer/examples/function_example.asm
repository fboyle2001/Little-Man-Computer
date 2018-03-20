REQ						LDA ZERO
						STO FUNC_MULTIPLY_MULTIPLER
						INP
						BRZ END
						STO FUNC_MULTIPLY_MULTIPLER
						LDA ZERO
						STO FUNC_MULTIPLY_MULTI
						INP
						BRZ END
						STO FUNC_MULTIPLY_MULTI
						BRP BEGIN
						HLT
BEGIN					LDA RETADDR
						STO FUNC_MULTIPLY_RETADDR
						LDA ZERO
						STO FUNC_MULTIPLY_RESULT
						BRA FUNC_MULTIPLY
A_RET					LDA FUNC_MULTIPLY_RESULT
						OUT
						LDA NEW_LINE
						OTC
						BRA REQ
# This function takes 3 parameters FUNC_MULTIPLY_MULTIPLER, FUNC_MULTIPLY_MULTI and FUNC_MULTIPLY_RETADDR
# All functions require a return address so that they can go back to where they were called from
# This requires the use of non-standard LMC instructions and uses the ACC label-variable crossover
# It uses the current value of the accumulator as the branch address
# This allows a dynamic return address as long as the return address is known ahead of time
# This will always be the case as the code won't change once run
# The result, stored in FUNC_MULTIPLY_RESULT, can then be accessed
FUNC_MULTIPLY			LDA FUNC_MULTIPLY_MULTIPLER
						BRZ FUNC_MULTIPLY_END
						SUB ONE
						STO FUNC_MULTIPLY_MULTIPLER
						LDA FUNC_MULTIPLY_RESULT
						ADD FUNC_MULTIPLY_MULTI
						STO FUNC_MULTIPLY_RESULT
						LDA FUNC_MULTIPLY_MULTIPLER
						BRA FUNC_MULTIPLY
FUNC_MULTIPLY_END		LDA FUNC_MULTIPLY_RETADDR
						BRA ACC
END						HLT
ONE     				DAT 1
FUNC_MULTIPLY_RESULT  	DAT 0
FUNC_MULTIPLY_MULTIPLER DAT 0
FUNC_MULTIPLY_MULTI   	DAT 0
FUNC_MULTIPLY_RETADDR	DAT 0
RETADDR					DAT 17
ZERO					DAT 0
NEW_LINE				DAT	10