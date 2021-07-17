package com.test.beln.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "news")
data class News @JvmOverloads constructor(
    @ColumnInfo(name = "id") @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "image_url") val imageUrl: String = "",
    @ColumnInfo(name = "deleted") val _deleted: Int = 1
) {
    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isDeleted: Boolean
        get() = _deleted == 0

    val isEmpty
        get() = title.isEmpty() && description.isEmpty()



}
