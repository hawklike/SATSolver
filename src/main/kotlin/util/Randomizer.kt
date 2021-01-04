package util

import java.util.concurrent.ThreadLocalRandom

object Randomizer {

    /**
     * Returns true with probability 1:*n*.
     * For example, if *n* is set to 3, then
     * returns true with probability of 33.3 %.
     *
     * @param n probability
     * @return true with probability 1:n
     * @author steuejan
     */
    fun oneTo(n: Int): Boolean {
        return ThreadLocalRandom.current().nextInt(1, n + 1) % n == 0
    }

    /**
     * Returns a pseudorandom int value between
     * 0 and the specified bound (exclusive).
     */
    fun nextInt(upperBound: Int) = ThreadLocalRandom.current().nextInt(0, upperBound)
}
