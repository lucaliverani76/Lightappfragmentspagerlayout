package com.example.lightappfragmentspagerlayout




//import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class lightinformation(
        public var red: Int = 255,
        public var green: Int = 255,
        public var blue: Int = 255,
        public var white: Int = 0,
        public var stateonoff: Boolean = false,
        public var NameofAP: String = "",
        public var IP: String = "0.0.0.0",
        public var port: Int = 0,
        public var stringtosend: String = "{\"red\":255, \"green\":255 , \"blue\":255,  \"white\":0}"

){

    public fun setcolostring(
            red: Int = 255, green: Int = 255, blue: Int = 255,
            white: Int = 0
    ){

        this.stringtosend = "{\"red\":" +
                red.toInt() +
                ",\"green\":" +
                green.toInt() +
                ",\"blue\":" +
                blue.toInt() +
                ", \"white\":" +
                white.toInt() +
                "}"

    }


}



class MyCanvasView: AppCompatImageView {

    public lateinit var extraBitmap: Bitmap
    public lateinit var otherimage: Bitmap
    public var unmodifiedcolor:Int=0
    public var modifiedcolor:Int=0
    public var V:Float=1f
    public var S:Float=1f
    public var RGBW : RGBtoRGBW.colorRgbw = RGBtoRGBW.colorRgbw()
    public var rrr = RGBtoRGBW()


    fun init()
    {
        extraBitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888)
    }

    public constructor (context: Context):super(context)
    {
        init()
    }

    public constructor (context: Context, attrs: AttributeSet?):super(context, attrs)
    {
        init()
    }

    public constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int):super(
            context,
            attrs,
            defStyleAttr
    )
    {
        init()
    }

    private lateinit var extraCanvas: Canvas
    private val backgroundColor = ResourcesCompat.getColor(
            resources,
            android.R.color.holo_red_light,
            null
    )
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f



    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val getmin:Int=kotlin.math.min(height, width)

        val original = BitmapFactory.decodeResource(resources, R.drawable.pngegg)

        if (::otherimage.isInitialized) otherimage.recycle()

        otherimage = Bitmap.createScaledBitmap(original, getmin, getmin, false)
        var w_1= (extraBitmap.getWidth() - otherimage.getWidth()) / 2
        var h_1 = (extraBitmap.getHeight() - otherimage.getHeight()) / 2

        for (j in  0..otherimage.getHeight()-1)
            for (i in 0..otherimage.getWidth()-1)
            {
                var pixel = otherimage.getPixel(i, j)

                extraBitmap.setPixel(i + w_1, j + h_1, pixel);
            }
        extraCanvas = Canvas(extraBitmap)



        var paint = Paint()
        paint.setColor(Color.WHITE)
        //Color.parseColor("#FFFFFF"))
        //paint.setStrokeWidth(30F)
        //paint.setStyle(Paint.Style.STROKE)
        paint.setAntiAlias(false)
        paint.setDither(false)



        var center_x = (width/2).toFloat()
        var center_y = (height/2).toFloat()
        var radius = (otherimage.getWidth()/5).toFloat()

        // draw circle
        extraCanvas.drawCircle(center_x, center_y, radius, paint)


        //extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y


        if (
                ((motionTouchEventX<extraBitmap.width)  and (motionTouchEventY<extraBitmap.height))
                and
                ((motionTouchEventX>0)  and (motionTouchEventY>0))
                and
                (((motionTouchEventX - extraBitmap.width/2) * (motionTouchEventX - extraBitmap.width/2) +
                        (motionTouchEventY - extraBitmap.height/2) * (motionTouchEventY - extraBitmap.height/2))>
                        (otherimage.height/4)*(otherimage.height/4))

                and
                (((motionTouchEventX - extraBitmap.width/2) * (motionTouchEventX - extraBitmap.width/2) +
                        (motionTouchEventY - extraBitmap.height/2) * (motionTouchEventY - extraBitmap.height/2))<
                        (otherimage.height/2.1)*(otherimage.height/2.1))

        ) {
            var pixel = extraBitmap.getPixel(motionTouchEventX.toInt(), motionTouchEventY.toInt());
            unmodifiedcolor = pixel
            drawstuff()
        }


        return true
    }

    public fun drawstuff(){

        var pixel=unmodifiedcolor

        //then do what you want with the pixel data, e.g
        var redValue = Color.red(pixel);
        var blueValue = Color.blue(pixel);
        var greenValue = Color.green(pixel)
        //RGBW=rrr.rgbToRgbw(redValue  as UInt,greenValue as UInt, blueValue as UInt)


        var  hsv = FloatArray(3)
        val RDBW= Color.RGBToHSV(redValue, greenValue, blueValue, hsv);

        hsv[1]=S
        hsv[2]=V

        modifiedcolor = Color.HSVToColor(hsv);

        var redValue_m = Color.red(modifiedcolor);
        var blueValue_m = Color.blue(modifiedcolor);
        var greenValue_m = Color.green(modifiedcolor)
        //RGBW=rrr.rgbToRgbw(redValue  as UInt,greenValue as UInt, blueValue as UInt)


        val activity = context as Activity
        (activity as MainActivity).setdata(redValue_m, greenValue_m, blueValue_m)


        var paint = Paint()
        paint.setColor(modifiedcolor)
        //Color.parseColor("#FFFFFF"))
        //paint.setStrokeWidth(30F)
        //paint.setStyle(Paint.Style.STROKE)
        paint.setAntiAlias(false)
        paint.setDither(false)


        var center_x = (width / 2).toFloat()
        var center_y = (height / 2).toFloat()
        var radius = (otherimage.getWidth() / 5).toFloat()

        // draw circle
        extraCanvas.drawCircle(center_x, center_y, radius, paint)

        invalidate()

    }


}



