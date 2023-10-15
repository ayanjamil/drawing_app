package com.example.drawingapp


import android.content.Context
import android.graphics.* // added new from git
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class drawingclass (context: Context, attrs:AttributeSet) : View(context,attrs) {
    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 20.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas? = null
    private val mPaths = ArrayList<CustomPath>() //git this helps in storing the curves drawn

    // Paint,Canvas and Bitmap are all classes which are predifined in this
    // and to use it we have to import them
    // all this is from graphics library
    // this is a class and we have defined thise variables which we can
    // use futhure out of this class with convient
    init {
        setupDrawing()// this class was intitiallized in the main the firstfew lines
    }

    private fun setupDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        Log.d("brushsize","the brush size intitialy is $mBrushSize")
        mDrawPaint?.color = color//git
        mDrawPaint?.style = Paint.Style.STROKE
        mDrawPaint?.strokeJoin = Paint.Join.ROUND
        mDrawPaint?.strokeCap = Paint.Cap.ROUND
        Log.d("setupDrawing_called", "settupdrawing is called")

        //check this one if it is not added int he furthure videos thaen that means we were
        // doing wrong it was before mDrwapaint instead of McanvasPaint
        mCanvasPaint =
            Paint(Paint.DITHER_FLAG)//edited from git this maybe was wrong which was causing bug
        //mBrushSize=20.toFloat() // we dont need this now as we already did this is in the function below
        //this all are the functions of Paint library and we can define this is jsu
        // mDrawPaint is a object of class Paint
        //The strokeJoin property specifies how line segments and arcs are joined together.
        // There are three possible stroke join styles
        //mDrawPaint!!.strokeJoin=Paint.Join.ROUND sets the stroke join style of the mDrawPaint object to ROUND
        //You can use the mDrawPaint!!.color=color code to set the color of the mDrawPaint
        // object to any color you want
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!) //!! says that this is not null

    }   // each time the /**/size of the screen is chaneged and it is called when
    // the screen is inflated opens our view as display our drwainf class

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvasBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, mCanvasPaint)
        }//git check if it is present in the furthure lectures
        for (p in mPaths) {
            mDrawPaint?.strokeWidth = p.brushThickness
            mDrawPaint?.color = p.color
            canvas.drawPath(p, mDrawPaint!!)
        }//iterating through a list of Path objects stored in the mPaths collection
        // and drawing each Path on the canvas using a specified paint (mDrawPaint)
        // git
        if (!mDrawPath!!.isEmpty) {
            mDrawPaint?.strokeWidth = mDrawPath!!.brushThickness //mDrawPath is of type custom pathe
            // that is why we can use the brushThickenss here
            mDrawPaint?.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath?.color = color
                mDrawPath?.brushThickness = mBrushSize
                mDrawPath?.reset()
                mDrawPath?.moveTo(touchX, touchY)
            }

            MotionEvent.ACTION_MOVE -> {
                mDrawPath?.lineTo(touchX, touchY)
            }

            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!) //GIT //adds to the list of lines when action is up

                mDrawPath = CustomPath(color, mBrushSize)
            }

            else -> return false
        }
        invalidate()
        return true
    }


    fun setbrushsize(newsize: Float) {
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newsize, resources.displayMetrics
        )
        // so this DIP is the unit here and we are doing this because we want the size of the brush to
        // be in propotion to that of the screen
        mDrawPaint!!.strokeWidth = mBrushSize
    }
    fun setcolor(newcolor:String){
        color=Color.parseColor(newcolor)// this is how we passed the newcolor to the variabel color
        mDrawPaint!!.color=color // finally assigning

    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }
}
