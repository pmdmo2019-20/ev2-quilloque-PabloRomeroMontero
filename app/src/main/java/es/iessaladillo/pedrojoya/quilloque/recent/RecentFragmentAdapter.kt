package es.iessaladillo.pedrojoya.quilloque.recent


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.getCallTypeIcon
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.LlamadaContacto
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dial_fragment_item.view.imgAvatar
import kotlinx.android.synthetic.main.dial_fragment_item.view.lblPhoneNumber
import kotlinx.android.synthetic.main.recent_fragment_item.*
import kotlinx.android.synthetic.main.recent_fragment_item.view.*

class RecentFragmentAdapter :
    ListAdapter<LlamadaContacto, RecentFragmentAdapter.ViewHolder>(LlamadaContactoDiffCallback) {

    var onClickListener: ((Int) -> Unit)? = null
    var delete: ((Int) -> Unit)? = null
    var callVideollamada: ((Int) -> Unit)? = null
    var callLlamada: ((Int) -> Unit)? = null
    var addContact: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recent_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = currentList[position]
        holder.bind(student)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).callId
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(adapterPosition)
            }

            btnDelete.setOnClickListener {
                delete?.invoke(adapterPosition)
            }


            btnCall.setOnClickListener {
                callLlamada?.invoke(adapterPosition)
            }
            btnVideoCall.setOnClickListener {
                callVideollamada?.invoke(adapterPosition)
            }
            lblCreateContact.setOnClickListener {
                addContact?.invoke(adapterPosition)
            }
        }

        //ESTO
        fun bind(llamadaContacto: LlamadaContacto) {
            llamadaContacto.run {
                containerView.imgAvatar.setImageDrawable(createAvatarDrawable(if (llamadaContacto.contactName.isNullOrBlank()) "?" else llamadaContacto.contactName))
                containerView.lblName.text =
                    if (llamadaContacto.contactName.isNullOrBlank()) phoneNumber else llamadaContacto.contactName
                containerView.lblPhoneNumber.text = phoneNumber
                containerView.imgCallType.setImageResource(getCallTypeIcon(llamadaContacto.type))
                containerView.lblDate.text = date
                containerView.lblTime.text = time
                containerView.lblCreateContact.visibility =
                    if (llamadaContacto.contactName.isNullOrBlank()) View.VISIBLE else View.VISIBLE

            }

        }

    }

    object LlamadaContactoDiffCallback : DiffUtil.ItemCallback<LlamadaContacto>() {

        override fun areItemsTheSame(oldItem: LlamadaContacto, newItem: LlamadaContacto): Boolean {
            return oldItem.callId == newItem.callId
        }

        override fun areContentsTheSame(
            oldItem: LlamadaContacto,
            newItem: LlamadaContacto
        ): Boolean {
            return oldItem.contactId == newItem.contactId && oldItem.contactName == newItem.contactName
                    && oldItem.phoneNumber == newItem.phoneNumber && oldItem.time == newItem.time
                    && oldItem.type == newItem.type && oldItem.date == newItem.date
        }

    }
}