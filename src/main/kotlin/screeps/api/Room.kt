package screeps.api

import screeps.api.structures.Structure
import screeps.api.structures.StructureController
import screeps.api.structures.StructureStorage
import screeps.api.structures.StructureTerminal

abstract external class Room {
    val controller: StructureController?
    val energyAvailable: Int
    val energyCapacityAvailable: Int
    val memory: RoomMemory
    val name: String
    val storage: StructureStorage?
    val terminal: StructureTerminal?
    val visual: RoomVisual

    fun createConstructionSite(x: Int, y: Int, structureType: StructureConstant): ScreepsReturnCode
    fun createConstructionSite(pos: RoomPosition, structureType: StructureConstant): ScreepsReturnCode
    fun createFlag(
        x: Int,
        y: Int,
        name: String = definedExternally,
        color: ColorConstant = definedExternally,
        secondaryColor: ColorConstant = definedExternally
    ): Any
    fun createFlag(
        pos: RoomPosition,
        name: String = definedExternally,
        color: ColorConstant = definedExternally,
        secondaryColor: ColorConstant = definedExternally
    ): Any
    fun <T> find(findConstant: FindConstant<T>, opts: FilterOption<T> = definedExternally): Array<T>
    fun findExitTo(room: String): ExitConstant
    fun findExitTo(room: Room): ExitConstant
    fun findPath(fromPos: RoomPosition, toPos: RoomPosition, opts: FindPathOptions = definedExternally): Array<PathStep>
    fun getPositionAt(x: Int, y: Int): RoomPosition?
    fun lookAt(x: Int, y: Int): Array<RoomPosition.Look>
    fun lookAt(target: RoomPosition): Array<RoomPosition.Look>
    fun <T> lookForAt(type: LookConstant<T>, x: Int, y: Int): Array<T>?
    fun getTerrain(): Terrain

    class Terrain(roomName: String) {
        val roomName: String
        operator fun get(x: Int, y: Int): TerrainMaskConstant
        fun getRawBuffer() : Array<Int>
    }

    interface PathStep {
        var x: Int
        var dx: Int
        var y: Int
        var dy: Int
        var direction: DirectionConstant
    }

    interface CoordinateInRoom {
        val x: Int
        val y: Int
    }

    interface LookAtAreaArrayItem : RoomPosition.Look, CoordinateInRoom

    interface LookCreep : CoordinateInRoom {
        val creep: Creep
    }
    interface LookPowerCreep : CoordinateInRoom {
        val powerCreep: PowerCreep
    }
    interface LookStructure : CoordinateInRoom {
        val structure: Structure
    }
    interface LookTerrain : CoordinateInRoom {
        val terrain: TerrainConstant
    }
    interface LookConstructionSite : CoordinateInRoom {
        val constructionSite: ConstructionSite
    }
    interface LookResource : CoordinateInRoom {
        val resource: Resource
    }
    interface LookTombstone : CoordinateInRoom {
        val tombstone: Tombstone
    }
    interface LookSource : CoordinateInRoom {
        val source: Source
    }
    interface LookMineral : CoordinateInRoom {
        val mineral: Mineral
    }
    interface LookDeposit : CoordinateInRoom {
        val deposit: Deposit
    }
    interface LookFlag : CoordinateInRoom {
        val flag: Flag
    }
    interface LookRuin : CoordinateInRoom {
        val ruin: Ruin
    }

    companion object {
        fun serializePath(path: Array<PathStep>): String
        fun deserializePath(path: String): Array<PathStep>
    }
}

private typealias LookAtAreaResult = Record<Int, Record<Int, Array<RoomPosition.Look>>>

fun Room.lookAtArea(top: Int, left: Int, bottom: Int, right: Int): LookAtAreaResult =
    this.asDynamic().lookAtArea(top, left, bottom, right, false).unsafeCast<LookAtAreaResult>()

fun Room.lookAtAreaAsArray(top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookAtAreaArrayItem> =
    this.asDynamic().lookAtArea(top, left, bottom, right, true).unsafeCast<Array<Room.LookAtAreaArrayItem>>()


fun <T> Room.lookForAtArea(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Record<Int, Record<Int, Array<T>>> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, false)


fun <T: Creep> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookCreep> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: PowerCreep> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookPowerCreep> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Structure> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookStructure> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: TerrainConstant> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookTerrain> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: ConstructionSite> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookConstructionSite> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Resource> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookResource> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Tombstone> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookTombstone> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Source> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookSource> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Mineral> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookMineral> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Deposit> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookDeposit> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Flag> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookFlag> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

fun <T: Ruin> Room.lookForAtAreaAsArray(type: LookConstant<T>, top: Int, left: Int, bottom: Int, right: Int): Array<Room.LookRuin> =
    asDynamic().lookForAtArea(type, top, left, bottom, right, true)

external interface FindPathOptions : Options {
    var ignoreCreeps: Boolean? get() = definedExternally; set(value) = definedExternally
    var ignoreDestructibleStructures: Boolean? get() = definedExternally; set(value) = definedExternally
    var ignoreRoads: Boolean? get() = definedExternally; set(value) = definedExternally
    var costCallback: ((roomName: String, costMatrix: PathFinder.CostMatrix) -> PathFinder.CostMatrix)?
        get() = definedExternally; set(value) = definedExternally

    var maxOps: Int? get() = definedExternally; set(value) = definedExternally
    var heuristicWeight: Double? get() = definedExternally; set(value) = definedExternally
    var serialize: Boolean? get() = definedExternally; set(value) = definedExternally
    var maxRooms: Int? get() = definedExternally; set(value) = definedExternally
    var range: Int? get() = definedExternally; set(value) = definedExternally
    var plainCost: Int? get() = definedExternally; set(value) = definedExternally
    var swampCost: Int? get() = definedExternally; set(value) = definedExternally
}
