package ashish.be.gupta.firstapplication.modules.spinneradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.RoomSpinnerDataBinding
import ashish.be.gupta.firstapplication.modules.model.EntityRoomModel

class RoomSpinnerAdapter (val data : ArrayList<EntityRoomModel>) : BaseAdapter() {

    inner class ItemViewHolder(private val roomSpinnerDataBinding : RoomSpinnerDataBinding){
        var view = roomSpinnerDataBinding.root
        fun bind(item : EntityRoomModel){
            roomSpinnerDataBinding.roomDetails = item
            roomSpinnerDataBinding.executePendingBindings()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemViewHolder : ItemViewHolder

        if (convertView == null){
            val view : RoomSpinnerDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.lsv_spinner_room,parent,false)

            itemViewHolder = ItemViewHolder(view)
            itemViewHolder.view = view.root
            itemViewHolder.view.tag = itemViewHolder

        } else {
            itemViewHolder = convertView.tag as ItemViewHolder
        }

        itemViewHolder.bind(data[position])
        return itemViewHolder.view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}