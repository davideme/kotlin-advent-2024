import kotlin.math.*

data class Vec2(
    val x: Float,
    val y: Float
) {
    constructor() : this(0f, 0f)
    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    companion object {
        val ZERO = Vec2(0f, 0f)
        /** Random normal vector */
        val RANDOM
            get() =
                Vec2(1f, 0f)
                    .rotate(Math.random().toFloat() * 360)
    }

    operator fun times(scalar: Number) = Vec2(x * scalar.toFloat(), y * scalar.toFloat())
    operator fun div(scalar: Number) = Vec2(x / scalar.toFloat(), y / scalar.toFloat())
    operator fun plus(vec: Vec2) = Vec2(x + vec.x, y + vec.y)
    operator fun minus(vec: Vec2) = Vec2(x - vec.x, y - vec.y)
    operator fun unaryMinus() = this * -1f

    infix fun dst2To(to: Vec2) = (x - to.x).pow(2) + (y - to.y).pow(2)
    infix fun dstTo(to: Vec2) = sqrt(this dst2To to)

    infix fun scl(by: Vec2) = Vec2(x * by.x, y * by.y)

    fun norm() = (this dstTo ZERO).let { dst -> Vec2(x / dst, y / dst) }
    fun rotate(degrees: Float): Vec2 {
        val rad = (degrees / 180 * PI).toFloat()
        return Vec2(x * cos(rad) - y * sin(rad), x * sin(rad) + y * cos(rad))
    }

}

typealias Line = Pair<Vec2, Vec2>

/**
 * Returns array...
 * * of zero points, if there is no intersection;
 * * of one point, if there's one intersection;
 * * of two points (segment), if it is continuous intersection;
 * */
fun intersectLines(l1: Line, l2: Line): List<Vec2> {

    // Checking if lines are in rectangular bounds of each other
    infix fun Float.rng(that: Float) = if (that > this) this..that else that..this
    if (l1.first.x !in l2.first.x rng l2.second.x && l2.first.x !in l1.first.x rng l1.second.x)
        return emptyList()
    if (l1.first.y !in l2.first.y rng l2.second.y && l2.first.y !in l1.first.y rng l1.second.y)
        return emptyList()


    /* to be invoked in case of collinearities */
    fun solveCollinearLines(): List<Vec2> {
        val l1a = listOf(l1.first, l1.second)
        val l2a = listOf(l2.first, l2.second)
        val centralSegment = (l1a + l2a)
            .sortedBy { it.x }
            .sortedBy { it.y }
            .drop(1)
            .take(2)
        return centralSegment.distinct()
    }

    // start
    val s = listOf(l1.first, l2.first)
    // delta
    val d = listOf(l1.second - l1.first, l2.second - l2.first)

    val (s1, s2) = s
    val (d1, d2) = d

    // s1.x + (a * d1.x) = s2.x + (b * d2.x)
    // s1.y + (a * d1.y) = s2.y + (b * d2.y)

    // (a * d1.x) - (b * d2.x) = s2.x - s1.x
    // (a * d1.y) - (b * d2.y) = s2.y - s1.y

    val sx = s2.x - s1.x
    val sy = s2.y - s1.y

    // (a * d1.x) - (b * d2.x) = sx
    // (a * d1.y) - (b * d2.y) = sy

    // Checking for perpendiculars
    listOf(l1, l2, l1).windowed(2).forEach { (a, b) ->
        if (a.first.x == a.second.x && b.first.y == b.second.y) {
            val collideX = listOf(b.first.x, b.second.x)
                .sorted()
                .let { (a, b) -> a..b }
                .contains(a.first.x)

            val collideY = listOf(a.first.y, a.second.y)
                .sorted()
                .let { (a, b) -> a..b }
                .contains(b.first.y)

            return if (collideX && collideY) listOf(Vec2(a.first.x, b.first.y))
            else emptyList()
        }
    }

    // Checking special cases of collinear and parallel lines
    listOf(
        listOf(d1.x, d2.x, sx),
        listOf(d1.y, d2.y, sy)
    ).forEach {
        if (it.take(2).all { it == 0f })
            return if (it.last() == 0f)
            // collinear, system can be described with one equation
                solveCollinearLines()
            else
            // parallel, system can not be solved
                emptyList()
    }


    val compX = listOf(d1.x, -d2.x)
    val compY = listOf(d1.y, -d2.y)
    val divisible = compX.zip(compY).indexOfFirst { it.first != 0f && it.second != 0f }
    val solveFor = divisible.inc().rem(2)

    // If system degenerates to two same variable equations
    if (divisible == -1)
        return if ((compX.all { it == 0f } && sx != 0f) || (compY.all { it == 0f } && sy != 0f))
        // parallel, system can not be solved.
            listOf()
        else
        // collinear, system can be described with one equation
            solveCollinearLines()

    val mul = compX[divisible] / compY[divisible]
    val coef = compX[solveFor] - compY[solveFor] * mul
    val sum = sx - sy * mul

    if (coef == 0f)
        return if (sum == 0f)
        // final equation is a useless koala
            solveCollinearLines()
        else
        // final equation is not solvable
            listOf()

    return listOf(s[solveFor] + d[solveFor] * (sum / coef))

}

typealias Poly = List<Vec2>

/**
 * Finds longest clockwise path through given polygons
 *
 * @param checkForLoops Whether or not check for infinite loops, that might occur on non-clockwise polygons.
 * */
fun union(p1: Poly, p2: Poly, checkForLoops: Boolean = true): Poly {
    var (current, other) =
        listOf(p1, p2)
            .map { poly ->
                val offset = poly.withIndex().minBy { it.value.y }!!.index
                (poly.drop(offset) + poly.take(offset)).toList()

            }
            .sortedBy { it.first().y }

    val startPoly = current
    var cp = 0

    val path = mutableListOf(startPoly[0])
    val loopCheck = mutableSetOf<Pair<Int, Int>>()

    fun swapPolys() {
        other = current.also { current = other }
    }

    fun nextEdge() {
        cp += 1; cp %= current.size
    }

    nextEdge()

    fun Poly.edge(i: Int) = this[i] to this[(i + 1) % size]

    while (cp != 0 || current != startPoly) {
        path.add(current[cp])
        val edge = current.edge(cp)

        if (checkForLoops && !loopCheck.add(cp to (if (current == startPoly) 0 else 1)))
            throw IllegalArgumentException("One of the polys is incorrectly oriented!")

        for (op in 0 until other.size) {
            val testEdge = other.edge(op)
            val intersection = intersectLines(edge, testEdge)
            if (intersection.isNotEmpty()) {
                if (intersection.size == 1)
                    path.add(intersection[0])
                path.add(testEdge.second)
                swapPolys()
                cp = op
                break
            }

        }
        nextEdge()
    }

    return path
}