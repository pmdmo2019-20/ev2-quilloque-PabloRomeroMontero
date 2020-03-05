package es.iessaladillo.pedrojoya.quilloque.dial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Contact
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.ContactoSugerido
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.*
import kotlinx.android.synthetic.main.contacts_fragment_item.imgAvatar
import kotlinx.android.synthetic.main.contacts_fragment_item.lblName
import kotlinx.android.synthetic.main.contacts_fragment_item.lblPhoneNumber

class ContactFragmentAdapter :
    ListAdapter<Contact, ContactFragmentAdapter.ViewHolder>(ContactDiffCallback) {

    var onClickListener: ((Int) -> Unit)? = null
    var delete: ((Int) -> Unit)? = null
    var callVideollamada: ((Int) -> Unit)? = null
    var callLlamada: ((Int) -> Unit)? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.contacts_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = currentList[position]
        holder.bind(contact)
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

        }

        fun bind(contact: Contact) {
            contact.run {
            imgAvatar.setImageDrawable(createAvatarDrawable(contact.name))
            lblName.text = contact.name
            lblPhoneNumber.text = contact.phoneNumber

            }

        }

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }
    object ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean =
            oldItem.phoneNumber == newItem.phoneNumber && oldItem.name == newItem.name

    }
}