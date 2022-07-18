package com.task.noteapp.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.databinding.ActivityEditNoteBinding
import com.task.noteapp.model.Notes
import com.task.noteapp.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/*Using viewbinding through xml layout*/
@AndroidEntryPoint
class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    lateinit var viewModel:NotesViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Here we can avoid naming the layout xml file by inflating owner
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)


        val type = intent.getStringExtra("type")
        if (type.equals("Edit")) {
            val noteTitles = intent.getStringExtra("title")
            val noteDesc = intent.getStringExtra("description")
            noteID = intent.getIntExtra("noteID", -1)
            binding.apply {
                addUpdate.setText("Update")
                noteTitle.setText(noteTitles)
                noteDescription.setText(noteDesc)
            }


        } else {
            binding.addUpdate.setText("Save")

        }
        binding.addUpdate.setOnClickListener {
            val noteTitles = binding.noteTitle.text.toString()
            val noteDesc = binding.noteDescription.text.toString()

            if(type.equals("Edit")) {
                if (noteTitles.isNotEmpty() && noteDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updatedNote = Notes(noteTitles, noteDesc, currentDate)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note is Updated", Toast.LENGTH_LONG).show()

                }
            }
                else {
                       val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDate:String = sdf.format(Date())
                        val updatedNote = Notes(noteTitles, noteDesc,currentDate)
                        updatedNote.id = noteID
                        viewModel.addNote(Notes(noteTitles,noteDesc,currentDate))
                        Toast.makeText(this,"Note is Added", Toast.LENGTH_LONG).show()

                }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            }


        }


    }
