"use strict"

// Const
function Const(value) {
    this.value = value;
}
Const.prototype = {
    evaluate: function () { return this.value },
    toString: function () { return this.value.toString() },
    prefix: function() { return this.toString() },
    diff: function () { return constants.ZERO },
}

const constants = {
    'ZERO': new Const(0),
    'ONE': new Const(1),
    'TWO': new Const(2),
};

// Variable
let argsOrd = ['x', 'y', 'z'];
function Variable(name) {
    this.name = name;
}
Variable.prototype = {
    evaluate: function (...args) { return args[argsOrd.indexOf(this.name)] },
    toString: function () { return this.name },
    prefix: function() { return this.toString() },
    diff: function (d) { return d === this.name ? constants.ONE : constants.ZERO },
}

// Operation
function Operation(...operands) {
    this.operands = operands;
}
Operation.prototype = {
    evaluate: function (...args) { return this.f(...this.operands.map(operand => operand.evaluate(...args))) },
    toString: function () { return this.operands.reduce((accumulator, operand) => accumulator + operand + " ", '') + this.sign },
    prefix: function() { return '(' + this.sign + this.operands.reduce((accumulator, operand) => accumulator + " " + operand.prefix(), '') + ')'},
    diff: function (d) { return this.diffRule(...this.operands)(d) },
}

// Operators-list for parser
const operators = new Map();

// Creators
function createOperation(f, sign, diffRule, n) {
    function Constructor(...operands) {
        Operation.call(this, ...operands);
    }
    Constructor.prototype = Object.create(Operation.prototype);
    Constructor.prototype.constructor = Constructor;
    Constructor.prototype.f = f;
    Constructor.prototype.sign = sign;
    Constructor.prototype.diffRule = diffRule;
    operators.set(sign, {constructor: Constructor, argc: n});
    return Constructor;
}

function createConcreteOperation(f, sign, diffRule, n) {
    let operation = createOperation(f, sign, diffRule, n);
    let proto = operators.get(operation.prototype.sign).constructor.prototype;
    operators.delete(proto.sign);
    operators.set(proto.sign = proto.sign + n, {constructor: proto.constructor, argc: n})
    return operation;
}

// Operators
const Add = createOperation(
    (a, b) => a + b,
    "+",
    (a, b) => d => new Add(a.diff(d), b.diff(d)),
    2,
)
const Subtract = createOperation(
    (a, b) => a - b,
    "-",
    (a, b) => d => new Subtract(a.diff(d), b.diff(d)),
    2,
)
const Multiply = createOperation(
    (a, b) => a * b,
    "*",
    (a, b) => d => new Add(
        new Multiply(a.diff(d), b),
        new Multiply(a, b.diff(d))
    ),
    2,
)
const Divide = createOperation(
    (a, b) => a / b,
    "/",
    (a, b) => d => new Divide(
        new Subtract(
            new Multiply(a.diff(d), b),
            new Multiply(a, b.diff(d))
        ),
        new Multiply(b, b)
    ),
    2,
)
const Negate = createOperation(
    a => -a,
    "negate",
    a => d => new Negate(a.diff(d)),
    1,
)
const Exp = createOperation(
    a => Math.pow(Math.E, a),
    "exp",
    a => d => new Multiply(
        new Exp(a),
        a.diff(d)
    ),
    1,
)
const Ln = createConcreteOperation(
    a => Math.log(a),
    "ln",
    a => d => new Multiply(
        new Divide(
            constants.ONE,
            a
        ),
        a.diff(d)
    ),
    1,
)
const Sqrt = createOperation(
    a => Math.sqrt(a),
    "sqrt",
    a => d => new Multiply(
        new Divide(
            constants.ONE,
            new Multiply(
                constants.TWO,
                new Sqrt(a)
            )
        ),
        a.diff(d)
    ),
    1,
)
const ArcTan = createOperation(
    a => Math.atan(a),
    "atan",
    a => d => new Divide(
        a.diff(d),
        new Add(
            constants.ONE,
            new Multiply(a, a)
        )
    ),
    1,
)
const ArcTan2 = createOperation(
    (a, b) => Math.atan2(a, b),
    "atan2",
    (a, b) => d => new ArcTan(new Divide(a, b)).diff(d),
    2,
)
const SumSq = createOperation(
    (...args) => args.map(arg => Math.pow(arg, 2)).reduce((accum, arg) => accum + arg, 0),
    "sumsq",
    (...args) => d => args.map(arg => new Multiply(arg, arg).diff(d)).reduce((accum, arg) => new Add(accum, arg), constants.ZERO),
)
function SumSqN(n) {
    return createConcreteOperation(
        SumSq.prototype.f,
        "sumsq",
        SumSq.prototype.diffRule,
        n,
    )
}
function DistanceN(n) {
    return createConcreteOperation(
        (...args) => Math.sqrt(SumSq.prototype.f(...args)),
        "distance",
        (...args) => d => new Sqrt(args.map(arg => new Multiply(arg, arg)).reduce((accum, arg) => new Add(accum, arg), constants.ZERO)).diff(d),
        n,
    )
}

