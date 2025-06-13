package rus.one.app.profile

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity ORDER BY id ASC")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?

//    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
//    suspend fun getUserByUsername(username: String): UserEntity?

    @Update
    suspend fun update(user: UserEntity)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    suspend fun deleteById(id: Long)
}