object Retract : UnaryPredicate<ExecutionContext>("retract") {
    override fun Solve.Request<ExecutionContext>.computeAll(
        x: Term
    ): Sequence<Solve.Response> {
        ensuringArgumentIsWellFormedClause(0)
        val clause: Clause = when (x) {
            is Clause -> x
            is Struct -> Rule.of(x, Var.anonymous())
            else -> return sequenceOf(ensuringArgumentIsCallable(0).replyFail())
        }
        ensuringClauseProcedureHasPermission(clause, PermissionError.Operation.MODIFY)
        return context.dynamicKb[clause].buffered().map {
            val substitution = when (x) {
                is Clause -> (x mguWith it) as Substitution.Unifier
                else -> (x mguWith it.head!!) as Substitution.Unifier
            }
            replySuccess(substitution) {
                removeDynamicClauses(it)
            }
        }
    }
}