"use strict"

// Const
function Const(value) {
    this.value = value;
}
Const.prototype = {
    evaluate: function () { return this.value },
    toString: function () { return this.value.toString() },
    diff:     function () { return new Const(0) },
}

// Variable
let argsOrd = ['x', 'y', 'z'];
function Variable(name) {
    this.name = name;
}
Variable.prototype = {
    evaluate: function (...args) { return args[argsOrd.indexOf(this.name)] },
    toString: function () { return this.name },
    diff:     function (differential) { return this.name === differential ? new Const(1) : new Const(0) },
}

// Expression
function Expression(...operands) {
    this.operands = operands;
}
Expression.prototype = {
    evaluate: function (...args) { return this.f(...this.operands.map(operand => operand.evaluate(...args))) },
    toString: function () { return this.operands.reduce((accumulator, operand) => accumulator + operand + " ", '') + this.sign },
    diff:     function (differential) { return this.diffRule(...this.operands)(differential) },
}

// Creator of constructors
function operationConstructorCreator(f, sign, diffRule) {
    function ExpressionConstructor(...operands) {
        Expression.call(this, ...operands);
    }
    ExpressionConstructor.prototype = {
        ...Expression.prototype,
        constructor: ExpressionConstructor,
        f: f,
        sign: sign,
        diffRule: diffRule,
    }
    return ExpressionConstructor;
}

// Operators (binary)
const Add = operationConstructorCreator(
    (a, b) => a + b,
    "+",
    (a, b) => d => new Add(a.diff(d), b.diff(d)),
)
const Subtract = operationConstructorCreator(
    (a, b) => a - b,
    "-",
    (a, b) => d => new Subtract(a.diff(d), b.diff(d)),
)
const Multiply = operationConstructorCreator(
    (a, b) => a * b,
    "*",
    (a, b) => d => new Add(new Multiply(a.diff(d), b), new Multiply(a, b.diff(d))),
)
const Divide = operationConstructorCreator(
    (a, b) => a / b,
    "/",
    (a, b) => d => new Divide(new Subtract(new Multiply(a.diff(d), b), new Multiply(a, b.diff(d))), new Multiply(b, b))
)
const Negate = operationConstructorCreator(
    a => -a,
    "negate",
    a => d => new Negate(a.diff(d)),
)
const Sqrt = operationConstructorCreator(
    a => Math.sqrt(a),
    "sqrt",
    a => d => new Multiply(new Divide(new Const(1), (new Multiply(new Const(2), new Sqrt(a)))), a.diff(d)),
)

/*
    PARSER
*/
function parse(str) {
    const operators = {
        '+': {
            f: Add,
            args: 2
        },
        '-': {
            f: Subtract,
            args: 2
        },
        '*': {
            f: Multiply,
            args: 2
        },
        '/': {
            f: Divide,
            args: 2
        },
        'negate': {
            f: Negate,
            args: 1
        },
    }
    const constants = {
        'one': new Const(1),
        'two': new Const(2),
    }
    let isOperand = str => (!isNaN(str) || argsOrd.includes(str) || str in constants);
    let convertOperand = str => argsOrd.includes(str) ? new Variable(str) : str in constants ? constants[str] : new Const(Number(str));
    let convertFunction = (f, n, stack) => {
        let args = stack.slice(stack.length-n);
        stack.splice(stack.length-n, n);
        return new f(...args);
    }
    let operands = [], token, tokens = str.split(" ").filter(e => e !== '').reverse();
    while (tokens.length > 0) {
        let result = isOperand(token = tokens.pop()) ? convertOperand(token):
                     convertFunction(operators[token]["f"], operators[token]["args"], operands);
        operands.push(result);
    }
    return operands.pop();
}
