package com.example.drawingapp


import android.content.Context
import android.graphics.* // added new from git
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class drawingclass (context: Context, attrs:AttributeSet) : View(context,attrs){
    private var mDrawPath:CustomPath?=null
    private var mCanvasBitmap:Bitmap?=null
    private var mDrawPaint:Paint?=null
    private var mCanvasPaint: Paint?=null
    private var mBrushSize:Float=0.toFloat()
    private var color = Color.WHITE
    private var canvas:Canvas?=null
    private val mPaths = ArrayList<CustomPath>() //git this helps in storing the curves drawn
    // Paint,Canvas and Bitmap are all classes which are predifined in this and to use it we have to import them
    // all this is from graphics library
    // this is a class and we have defined thise variables which we can use futhure out of this class with convient
    init {
        setupDrawing()
    }
    private fun setupDrawing(){
        mDrawPaint=Paint()
        mDrawPath=CustomPath(color,mBrushSize)
        mDrawPaint?.color = color//git
        mDrawPaint?.style=Paint.Style.STROKE
        mDrawPaint?.strokeJoin=Paint.Join.ROUND
        mDrawPaint?.strokeCap=Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)//edited from git this maybe was wrong which was causing bug
        mBrushSize=20.toFloat()
        //this all are the functions of Paint library and we can define this is jsu
        // mDrawPaint is a object of class Paint
        //The strokeJoin property specifies how line segments and arcs are joined together. There are three possible stroke join styles
        //mDrawPaint!!.strokeJoin=Paint.Join.ROUND sets the stroke join style of the mDrawPaint object to ROUND
        //You can use the mDrawPaint!!.color=color code to set the color of the mDrawPaint object to any color you want
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas=Canvas(mCanvasBitmap!!) //!! says that this is not null

    }// each time the size of the screen is chaneged and it is called when the screen is inflated opens our view as display our drwainf class

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvasBitmap?.let {
            canvas.drawBitmap(it, 0f,   0f, mCanvasPaint)
        }//git
        for (p in mPaths) {
            mDrawPaint?.strokeWidth = p.brushThickness
            mDrawPaint?.color = p.color
            canvas.drawPath(p, mDrawPaint!!)
        }//git
        if(!mDrawPath!!.isEmpty){
            mDrawPaint?.strokeWidth = mDrawPath!!.brushThickness //mDrawPath is of type custom pathe that is why we can use the brushThickenss here
            mDrawPaint?.color=mDrawPath!!.color
            canvas.drawPath(mDrawPath!!,mDrawPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate

        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                mDrawPath?.color=color
                mDrawPath?.brushThickness=mBrushSize
                mDrawPath?.reset()
                mDrawPath?.moveTo(touchX,touchY)
            }
            MotionEvent.ACTION_MOVE->{
                mDrawPath?.lineTo(touchX,touchY)
            }
            MotionEvent.ACTION_UP->{
                mPaths.add(mDrawPath!!) //Add when to stroke is drawn to canvas and added in the path arraylist

                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false

        }
        invalidate()
        return true
    }



    internal inner class CustomPath(var color:Int,var brushThickness:Float) : Path() {

    }
}