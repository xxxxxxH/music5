package jboster.poshe.one.rodeo

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import jboster.poshe.one.R
import jboster.poshe.one.entity.LocalMusicEntity
import jboster.poshe.one.event.Event
import org.greenrobot.eventbus.EventBus
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@SuppressLint("NonConstantResourceId")
@LayoutId(R.layout.item_song)
class Aiteng(view: View) : ItemViewHolder<LocalMusicEntity>(view) {

    @ViewId(R.id.itemRoot)
    lateinit var itemRoot:RelativeLayout

    @ViewId(R.id.cover)
    lateinit var cover:ImageView

    @ViewId(R.id.name)
    lateinit var name:TextView

    @ViewId(R.id.author)
    lateinit var author:TextView

    override fun onSetValues(item: LocalMusicEntity, positionInfo: PositionInfo?) {
        Glide.with(context).load(item.img_uri).into(cover)
        name.text = item.title
        author.text = item.artist
        itemRoot.setOnClickListener { EventBus.getDefault().post(Event("item", item)) }
    }
}