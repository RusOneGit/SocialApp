package rus.one.app.posts.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAllPostsFlow(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostEntity>)

    @Update
    suspend fun update(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :postId")
    suspend fun updateContentById(postId: Long, content: String)

    //  Больше не нужен, используем insert с onConflict = OnConflictStrategy.REPLACE
    //fun save(post: PostEntity) =
    //    if (post.id != 0L) changeContent(post.id, post.content) else insert(post)

    @Query(
        """
        UPDATE PostEntity SET
        likesCount = likesCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    suspend fun likeByID(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query("SELECT * FROM PostEntity WHERE id < 0")
    suspend fun getUnsyncedPosts(): List<PostEntity>
}
