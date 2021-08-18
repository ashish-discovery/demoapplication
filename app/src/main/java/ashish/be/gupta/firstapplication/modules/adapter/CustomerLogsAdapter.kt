package ashish.be.gupta.firstapplication.modules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.LsvCustomerLogsBinding
import ashish.be.gupta.firstapplication.modules.model.EntityCustomerLogsModel

class CustomerLogsAdapter(
    private val data: ArrayList<EntityCustomerLogsModel>
) : RecyclerView.Adapter<CustomerLogsAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(private val lsvCustomerLogsBinding: LsvCustomerLogsBinding) :
        RecyclerView.ViewHolder(lsvCustomerLogsBinding.root) {
        fun bind(item: EntityCustomerLogsModel) {
            lsvCustomerLogsBinding.customerLogsModel = item
            lsvCustomerLogsBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: LsvCustomerLogsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.lsv_customer_logs, parent, false
        )

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}