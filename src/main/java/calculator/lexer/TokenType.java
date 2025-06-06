package calculator.lexer;

public enum TokenType {
    NUMBER,         // 0, 5, 10
    IDENTIFIER,     // i, j, x, y
    PLUS,           // +
    MINUS,          // +
    MUL,            //*
    DIV,            // /
    MODULO,         // %
    ASSIGN,         // =
    PLUS_ASSIGN,    // +=
    MINUS_ASSIGN,   // -=
    MUL_ASSIGN,     // *=
    DIV_ASSIGN,     // /=
    MODULO_ASSIGN,  // %=
    INCREMENT,      // ++
    DECREMENT,      // --
    LPAREN,         // (
    RPAREN,         // )
    EOF             // End of File
}
