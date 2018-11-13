package com.example.maria.homework3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter: CustomAdapter
    private var message: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        users_radio_group.check(first_user_rb.id)
        adapter = CustomAdapter()
        rv_message_list.layoutManager = LinearLayoutManager(this)

        rv_message_list.addItemDecoration(
                SampleDecoration(
                        this
                )
        )
        rv_message_list.adapter = adapter
        btn_add.setOnClickListener {
            var radioButtonID = users_radio_group.checkedRadioButtonId
            val radioButton: View = users_radio_group.findViewById(radioButtonID);
            val idx = users_radio_group.indexOfChild(radioButton)

            message=message_edit_text.text.toString()
            if(message != ""){
                adapter.addNew(idx, message)
                rv_message_list.post { Runnable {
                    // Do something here
                    rv_message_list.scrollToPosition(adapter.itemCount - 1)
                    message_edit_text.setText("")
                }.run()
                }
            }
        }
    }
}
