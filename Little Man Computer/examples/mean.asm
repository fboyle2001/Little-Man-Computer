INLOOP		INP
			BRZ CALC
			STO TEMP
			LDA SUM
			ADD TEMP
			STO SUM
			LDA COUNTER
			ADD ONE
			STO COUNTER
			BRA INLOOP
CALC		LDA COUNTER
			BRZ DIVERROR
DIVLOOP		LDA SUM
			SUB COUNTER
			STO SUM
			LDA QUOTIENT
			ADD ONE
			STO QUOTIENT
			LDA SUM
			BRZ DIVPERFECT
			BRP DIVLOOP
			BRA DIVOVER
DIVOVER		LDA SUM
			ADD COUNTER
			STO REMAINDER
			LDA QUOTIENT
			SUB ONE
			STO QUOTIENT
DIVPERFECT	LDA QUOTIENT
			OUT
			LDA LOWER_R
			OTC
			LDA REMAINDER
			OUT
			HLT
DIVERROR	LDA COUNTER
			OUT
QUOTIENT	DAT 0
REMAINDER	DAT 0
ONE 		DAT 1
TEMP 		DAT 0
COUNTER 	DAT 0
SUM			DAT 0
LOWER_R		DAT 114