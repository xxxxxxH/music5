package jboster.poshe.one.ui

import android.media.MediaPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.entity.LocalMusicEntity
import jboster.poshe.one.event.Event
import jboster.poshe.one.rodeo.Aiteng
import jboster.poshe.one.utils.PlayerUtils
import jboster.poshe.one.utils.getLocalMusic
import kotlinx.android.synthetic.main.bendi_layout.*
import kotlinx.android.synthetic.main.local_bottom.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uk.co.ribot.easyadapter.EasyRecyclerAdapter

class BendiYemian:Outta(R.layout.bendi_layout) , MediaPlayer.OnCompletionListener{

    private val playerUtils by lazy {
        PlayerUtils.get()
    }

    override fun start() {
        super.start()
        EventBus.getDefault().register(this)
        getLocalMusic {
            if (it.size > 0){
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.adapter = EasyRecyclerAdapter<LocalMusicEntity>(
                    this,
                    Aiteng::class.java,
                    it
                )
            }
        }
        play.setOnClickListener {
            if (playerUtils.isPlaying()){
                playerUtils.pause()
                play.setImageResource(R.mipmap.lo10)
            }else{
                playerUtils.resume()
                play.setImageResource(R.mipmap.lo9)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: Event){
        if (e.getMessage()[0] == "item"){
            val data = e.getMessage()[1] as LocalMusicEntity
            songName.text = data.title
            songAuthor.text = data.artist
            Glide.with(this).load(data.img_uri).into(coverImage)
            playerUtils.getMediaPlayer().setOnCompletionListener(this)
            playerUtils.musicPath = data.path
            playerUtils.setPath()
            playerUtils.start()
            play.setImageResource(R.mipmap.lo9)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (playerUtils.isPlaying()){
            playerUtils.stop()
        }
        EventBus.getDefault().unregister(this)
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }
}