const Sumsq2 = SumSqN(2);
const Sumsq3 = SumSqN(3);
const Sumsq4 = SumSqN(4);
const Sumsq5 = SumSqN(5);
const Distance2 = DistanceN(2);
const Distance3 = DistanceN(3);
const Distance4 = DistanceN(4);
const Distance5 = DistanceN(5);

/*
    PARSER
*/
function ParseError(message) {
    this.message = "ParseError: " + message;
}
ParseError.prototype = {
    ...Object.create(Error.prototype),
    name: "ParseError",
    constructor: ParseError,
};

const assert = (statement, string) => { if (!statement) throw new ParseError(string); }
const isOperand = str => !isNaN(str) || argsOrd.includes(str);
const convertOperand = str => argsOrd.includes(str) ? new Variable(str) : new Const(Number(str));

function parse(str) {
    const convertFunction = (f, n) => new f(...operands.splice(operands.length-n, n));
    function handleToken(token) {
        let result;
        if (isOperand(token)) {
            result = convertOperand(token);
        } else if (operators.has(token)) {
            result = convertFunction(operators.get(token).constructor, operators.get(token).argc);
        } else if (operators.get((token = token.split(/(\d+)/))[0]).argc) {
            result = convertFunction(operators.get(token[0])(token[1]), token[1]);
        } else {
            assert(false, `Unknown type of token: ${token}.`)
        }
        return result;
    }
    let operands = [];
    str.trim().split(/\s+/).forEach(token => operands.push(handleToken(token)));
    return operands.pop();
}

function parsePrefix(str) {
    /* Matches any:
          integer,
          non-space sequence of characters (в простонародье word)
          brace (at last brace, that's why braces are matched correctly) */
    let tokens = str.match(/-?\d+|\w+|\S/g).reverse();
    function recursiveParse(tokens) {
        // Take first token
        let token = tokens.pop();
        // Check for an operator
        assert(operators.has(token), `Expected operator, found '${token}'`);
        let operator = operators.get(token), operands = [];
        // Take all operands which are needed to apply current operator
        for (let i = 0; i < operator.argc; i++) {
            token = tokens.pop()
            // Check for a normal (variable, const or opened bracket) operator start
            assert(isOperand(token) || token === '(', `Expected operand or opened brace, found '${token}'.`);
            // Convert and push variable, const or parse and push the whole operand-chunk
            operands.push(isOperand(token) ? convertOperand(token) : recursiveParse(tokens));
        }
        // Make sure that the expression (or single-operator block) lasted with a closed brace
        assert((token = tokens.pop()) === ')', `Expected closed brace, found '${token}'.`);
        // Successfully parsed an operator
        return new operator.constructor(...operands);
    }
    // Check for a single-operand expression. If so => leave
    if (tokens.length === 1 && isOperand(tokens[0])) return convertOperand(tokens.pop());
    // Check for a normal (opened bracket) start
    assert(tokens.pop() === '(', `Expected '(' at the start of an expression.`);
    let result = recursiveParse(tokens);
    // Check for a normal (nothing left) ending
    assert(tokens.length === 0, `Expected end of expression, found tokens: ${tokens}.`);
    // Successfully parsed the whole expression
    return result;
}
