package RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName ="display",indices=[Index(value=["day"],unique=true)])
data class DisplayItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "day") val day: String?,
//    @ColumnInfo(name = "month") val month: String?,
//    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "income") val income: Int?,
    @ColumnInfo(name = "expense") val expense: Int?,
     @ColumnInfo(name = "cash_type") val bank: String?
)

