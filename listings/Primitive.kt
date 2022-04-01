interface Primitive {
    fun solve(request: Request): Sequence<Response>
}

interface Request {
    val signature: Signature
    val arguments: List<Term>
    val context: ExecutionContext
    val requestIssuingInstant: TimeInstant = currentTimeInstant()
    val executionMaxDuration: TimeDuration = TimeDuration.MAX_VALUE

    fun replyWith(...): Response
    fun replySuccess(...): Response
    fun replyFail(...): Response
    fun replyException(...): Response
}

interface Response {
    val solution: Solution
    val sideEffects: List<SideEffect>
}