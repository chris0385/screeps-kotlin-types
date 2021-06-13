package screeps.utils

import screeps.api.Game
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Lazy property that computed at most once per tick
 */
private class TickLazy<in P : Any?, T : Any>(private val computeOncePerTick: P.() -> T) : ReadOnlyProperty<P, T> {
    private var value: T? = null
    private var tick: Int = -1

    override fun getValue(thisRef: P, property: KProperty<*>): T {
        if (Game.time != tick) {
            tick = Game.time
            value = computeOncePerTick(thisRef)
        }
        return value.unsafeCast<T>()
    }
}

/**
 * Creates a lazy property that computed at most once per tick
 */
fun <P : Any?, T : Any> lazyPerTick(computeOncePerTick: P.() -> T): ReadOnlyProperty<P, T> {
    return TickLazy(computeOncePerTick)
}
