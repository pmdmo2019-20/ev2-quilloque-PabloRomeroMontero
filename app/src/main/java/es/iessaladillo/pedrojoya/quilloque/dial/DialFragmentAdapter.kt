package es.iessaladillo.pedrojoya.quilloque.dial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.ContactoSugerido
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dial_fragment_item.view.*

class DialFragmentAdapter :
    ListAdapter<ContactoSugerido, DialFragmentAdapter.ViewHolder>(ContactoSugeridoDiffCallback) {

    var onClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.dial_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactoSugerido = currentList[position]
        holder.bind(contactoSugerido)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(adapterPosition)
            }
        }

        fun bind(contactoSugerido: ContactoSugerido) {
            contactoSugerido.run {
                containerView.lblContactName.text = if (contactName.isBlank()) phoneNumber else contactName
                    containerView.lblPhoneNumber.text = phoneNumber
                    containerView.imgAvatar.setImageDrawable(if (contactName.isBlank()) createAvatarDrawable("?") else createAvatarDrawable(contactName))
            }

        }

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).phoneNumber.toLong()
    }
    object ContactoSugeridoDiffCallback : DiffUtil.ItemCallback<ContactoSugerido>() {
        override fun areItemsTheSame(
            oldItem: ContactoSugerido,
            newItem: ContactoSugerido
        ): Boolean =
            oldItem.contactName == newItem.contactName

        override fun areContentsTheSame(
            oldItem: ContactoSugerido,
            newItem: ContactoSugerido
        ): Boolean =
            oldItem.phoneNumber == newItem.phoneNumber

    }
}