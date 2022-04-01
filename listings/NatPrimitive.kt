val nat = Signature("natural", 1)

fun natural(req: Solve.Request<ExecutionContext>): Sequence<Solve.Response> =
    when (val arg1: Term = req.arguments[0]) {
        is Numeric -> sequenceOf(
            if (arg1.intValue >= BigInteger.ZERO) 
                req.replySuccess() else req.replyFail()
        )
        is Var -> generateSequence(0) { it + 1 }.map { Integer.of(it) }
                    .map { it mguWith arg1 }.map { req.replyWith(it) }
        else -> throw TypeError.forArgument(req.context, req.signature, TypeError.Expected.NUMBER, arg1, 0)
    }

val mylib = Library.aliased(
    alias = "prolog.mylib", 
    primitives = mapOf(nat to ::natural)
)

val solver = Solver.prolog.solverOf(Libraries.of(mylib))
println(solver.solveOnce(Struct.of("natural", Integer.ONE))) // yes
println(solver.solveOnce(Struct.of("natural", Integer.MINUS_ONE))) // no
println(solver.solveOnce(Struct.of("natural", Atom.of("a")))) // type_error
println(solver.solve(Struct.of("natural", Var.of("X"))).take(3).toList()) // 0,1,2