package com.lacalera.testapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lacalera.testapp.data.local.converter.TimeConverter
import com.lacalera.testapp.domain.model.Character
import java.util.Date

@TypeConverters(TimeConverter::class)
@Entity(tableName = CharacterEntity.TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "createDate") val createDate: Date = Date(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "flag")val flag: Int ?= CharacterEntity.PENDING,
) {
    companion object {
        const val TABLE_NAME = "CharacterEntity"
        const val PENDING = 0    //PENDIENTE
        const val TRUNK = -1     //TRUNCO
        const val OPERATING = 1  //OPERANDO
        const val FINALIZED = 2  //FINALIZADO
    }
}


// Mapeo desde Domain hacia Entity
fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id.toLong(),
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        image = this.image,
        url = this.url,
        created = this.created,
        flag = CharacterEntity.PENDING
    )
}

// Mapeo desde Entity hacia Domain
fun CharacterEntity.toDomain(): Character {
    return Character(
        id = this.id.toInt(),
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        image = this.image,
        url = this.url,
        created = this.created,
    )
}
