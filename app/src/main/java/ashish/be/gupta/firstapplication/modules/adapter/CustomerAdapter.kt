package ashish.be.gupta.firstapplication.modules.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.FloorListDataBinding
import ashish.be.gupta.firstapplication.databinding.LsvCustomerBinding
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerModel

class CustomerAdapter(
    private val data: ArrayList<EntityCustomerModel>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TYPE_FLOOR = 1
    private var TYPE_DATA = 2

    inner class ItemViewHolder(private val customerListDataBinding: LsvCustomerBinding) :
        RecyclerView.ViewHolder(customerListDataBinding.root) {
        fun bind(item: EntityCustomerModel) {
            customerListDataBinding.customerModel = item
            customerListDataBinding.listener = listener
            customerListDataBinding.executePendingBindings()
        }
    }

    inner class FloorItemViewHolder(private val floorListDataBinding: FloorListDataBinding) :
        RecyclerView.ViewHolder(floorListDataBinding.root) {
        fun bind(item: String) {
            floorListDataBinding.floorName = item
            floorListDataBinding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_FLOOR) {

            val view : FloorListDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.lsv_floor, parent, false
            )

            FloorItemViewHolder(view)

        } else {
            val view: LsvCustomerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.lsv_customer, parent, false
            )

            ItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (data[position].isDisplayFloor) {
            (holder as FloorItemViewHolder).bind(data[position].floorName.toString())
        } else {
            (holder as ItemViewHolder).bind(data[position])
        }

    }

    override fun getItemViewType(position: Int): Int {

        return if (data[position].isDisplayFloor) {
            TYPE_FLOOR
        } else {
            TYPE_DATA
        }

    }

    interface OnItemClickListener {
        fun onItemClick(entityCustomerModel: EntityCustomerModel)
        fun showPopMenu(view: View, entityCustomerModel: EntityCustomerModel)
    }

}