class ContactsAdapter(
        private val Type_: Array<String>,
        private val Title_: Array<String>,
        private val imgid_dots: Array<Int>,
        private val imgid_edit: Array<Int>,
        private val imgid_on: Array<Int>,
        var light_characteristics: MutableList<lightinformation>,
        var listofsender: MutableList<UDPBroadcaster>,
        var nn: myint
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {



    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        val textView = listItemView.findViewById(R.id.textView_x) as TextView
        val textView2 = listItemView.findViewById(R.id.textView2) as TextView
        val imageView = listItemView.findViewById(R.id.imageView1) as ImageView
        val imageView2 = listItemView.findViewById(R.id.imageView2) as ImageView
        val imageView3 = listItemView.findViewById(R.id.imageView3) as ImageView

    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.rowelement, parent, false)
        // Return a new holder instance

        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        //val contact: Contact = mContacts.get(position)

        viewHolder.textView.text = Type_[position]
        viewHolder.textView2.text = Title_[position]
        viewHolder.imageView.setImageResource(imgid_dots[position])
        viewHolder.imageView2.setImageResource(imgid_edit[position])
        viewHolder.imageView3.setImageResource(imgid_on[position])

        viewHolder.imageView3.setOnClickListener() {
            //Toast.makeText(mContext, "Ho cliccato!", Toast.LENGTH_SHORT).show()
            if (light_characteristics.get(position).stateonoff == false) {
                viewHolder.imageView3.setImageResource(R.drawable.ic_settings_power_24px_2);
                imgid_on[position] = R.drawable.ic_settings_power_24px_2
                light_characteristics.get(position).stateonoff = true
                listofsender.get(position).open(
                        localPort = 10004 + position,
                        destPort = light_characteristics.get(
                                position
                        ).port,
                        _IP = light_characteristics.get(position).IP,
                        lIGHT = light_characteristics.get(position)
                )
                listofsender.get(position).sendUDP()
            } else {
                viewHolder.imageView3.setImageResource(R.drawable.ic_settings_power_24px);
                imgid_on[position] = R.drawable.ic_settings_power_24px
                light_characteristics.get(position).stateonoff = false
                listofsender.get(position).close()
            }
        }


        viewHolder.imageView2.setOnClickListener() {

            val context: Context = it.getContext()
            //val myIntent = Intent(context, light_colors_and_features::class.java)
            nn.n = position
            val activity = context as Activity
            (activity as MainActivity).showFragment(1)

            //context.startActivity(myIntent)
        }
    }




    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return Type_.size
    }
}


class myint(public var n: Int = 0){

}



class EventViewModel : ViewModel() {
    //...
    var insertionId = MutableLiveData<Float>()

    fun insert(num: Float) {
            insertionId.value=num
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            else -> SecondFragment()
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 2
    }
}


class MainActivity : AppCompatActivity() {

    lateinit var transaction: FragmentTransaction
    val fragment1: FirstFragment = FirstFragment()
    val fragment2: SecondFragment = SecondFragment()

    lateinit var  vpPager: FragmentContainerView
    lateinit var image_music:ImageView
    lateinit var image_mic:ImageView

    lateinit var Aview:AndroidViewModel

    var counter:Int=0

    lateinit var intentx:Intent

    lateinit var viewModel: EventViewModel

    var light_characteristics: MutableList<lightinformation>    = mutableListOf()
    var listofsender: MutableList<UDPBroadcaster> = mutableListOf()
    var nn: myint = myint()


    var activity_: Activity? = null

    lateinit var broadcaster_receiver: UDPBroadcaster
    lateinit var updsender: UDPBroadcaster

    var listofdevices: MutableList<List<String>>? = null


    public fun setdata(
            red: Int = 255, green: Int = 255, blue: Int = 255,
            white: Int = 0
    ) {
        light_characteristics[nn.n].setcolostring(red, green, blue, white)
    }

    fun showFragment(n:Int=0) {

        transaction = supportFragmentManager.beginTransaction()
        if (n==0)
        {
            //transaction.hide(fragment2);
            transaction.replace(R.id.fragment_container_view, fragment1)
            //transaction.show(fragment1)

        }
        if (n==1)
        {
            //transaction.hide(fragment1);
            transaction.replace(R.id.fragment_container_view, fragment2)
            //transaction.show(fragment2)
        }

        //transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity_ = this
        setContentView(com.example.lightappfragmentspagerlayout.R.layout.activity_main)

        image_music = findViewById<View>(R.id.image_music) as ImageView
        image_mic = findViewById<View>(R.id.image_mic) as ImageView
        image_music.setImageResource(R.mipmap.ic_launcher_round)

        vpPager = findViewById<View>(com.example.lightappfragmentspagerlayout.R.id.fragment_container_view) as FragmentContainerView
        showFragment(0)

        viewModel = EventViewModel()
        Aview = AndroidViewModel()



    }

    private fun createAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(this)
    }

    inner class AndroidViewModel() : ViewModel() {

        val parentJob = Job()
        val coroutineScope = CoroutineScope(parentJob + Dispatchers.Default)
        var stopstart=false
        fun startCoroutine() {
            stopstart=true
            val job = coroutineScope.launch {

                while(stopstart) {
                    Thread.sleep(100)
                    counter++




                    viewModelScope.launch(Dispatchers.Main){
                        var pippo=0;


                }

                }
            }
        }

        fun clearJob(){
            stopstart=false

        }

        override fun onCleared() {
            super.onCleared()
            this.parentJob.cancel()
        }

    }




}

