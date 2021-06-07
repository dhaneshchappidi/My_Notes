package com.example.dhaneshchappidi.notes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.net.IDN

class add_notes : AppCompatActivity() {
    val dbTable="Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        try{
            var bundle:Bundle= intent.extras!!
            id=bundle.getInt("ID",0)
            if(id!=0) {
                etTitle.setText(bundle.getString("name") )
                etDes.setText(bundle.getString("des") )
            }
        }catch (ex:Exception){}
        button.setOnClickListener(View.OnClickListener {
            var dbmanager = DBmanager(this)
            var values = ContentValues()
            values.put("TITLE", etTitle.text.toString())
            values.put("Description", etDes.text.toString())
            if(id==0) {
                val ID = dbmanager.Insert(values)
                if (ID > 0) {
                    Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }   }
                else {
                var selectionArs = arrayOf(id.toString())
                val ID = dbmanager.Update(values, "ID=?", selectionArs)
                if (ID > 0) {
                    Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}