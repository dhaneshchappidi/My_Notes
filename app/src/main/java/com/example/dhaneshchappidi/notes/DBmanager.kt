package com.example.dhaneshchappidi.notes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DBmanager{
    val dbname="DBNotes"
    val dbtable="notes"
    val colID="ID"
    val coltitle="TITLE"
    val coldes="Description"
    val dbversion=1
    val sqlcreatetable="CREATE TABLE IF NOT EXISTS "+ dbtable + " (" + colID +" INTEGER PRIMARY KEY, "+
            coltitle +" TEXT, "+ coldes +" TEXT );"
    var sqlDB: SQLiteDatabase? =null
    constructor(context: Context){
        var db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context: Context? =null
        constructor(context: Context?):super(context,dbname,null,dbversion) {
            this.context = context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlcreatetable)
            Toast.makeText(context,"Database created",Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbtable")
        }
    }
    fun Insert(values:ContentValues):Long{
        val ID=sqlDB!!.insert(dbtable,"",values)
        return ID
    }
    fun  Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor {
        val qb= SQLiteQueryBuilder()
        qb.tables=dbtable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }
    fun Delete(selection:String,selectionArgs:Array<String>):Int{
        val count=sqlDB!!.delete(dbtable,selection,selectionArgs)
        return  count
    }
    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{
        val count=sqlDB!!.update(dbtable,values,selection,selectionargs)
        return count
    }
}