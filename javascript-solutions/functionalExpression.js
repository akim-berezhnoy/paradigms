"use strict"

/*
EASY
*/

let expression = f => (...args) => (x,y,z) => f(...args.map(arg => arg(x,y,z)));

let cnst = a => () => a;
function variable(name) {
    return (x, y, z) => ({'x': x, 'y': y, 'z': z})[name];
}
let add = expression((a, b) => a+b);
let subtract = expression((a, b) => a-b);
let multiply = expression((a, b) => a*b);
let divide = expression((a, b) => a/b);
let negate = expression(a => -a);

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
    let variables = ['x','y','z']
    const binaryOperators = {
        '+': add,
        '-': subtract,
        '*': multiply,
        '/': divide,
    }
    const unaryOperators = {
        'negate': negate
    }

    let isOperand = str => !isNaN(str) || variables.includes(str);
    let convertOperand = str => isNaN(str) ? variable(str) : cnst(Number(str));
    let rCurry = f => a => b => f(b,a);

    let token, operands = [], tokens = str.split(" ").filter((str) => str !== '').reverse();
    while (tokens.length > 0) {
        if (isOperand(token = tokens.pop())) {
            operands.push(convertOperand(token));
        } else {
            let operator =
                token in binaryOperators ?
                    rCurry(binaryOperators[token])(operands.pop()) :
                    unaryOperators[token];
            operands.push(operator(operands.pop()));
        }
    }
    return operands.pop();
}