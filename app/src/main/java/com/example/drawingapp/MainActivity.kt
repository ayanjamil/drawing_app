package com.example.drawingapp
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var drawingclass:drawingclass?=null//initiallizing the class
    private var mImageButtonCurrentPaint:ImageButton?=null
    val opengallerylauncher:ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == RESULT_OK && result.data!=null){
            val imagebackground:ImageView =findViewById(R.id.iv_background)
            imagebackground.setImageURI(result.data?.data)// result.data is the location and
            // again .data gives the image


        }



    }
    val requestpermission:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                permission ->
            permission.entries.forEach {
                val permissionname= it.key
                val isgrant = it.value
                if(isgrant){
                    Toast.makeText(
                        this@MainActivity,
                        "permission granted now you can read Stoarages",
                        Toast.LENGTH_LONG
                    ).show()
                    val pickintent =Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    // this will first open the gallery as intnet is used and it pcikintent will store the url
                    // of selected image
                    opengallerylauncher.launch(pickintent)



                }else{
                    if(permissionname==Manifest.permission.READ_EXTERNAL_STORAGE){
                        Toast.makeText(
                            this@MainActivity,
                            "Sorry permission is denied", // FLAG//
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)// It specifies the UI components that
        // will be displayed on the screen.
        drawingclass=findViewById(R.id.drawing_view)
        //drawingclass?.setbrushsize(20.toFloat())// defining it for the initial cond <<redflag >> maybe
        val linear_layoutpaint_colors=findViewById<LinearLayout>(R.id.ll_paint_color)
        mImageButtonCurrentPaint=linear_layoutpaint_colors[2] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
            // so what this part does is that it changes the colors icon to full from half when it is clicked
        )


        val ib_brush:ImageButton=findViewById(R.id.brush)
        ib_brush.setOnClickListener {

            brushchoosrdialog()
            Log.d("brushcalled","brush size change button called")

        }
        val undo_btn:ImageButton=findViewById(R.id.undo_btn)
        undo_btn.setOnClickListener {
            drawingclass?.onclick_undo() //drawing clas is nullable sow e need to add ?

//            brushchoosrdialog()
//            Log.d("brushcalled","brush size change button called")

        }
        val ibgallery:ImageButton=findViewById(R.id.gallery)
        ibgallery.setOnClickListener {
            request_storage_permission()

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
    fun paint_clicked(view:View){
        //view. This parameter is used to handle the click event for multiple buttons by assigning it as an OnClickListener to each of these buttons
        Toast.makeText(this, "Clicked paint", Toast.LENGTH_LONG).show()
        if(view!==mImageButtonCurrentPaint){
            val imagebuttom = view as ImageButton
            val colortag =imagebuttom.tag.toString()
            drawingclass?.setcolor(colortag)
            imagebuttom!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
                // so what this part does is that it changes the colors icon to full from half when it is clicked
            )// the button we presesd should become pressed one
            mImageButtonCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_normal)
                // so what this part does is that it changes the colors icon to full from half when it is clicked
            ) // the  unselected button should get notmal one
            mImageButtonCurrentPaint =view
        }

    }


    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
    private fun request_storage_permission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)// we needed to pass in the activity and the permission
        ){
            showRationaleDialog("Drawing_App","This app need to acces your location")
        }else{
            requestpermission.launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE

            ))
        }

    }



}


//problems is
//1. that Dialog is not working it is not able to open the brushdq.xml ===>solved
// 2. the privious part of the program is not beging
// the problem could be that it is not able to initialize the brsuh size
// this is not the probelm = that the image button is not causing drawing vies not to function
// we are not able to draw so this must be a problem in the beining or main part of code
// or there could be some problem which is not allowing to draw because of this dialog part