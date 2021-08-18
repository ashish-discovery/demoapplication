package ashish.be.gupta.firstapplication.modules.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.LsvSearchCustomerBinding
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerModel

class SearchCustomerAdapter(
    private val data: ArrayList<EntityCustomerModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<SearchCustomerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val customerListDataBinding: LsvSearchCustomerBinding) :
        RecyclerView.ViewHolder(customerListDataBinding.root) {
        fun bind(item: EntityCustomerModel) {
            customerListDataBinding.customerModel = item
            customerListDataBinding.listener = listener
            customerListDataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: LsvSearchCustomerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.lsv_search_customer, parent,
            false
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
        fun onItemClick(entityCustomerModel: EntityCustomerModel)
        fun showPopMenu(view: View, entityCustomerModel: EntityCustomerModel)
    }
}