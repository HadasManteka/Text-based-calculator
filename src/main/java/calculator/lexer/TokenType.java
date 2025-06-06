package calculator.lexer;

public enum TokenType {
    NUMBER,         // 0, 5, 10
    IDENTIFIER,     // i, j, x, y
    PLUS,
    MINUS,// +
    MUL,//*
    DIV,//
    MODULO,
    ASSIGN,         // =
    PLUS_ASSIGN,    // +=
    MINUS_ASSIGN,
    INCREMENT,      // ++ (for both prefix and postfix)
    DECREMENT,
    LPAREN,         // (
    RPAREN,         // )
    EOF             // End of File/Input
}
