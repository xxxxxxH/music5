package jboster.poshe.one.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hjq.permissions.XXPermissions
import jboster.poshe.one.entity.LocalMusicEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val testLoginUrl = "https://m.facebook.com/"



fun AppCompatActivity.huoQuQuanXian(jieguo: (Boolean) -> Unit) {
    XXPermissions.with(this).permission(permissions).request { _, result ->
        jieguo(result)
    }
}

fun AppCompatActivity.xiayigeYemian(clazz: Class<*>, guangbuguandangqianyiemian:Boolean = false){
    startActivity(Intent(this, clazz))
    if (guangbuguandangqianyiemian){
        finish()
    }
}


@SuppressLint("Recycle")
fun AppCompatActivity.getLocalMusic(r: (ArrayList<LocalMusicEntity>) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        val result = ArrayList<LocalMusicEntity>()
        val selectionStatement = "is_music=1 AND title != ''"
        val cur = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                "_id",
                "title",
                "artist",
                "album",
                "duration",
                "track",
                "artist_id",
                "album_id",
                "_data",
                "_size",
                "mime_type"
            ),
            selectionStatement,
            null,
            "duration DESC"
        )
        try {
            cur?.let {
                if (it.moveToFirst()) {
                    do {
                        val id: Long = it.getLong(0)
                        val title: String = it.getString(1)
                        val artist: String = it.getString(2)
                        val album: String = it.getString(3)
                        val duration: Int = it.getInt(4)
                        val trackNumber: Int = it.getInt(5)
                        val artistId: Long = it.getInt(6).toLong()
                        val albumId: Long = it.getLong(7)
                        val data: String = it.getString(8)
                        val size: String = it.getString(9)
                        if (!it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE))
                                .contains("audio/amr") && !it.getString(
                                it.getColumnIndexOrThrow(
                                    MediaStore.Audio.Media.MIME_TYPE
                                )
                            ).contains("audio/aac")
                        ) {
                            val entity = LocalMusicEntity(
                                id,
                                album,
                                albumId,
                                artist,
                                duration.toLong(),
                                getImgUri(albumId),
                                data,
                                title,
                                "",
                                0,
                                size,
                                trackNumber,
                                artistId,
                                0
                            )
                            result.add(entity)
                        }
                    } while (it.moveToNext())
                }
                it.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cur?.close()
        } finally {
            cur?.close()
        }
        withContext(Dispatchers.Main) {
            r(result)
        }
    }
}

fun getImgUri(album_id: Long): Uri? {
    return try {
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"),
            album_id
        )
    } catch (e: Exception) {
        null
    }
}