package com.example.dhaneshchappidi.notes

import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import com.hussein.startup.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var listnotes=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadquery("%")
    }
    override  fun onResume() {
        super.onResume()
        loadquery("%")
        Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val sv:SearchView= menu!!.findItem(R.id.appbarsearch).actionView as SearchView
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null)
            when(item.itemId){
                R.id.addnote->{
                    var intent= Intent(this,add_notes::class.java)
                    startActivity(intent)
                }
            }
        return super.onOptionsItemSelected(item)
    }
    fun loadquery(title:String){
        try {
            var dBmanager = DBmanager(this)
            var projections = arrayOf("ID", "TITLE", "Description")
            var selectionArgs = arrayOf(title)
            var cursor = dBmanager.Query(projections, "TITLE like ?", selectionArgs, "title")
            listnotes.clear()
            if (cursor.moveToFirst()) {
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val TITLE = cursor.getString(cursor.getColumnIndex("TITLE"))
                    val Description = cursor.getString(cursor.getColumnIndex("Description"))
                    listnotes.add(Note(ID, TITLE, Description))
                } while (cursor.moveToNext())
            }
            var mynotesadapter = MyNotesAdpater(this, listnotes)
            lvNotes.adapter = mynotesadapter
        }
        catch (ex:Exception){}

    }
    inner class  MyNotesAdpater: BaseAdapter {
        var listNotesAdpater = ArrayList<Note>()
        var context: Context? = null

        constructor(context: Context, listNotesAdpater: ArrayList<Note>) : super() {
            this.listNotesAdpater = listNotesAdpater
            this.context = context
        }

        override fun getCount(): Int {
            return listnotes.size
        }

        override fun getItem(p0: Int): Any {
            return listNotesAdpater[p0]
        }

        override fun getItemId(p0: Int): Long {

            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNotesAdpater[p0]
            myView.tvTitle.text = myNote.nodeName
            myView.tvDes.text = myNote.nodeDes
            myView.ivDelete.setOnClickListener {View.OnClickListener {
                var dbManager = DBmanager(this.context!!)
                val selectionArgs = arrayOf(myNote.nodeID.toString())
                dbManager.Delete("ID=?", selectionArgs)
                loadquery("%")
            }}
            myView.ivEdit.setOnClickListener{
                GoToUpdate(myNote)
            }
            return myView
        }
    }
    fun GoToUpdate(note:Note){
        var intent=  Intent(this,add_notes::class.java)
        intent.putExtra("ID",note.nodeID)
        intent.putExtra("name",note.nodeName)
        intent.putExtra("des",note.nodeDes)
        startActivity(intent)
    }
}