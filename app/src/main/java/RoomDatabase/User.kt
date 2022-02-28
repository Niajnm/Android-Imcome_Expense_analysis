package RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName ="user")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "day") val day: String?,
//    @ColumnInfo(name = "month") val month: String?,
//    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "money") val money: Int?,
     @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "cash_type") val bank: String?,
    @ColumnInfo(name = "Tag") val tag: String?
)

