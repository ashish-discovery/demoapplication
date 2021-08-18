package ashish.be.gupta.firstapplication.modules.spinneradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.CustomSpinnerDataBinding
import ashish.be.gupta.firstapplication.modules.model.EntityBuildingModel
import kotlin.collections.ArrayList

class BuildingSpinnerAdapter(var data: ArrayList<EntityBuildingModel>) : BaseAdapter() {

    inner class ItemViewHolder(private val customSpinnerDataBinding: CustomSpinnerDataBinding) {
        var view = customSpinnerDataBinding.root
        fun bind(item: EntityBuildingModel) {
            customSpinnerDataBinding.buildingDetails = item
            customSpinnerDataBinding.executePendingBindings()
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemViewHolder: ItemViewHolder

        if (convertView == null) {
            val view: CustomSpinnerDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.lsv_spinner_building, parent, false
            )

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