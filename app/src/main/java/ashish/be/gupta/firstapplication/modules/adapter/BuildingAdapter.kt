package ashish.be.gupta.firstapplication.modules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.BuildingListDataBinding
import ashish.be.gupta.firstapplication.modules.model.EntityBuildingModel


class BuildingAdapter(private val data: ArrayList<EntityBuildingModel>,
                      private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BuildingAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val buildingListDataBinding: BuildingListDataBinding) :
        RecyclerView.ViewHolder(buildingListDataBinding.root) {
        fun bind(item: EntityBuildingModel) {
            buildingListDataBinding.buildingDetails = item
            buildingListDataBinding.listener = listener
            buildingListDataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: BuildingListDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.lsv_building, parent, false
        )

        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    interface OnItemClickListener {
        fun onItemClick(entityBuildingModel: EntityBuildingModel)
    }
}