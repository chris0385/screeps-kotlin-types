package screeps.api

typealias ScreepId = String

external interface Identifiable {
    val id: ScreepId
}

external interface Harvestable

external interface RenewableHarvestable : Harvestable {
    val ticksToRegeneration: Int
}

external interface WithCooldown {
    val cooldown: Int
}

external interface Decaying {
    val ticksToDecay: Int
}

external interface Owned : OwnedNullable {
    override val owner: Owner
}

external interface OwnedNullable {
    val my: Boolean
    val owner: Owner?
}

external interface Owner {
    val username: String
}

external interface Attackable : Identifiable, RoomObjectNotNull {
    val hits: Int
    val hitsMax: Int
}

external interface NavigationTarget
external interface HasPosition : NavigationTarget {
    val pos: RoomPosition
}

external interface IStructure : RoomObjectNotNull, Attackable, Identifiable, OwnedNullable {
    val structureType: StructureConstant

    fun destroy(): ScreepsReturnCode
    fun isActive(): Boolean
    fun notifyWhenAttacked(enabled: Boolean): ScreepsReturnCode
}

external interface StoreOwner : Identifiable, HasPosition {
    val store: Store
}
