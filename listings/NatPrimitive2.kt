object Natural : UnaryPredicate<ExecutionContext>("natural") {
    override fun Solve.Request<ExecutionContext>.computeAll(first: Term) = 
        when (first) {
            is Var -> generateValues().map { replySuccess(Substitution.of(first, it)) }
            is Integer -> sequenceOf(replyWith(checkValue(first)))
            else -> sequenceOf(replyFail())
        }

    private fun generateValues(): Sequence<Term> =
        generateSequence(BigInteger.ZERO) { it + BigInteger.ONE }.map { Integer.of(it) }

    private fun checkValue(value: Integer): Boolean =
        value.intValue.signum >= 0
}

val mylib = Library.of(
    alias = "prolog.mylib", 
    primitives = mapOf(Natural.descriptionPair)
)

val solver = Solver.prolog.solverOf(libraries = Runtime.of(mylib))