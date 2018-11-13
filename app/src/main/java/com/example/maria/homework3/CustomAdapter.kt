package com.example.maria.homework3

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

class CustomAdapter :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        private var dataSet: ArrayList<Message> = ArrayList()
        lateinit var mTextView: TextView
        lateinit var mEditText: EditText
        lateinit var context: Context

        private var user1_user1Quantity:Int=0
        private var user1_user2Quantity:Int=0

        inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener  {
            val textView: TextView

            init {
                view.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
                textView = view.findViewById(R.id.textView)
                view.setOnCreateContextMenuListener(this)
            }

            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                menu?.add(0, 1, 0, "Delete")?.setOnMenuItemClickListener {
                    removeItem(adapterPosition)
                    return@setOnMenuItemClickListener true
                }
                menu?.add(0, 2, 0, "Edit")?.setOnMenuItemClickListener {
                    context = itemView.context
                    editItem(adapterPosition, v)
                    return@setOnMenuItemClickListener true
                }
                menu?.add(0, 3, 0, "Close")?.setOnMenuItemClickListener {
                    return@setOnMenuItemClickListener false
                }
            }
        }

    internal inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user1Quantity: TextView = view.findViewById(R.id.user1_textView)
        val user2Quantity: TextView = view.findViewById(R.id.user2_textView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(getUserLayout(viewType), viewGroup, false)
            HeaderViewHolder(v)

        } else {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(getUserLayout(viewType), viewGroup, false)
            ViewHolder(v)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        when (holder){
          is ViewHolder -> {
                holder.textView.text = dataSet[position-1].messageContent
          }
            is HeaderViewHolder -> {
                holder.user1Quantity.text = "User1: "+user1_user1Quantity.toString()
                holder.user2Quantity.text = "User2: "+user1_user2Quantity.toString()
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size+1

    override fun getItemViewType(position: Int): Int {
        if (position==0)
            return TYPE_HEADER
        if(position>0){
        val currentMessage: Message = dataSet[position-1]
        return when(currentMessage.getUser){
            1->{
                dataSet[position-1].getUser
            }
            else->{
                dataSet[position-1].getUser
            }
        }
        }
        return 3
    }

    private fun getUserLayout(viewType: Int): Int {
        return when (viewType) {
            0 -> R.layout.header_item
            1 -> R.layout.first_user_row_item
            else -> R.layout.second_user_row_item
        }
    }

   fun addNew(user: Int ,message: String){
       dataSet.add(Message(user+1,message))
       notifyItemInserted(dataSet.size+1)
       when(user){
           0 -> user1_user1Quantity++
           1 -> user1_user2Quantity++
       }
       notifyDataSetChanged()
   }

    private fun removeItem(position: Int) {
    var user=dataSet[position-1].getUser
        dataSet.removeAt(position-1)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataSet.size)
    when(user){
        1 -> user1_user1Quantity--
        2 -> user1_user2Quantity--
    }
    notifyDataSetChanged()
    }

    private fun editItem(position: Int, view: View?) {

        if (view != null) {
            mTextView = view.findViewById(R.id.textView)
            mTextView.visibility = View.GONE
            mEditText=view.findViewById(R.id.editText)
            mEditText.setText(mTextView.text.toString())
            mEditText.visibility = View.VISIBLE
            mEditText.requestFocus()
            showKeyboard()
            mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
            mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            mEditText.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        Log.d(TAG, "${mEditText.text}")
                        mTextView.text=mEditText.text
                        mEditText.visibility=View.GONE
                        mTextView.visibility = View.VISIBLE
                        dataSet[position-1].messageContent=mEditText.text.toString()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showKeyboard(){
        mEditText.postDelayed({
            val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(mEditText, 0)
        }, 100)
    }

    companion object {
        private val TAG = "CustomAdapter"
        private val TYPE_HEADER = 0
    }
}
