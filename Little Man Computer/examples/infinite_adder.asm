LOOP    INP
        STA VALUE
        BRZ END
        LDA RESULT
        ADD VALUE
        STA RESULT
        BRA LOOP
END     LDA RESULT
        OUT
VALUE   DAT 0
RESULT  DAT 0