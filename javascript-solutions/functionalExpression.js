"use strict"

/*
EASY
*/

let expression = f => (...args) => (x,y,z) => f(...args.map(arg => arg(x,y,z)));
let cnst = a => () => a;
let one = () => 1;
let two = () => 2;
let variable = name => (x, y, z) => ({'x': x, 'y': y, 'z': z})[name];
let add = expression((a, b) => a+b);
let subtract = expression((a, b) => a-b);
let multiply = expression((a, b) => a*b);
let divide = expression((a, b) => a/b);
let negate = expression(a => -a);
let madd = expression((a,b,c) => a*b+c);
let floor = expression(a => Math.floor(a));
let ceil = expression(a => Math.ceil(a));
let sin = expression((a) => Math.sin(a));
let cos = expression((a) => Math.cos(a));
let sinh = expression((a) => Math.sinh(a));
let cosh = expression((a) => Math.cosh(a));

let example = add(
    subtract(
        multiply(
            variable("x"),
            variable("x")
        ),
        multiply(
            cnst(2),
            variable("x")
        )
    ),
    cnst(1)
);


for (let i = 0; i <= 10; i++) {
    println(example(i, 0, 0))
}

/*
HARD
*/

function parse(str) {
    const operators = {
        '*+': {
            f: madd,
            args: 3
        },
        '+': {
            f: add,
            args: 2
        },
        '-': {
            f: subtract,
            args: 2
        },
        '*': {
            f: multiply,
            args: 2
        },
        '/': {
            f: divide,
            args: 2
        },
        'negate': {
            f: negate,
            args: 1
        },
        '_': {
            f: floor,
            args: 1
        },
        '^': {
            f: ceil,
            args: 1
        },
        'sin': {
            f: sin,
            args: 1
        },
        'cos': {
            f: cos,
            args: 1
        },
        'sinh': {
            f: sinh,
            args: 1
        },
        'cosh': {
            f: cosh,
            args: 1
        },
    }
    const variables = ['x','y','z']
    const constants = {
        'one': one,
        'two': two,
    }
    let isOperand = str => (!isNaN(str) || variables.includes(str) || str in constants);
    let convertOperand = str => !isNaN(str) ? cnst(Number(str)) : variables.includes(str) ? variable(str) : constants[str];
    let convertFunction = (f, n, stack) => {
        let operands = [n];
        for (let i = 0; i < n; i++) {
            operands[i] = stack.pop();
        }
        return f(...operands.reverse());
    }
    let token, operands = [], tokens = str.split(" ").filter((str) => str !== '').reverse();
    while (tokens.length > 0) {
        let result = isOperand(token = tokens.pop()) ?
                        convertOperand(token) :
                        convertFunction(operators[token]["f"], operators[token]["args"], operands);
        operands.push(result);
    }
    return operands.pop();
}
