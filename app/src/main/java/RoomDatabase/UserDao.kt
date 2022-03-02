package RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM display ORDER BY uid DESC")
    fun getAllDisplay(): List<DisplayItem>

    @Query("SELECT * FROM user ORDER BY uid DESC")
    fun getAllRecent(): List<User>

    @Query("SELECT * FROM display WHERE day LIKE '%' || :monthYr || '%' ORDER BY uid DESC")
    fun getAllbyMonth(monthYr: String?): MutableList<DisplayItem>

    @Query("SELECT * FROM user WHERE day LIKE '%' || :monthYr || '%'")
    fun getAllListbyMonth(monthYr: String?): MutableList<User>

    @Query("SELECT * FROM user WHERE day IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE day IN (:userIds)")
    fun loadAllBbyDate(userIds: String): List<User>


    @Query("SELECT * FROM display WHERE day IN (:userIds)")
    fun getUniqueDate(userIds: String): List<DisplayItem>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Insert
    fun insertAllDisplay(vararg item: DisplayItem)

    @Query("UPDATE display SET income =income +:incm WHERE day = :oldday")
    fun updateDisplayIn(incm: Int, oldday: String?)

    @Query("UPDATE display SET expense =expense +:expn WHERE day = :oldday")
    fun updateDisplayExpense(expn: Int, oldday: String?)

    @Delete
    fun delete(user: User)
}