package ashish.be.gupta.firstapplication.modules.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ashish.be.gupta.firstapplication.R
import ashish.be.gupta.firstapplication.databinding.LsvCustomerStbBinding
import ashish.be.gupta.firstapplication.modules.model.EntitySTBModel

class CustomerSTBAdapter(
    private val data: ArrayList<EntitySTBModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CustomerSTBAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val lsvCustomerStbBinding: LsvCustomerStbBinding) :
        RecyclerView.ViewHolder(lsvCustomerStbBinding.root) {
        fun bind(item: EntitySTBModel) {
            lsvCustomerStbBinding.stbModel = item
            lsvCustomerStbBinding.listener = listener
            lsvCustomerStbBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: LsvCustomerStbBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.lsv_customer_stb, parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClick(entitySTBModel: EntitySTBModel)
        fun showPopMenu(view: View, entitySTBModel: EntitySTBModel)
    }

}