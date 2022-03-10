package RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName ="paymentType")
data class PaymentType(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "type") val payType: String?,

)

