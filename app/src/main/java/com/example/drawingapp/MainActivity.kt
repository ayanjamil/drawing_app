package com.example.drawingapp
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private var drawingclass:drawingclass?=null//initiallizing the class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)// It specifies the UI components that
        // will be displayed on the screen.
        drawingclass=findViewById(R.id.drawing_view)
        //drawingclass?.setbrushsize(20.toFloat())// defining it for the initial cond <<redflag >> maybe



        val ib_brush:ImageButton=findViewById(R.id.brush)
        ib_brush.setOnClickListener {

            brushchoosrdialog()
            Log.d("brushcalled","brush size change button called")

        }
    }


    //check this function becuase the lecture says thta it should be
    // val smallbtn =brushDialog.smallbrushbtn
    private fun brushchoosrdialog(){
        val brushDialog=Dialog(this)
        brushDialog.setContentView(R.layout.brushdg)// this takes us to the brush.xml
        brushDialog.setTitle("Brush size: ")
        val smallbtn = brushDialog.findViewById<ImageButton>(R.id.smallbrushbtn)//this is a shady part see the lec
        smallbtn.setOnClickListener {
            drawingclass?.setbrushsize(1.toFloat())
            Log.d("smallbrush","small brush button is clicled in from the dialog and input is taken")
            brushDialog.dismiss()
        }
        val midbtn = brushDialog.findViewById<ImageButton>(R.id.midbrushbtn)//this is a shady part see the lec
        midbtn.setOnClickListener {
            drawingclass?.setbrushsize(10.toFloat())
            Log.d("midum_brush","midium brush button is clicled in from the dialog and input is taken")
            brushDialog.dismiss()
        }
        val largebtn = brushDialog.findViewById<ImageButton>(R.id.largebrushbtn)//this is a shady part see the lec
        largebtn.setOnClickListener {
            drawingclass?.setbrushsize(30.toFloat())
            Log.d("large_brush","large brush button is clicled in from the dialog and input is taken")
            brushDialog.dismiss()
        }
        brushDialog.show()

    }
}


//problems is
//1. that Dialog is not working it is not able to open the brushdq.xml ===>solved
// 2. the privious part of the program is not beging
// the problem could be that it is not able to initialize the brsuh size
// this is not the probelm = that the image button is not causing drawing vies not to function
// we are not able to draw so this must be a problem in the beining or main part of code
// or there could be some problem which is not allowing to draw because of this dialog part
