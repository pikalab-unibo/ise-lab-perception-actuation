object Assert : UnaryPredicate.NonBacktrackable<ExecutionContext>("assert") {
    override fun Solve.Request<ExecutionContext>.computeOne(x: Term): Solve.Response {
        ensuringArgumentIsWellFormedClause(0)
        val clause: Clause = when (x) {
            is Clause -> x
            is Struct -> Fact.of(x)
            else -> return ensuringArgumentIsCallable(0).replyFail()
        }
        ensuringClauseProcedureHasPermission(clause, MODIFY)
        return replySuccess {
            addDynamicClauses(clause)
        }
    }
}