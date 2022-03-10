package RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName ="category")
data class Category(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "type") val catType: String?,

)

