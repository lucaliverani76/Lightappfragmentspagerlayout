package com.example.lightappfragmentspagerlayout




//import android.R
//import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class lightinformation(
        public var red: Int = 0,
        public var green: Int = 0,
        public var blue: Int = 0,
        public var red_transformed: Int = 0,
        public var green_transformed: Int = 0,
        public var blue_transformed: Int = 0,
        public var white: Int = 0,
        public var stateonoff: Boolean = false,
        public var NameofAP: String = "",
        public var IP: String = "0.0.0.0",
        public var port: Int = 0,
        public var stringtosend: String = "{\"GLights\":0,\"red\":0, \"green\":0 , \"blue\":0,  \"alphs\":0}",
        public var stringtosend_old: String = "{\"GLights\":0,\"red\":0, \"green\":0 , \"blue\":0,  \"alpha\":0}",
        public var Sint: Int = 0,
        public var Vint: Int = 100,
        public var ActionType: Int = 0,
        public var modifiedcolor: Int = 0,
        public var Type_: String = "",
        var hsv: FloatArray = FloatArray(4),
        var linkedtomusic:Boolean=false
) {
    companion object {
        var uniquecounter:Int=0
    }

    public var hsvlocal:FloatArray= FloatArray(4)
    public var portidentified:Int=0

    init{
        portidentified=uniquecounter
        uniquecounter++
    }

    public var sublights:MutableList<lightinformation> = mutableListOf()
    public var udpbroadcaster:UDPBroadcaster?=null

    public fun setcolostring(
            red: Int = 255, green: Int = 255, blue: Int = 255,
            white: Int = 0, Sint: Int = 100, Vint: Int = 100, modifiedcolor: Int
    ) {


        this.red = red
        this.green = green
        this.blue = blue

        this.Sint = Sint
        this.Vint = Vint
        this.modifiedcolor=modifiedcolor

        this.red_transformed = red
        this.green_transformed = green
        this.blue_transformed = blue

        this.white = white



        this.stringtosend = "{" +
                "\"GLights\":0,"+
                "\"red\":" +
                this.red_transformed.toInt() +
                ",\"green\":" +
                this.green_transformed.toInt() +
                ",\"blue\":" +
                this.blue_transformed.toInt() +
                ", \"alpha\":" +
                this.white.toInt() +
                "}"
        this.stringtosend_old = this.stringtosend

        val RDBW= Color.RGBToHSV(this.red_transformed, this.green_transformed,
                this.blue_transformed, this.hsvlocal);

   for (n in 0..sublights.size-1)
   {
       sublights.get(n).red = red
       sublights.get(n).green = green
       sublights.get(n).blue = blue

       sublights.get(n).Sint = Sint
       sublights.get(n).Vint = Vint
       sublights.get(n).modifiedcolor=modifiedcolor

       sublights.get(n).red_transformed = this.red_transformed
       sublights.get(n).green_transformed = this.green_transformed
       sublights.get(n).blue_transformed = this.blue_transformed
       sublights.get(n).white = white

       sublights.get(n).stringtosend = "{" +
               "\"GLights\":0,"+
               "\"red\":" +
               sublights.get(n).red_transformed.toInt() +
               ",\"green\":" +
               sublights.get(n).green_transformed.toInt() +
               ",\"blue\":" +
               sublights.get(n).blue_transformed.toInt() +
               ", \"alpha\":" +
               sublights.get(n).white.toInt() +
               "}"
       sublights.get(n).stringtosend_old =  sublights.get(n).stringtosend

       sublights.get(n).hsvlocal=this.hsvlocal
   }

    }

    public fun setstringtosend(s: String){
        this.stringtosend=s
        for (n in 0..sublights.size-1)
        {sublights.get(n).stringtosend=s}
    }

    public fun sethsv(){

        var newH=(this.hsvlocal[0]+this.hsv[0]*360)
        if (newH>360){newH=newH-360}
        var tt:FloatArray= FloatArray(3)
        tt[0]=newH
        tt[1]=this.hsv[1]
        tt[2]=this.hsv[2]
        var mycolor=Color.HSVToColor(tt)
        var  red_=Color.red(mycolor)
        var green_=Color.green(mycolor)
        var blue_=Color.blue(mycolor)
        //val a = Color.alpha(intColor)

        this.stringtosend = "{" +
                "\"GLights\":0,"+
                "\"red\":" +
                red_.toInt() +
                ",\"green\":" +
                green_.toInt() +
                ",\"blue\":" +
                blue_.toInt() +
                ", \"alpha\":" +
                this.white.toInt() +
                "}"
    }


    public fun setmodifiedcolor(s: Int){
        this.modifiedcolor=s
        for (n in 0..sublights.size-1)
        {sublights.get(n).modifiedcolor=s}
    }
    public fun setcolostring(
            ActionType: Int, ActionString: String
    ) {

        this.ActionType = ActionType
        if (ActionType == 0) {
            this.stringtosend=this.stringtosend_old
        } else {
            this.stringtosend = "{" +
                    "\"GLights\":" +
                    ActionType.toString() +
                    ",\"red\":0, \"green\":0 , \"blue\":0,  \"alpha\":0}"

        }
        for (n in 0..sublights.size-1)
        {
            sublights.get(n).ActionType = ActionType
            if (ActionType == 0) {
                sublights.get(n).stringtosend =sublights.get(n).stringtosend_old
            } else {
                sublights.get(n).stringtosend ="{" +
                        "\"GLights\":" +
                        ActionType.toString() +
                        ",\"red\":0, \"green\":0 , \"blue\":0,  \"alpha\":0}"

            }

        }
    }


    public fun setstateonoff(state_: Boolean){
        this.stateonoff=state_
        if (state_==false)
            this.setstringtosend(
            "{\"GLights\":0,\"red\":0, \"green\":0 , \"blue\":0,  \"alpha\":0}")
        for (n in 0..sublights.size-1)
        {sublights.get(n).stateonoff=state_}
    }

    fun convertoRGBW(Ri: Int = 0, Gi: Int = 0, Bi: Int = 0): Int {

    //Get the maximum between R, G, and B
    var tM: Float = kotlin.math.max(Ri, kotlin.math.max(Gi, Bi)).toFloat()

//If the maximum value is 0, immediately return pure black.
    if(tM == 0f)
    { return (0 and 0xff shl 24 or (0 and 0xff shl 16) or (0 and 0xff shl 8) or (0 and 0xff) )
    }

//This section serves to figure out what the color with 100% hue is
    var multiplier = 255.0f / tM;
    var hR = Ri * multiplier;
    var hG = Gi * multiplier;
    var hB = Bi * multiplier;

//This calculates the Whiteness (not strictly speaking Luminance) of the color
    var M = kotlin.math.max(hR, kotlin.math.max(hG, hB));
    var m = kotlin.math.min(hR, kotlin.math.min(hG, hB));
    var Luminance = ((M + m) / 2.0f - 127.5f) * (255.0f/127.5f) / multiplier;

//Calculate the output values
    var Wo = (Luminance).toInt();
    var Bo = (Bi - Luminance).toInt();
    var Ro = (Ri - Luminance).toInt();
    var Go = (Gi - Luminance).toInt();

//Trim them so that they are all between 0 and 255
    if (Wo < 0) Wo = 0;
    if (Bo < 0) Bo = 0;
    if (Ro < 0) Ro = 0;
    if (Go < 0) Go = 0;
    if (Wo > 255) Wo = 255;
    if (Bo > 255) Bo = 255;
    if (Ro > 255) Ro = 255;
    if (Go > 255) Go = 255;
    return (Wo and 0xff shl 24 or (Ro and 0xff shl 16) or (Go and 0xff shl 8) or (Bo and 0xff) )
}

    fun sendUDP()
    {
        this.udpbroadcaster?.sendUDP()
        for (n in 0..sublights.size-1) {
            sublights.get(n).udpbroadcaster?.sendUDP()
        }
    }

    fun closeUDP()
    {
        this.udpbroadcaster?.close()
        for (n in 0..sublights.size-1) {
            sublights.get(n).udpbroadcaster?.close()
        }
    }

}



class MyCanvasView: AppCompatImageView {

    public lateinit var extraBitmap: Bitmap
    public lateinit var otherimage: Bitmap
    public var unmodifiedcolor:Int=0
    public var modifiedcolor:Int=0
    public var V:Float=1f
    public var S:Float=1f
    public var White_:Int=0
    public var Vint:Int=100
    public var Sint:Int=100



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

    public fun drawstuff(mecolor: Int){


        //val mecolor: Int = 255 and 0xff shl 24 or (red and 0xff shl 16) or (green and 0xff shl 8) or (blue and 0xff)

        var paint = Paint()
        paint.setColor(mecolor)

        paint.setAntiAlias(false)
        paint.setDither(false)


        var center_x = (width / 2).toFloat()
        var center_y = (height / 2).toFloat()
        var radius = (otherimage.getWidth() / 5).toFloat()

        // draw circle
        extraCanvas.drawCircle(center_x, center_y, radius, paint)

        invalidate()

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


        S=Sint.toFloat() / 100f
        V=Vint.toFloat() / 100f
        hsv[1]=S
        hsv[2]=V


        var redValue_m :Int = 255
        var blueValue_m :Int = 255
        var greenValue_m :Int = 255
        var white_m:Int = 255

        val andreacrazyidea=true
        if (andreacrazyidea)
        {
            redValue_m = (redValue.toFloat()*this.V).toInt()
            blueValue_m = (blueValue.toFloat()*this.V).toInt()
            greenValue_m = (greenValue.toFloat()*this.V).toInt()
            white_m = this.White_

            val fact_white=0.5f
            var xred:Float=(redValue_m.toFloat()+white_m.toFloat()/fact_white)
            var xblue:Float=(blueValue_m.toFloat()+white_m.toFloat()/fact_white)
            var xgreen:Float=(greenValue_m.toFloat()+white_m.toFloat()/fact_white)
            val mmax:Float= kotlin.math.max(kotlin.math.max(xred, xblue), xgreen)
            if (mmax>255f) {
                xred = kotlin.math.min((xred / mmax * 255f), 255f)
                xblue = kotlin.math.min((xblue / mmax * 255f), 255f)
                xgreen = kotlin.math.min((xgreen / mmax * 255f), 255f)
            }
            else
            {


            }

            modifiedcolor = 255 and 0xff shl 24 or (xred.toInt() and 0xff shl 16) or (xgreen.toInt() and 0xff shl 8) or (xblue.toInt() and 0xff)

        }
        else
        {
            hsv[1]=1.0f
            modifiedcolor = Color.HSVToColor(hsv);

            white_m=this.White_
            redValue_m = Color.red(modifiedcolor);
            blueValue_m = Color.blue(modifiedcolor);
            greenValue_m = Color.green(modifiedcolor)
            //RGBW=rrr.rgbToRgbw(redValue  as UInt,greenValue as UInt, blueValue as UInt)

        }
        val activity = context as Activity
        (activity as MainActivity).setdata(redValue_m, greenValue_m, blueValue_m, white_m, Sint, Vint, modifiedcolor)


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
        /*private val Type_: Array<String>,
        private val Title_: Array<String>,
        private val imgid_dots: Array<Int>,
        private val imgid_edit: Array<Int>,
        private val imgid_on: Array<Int>,*/
        var light_characteristics: MutableList<lightinformation>,
        /*var listofsender: MutableList<UDPBroadcaster>,*/
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

        viewHolder.textView.text = "       " //Type_[position]
        //val mecolor: Int = 255 and 0xff shl 24 or (light_characteristics.get(position).red and 0xff shl 16) or (light_characteristics.get(position).green and 0xff shl 8) or (light_characteristics.get(position).blue and 0xff)
        viewHolder.textView.setBackgroundColor(light_characteristics.get(position).modifiedcolor)

        viewHolder.textView2.text = light_characteristics.get(position).NameofAP
        if (light_characteristics.get(position).Type_!="AP")
        {
            viewHolder.imageView.setImageResource(R.drawable.ic_audiotrack_24px)
            viewHolder.imageView2.setImageResource(R.drawable.ic_baseline_color_lens_24)
            if (light_characteristics.get(position).stateonoff == false) {
                viewHolder.imageView3.setImageResource(R.drawable.ic_baseline_toggle_off_24)
            }
            else
            {
                viewHolder.imageView3.setImageResource(R.drawable.ic_baseline_toggle_on_24)
            }
        }
        else
        {
            viewHolder.imageView.setImageResource(R.drawable.ic_baseline_info_24)
            viewHolder.imageView2.setImageResource(R.drawable.ic_baseline_import_export_24)
            viewHolder.imageView3.setImageResource(R.mipmap.empty_round)
        }

            viewHolder.imageView.setOnClickListener()
            {
                if (light_characteristics.get(position).Type_!="AP")
                {
                    light_characteristics.get(position).linkedtomusic= !(light_characteristics.get(position).linkedtomusic)

                }
            }

            viewHolder.imageView3.setOnClickListener()
            {

                if (light_characteristics.get(position).Type_!="AP")
                {
                    if (light_characteristics.get(position).stateonoff == false) {
                        viewHolder.imageView3.setImageResource(R.drawable.ic_baseline_toggle_on_24);
                        /*imgid_on[position] = R.drawable.ic_baseline_toggle_on_24*/

                        light_characteristics.get(position).setstateonoff(true)
                        light_characteristics.get(position).setstringtosend(
                            light_characteristics.get(position).stringtosend_old)


                        light_characteristics.get(position).udpbroadcaster?.open(
                            localPort = 10000 + light_characteristics.get(position).portidentified,
                            destPort = light_characteristics.get(
                                position
                            ).port,
                            _IP = light_characteristics.get(position).IP,
                            lIGHT = light_characteristics.get(position)
                        )

                        light_characteristics.get(position).sendUDP()
                    } else {
                        light_characteristics.get(position).setstringtosend(
                            "{\"GLights\":0,\"red\":0, \"green\":0 , \"blue\":0,  \"alpha\":0}")

                        viewHolder.imageView3.setImageResource(R.drawable.ic_baseline_toggle_off_24);
                        /*imgid_on[position] = R.drawable.ic_baseline_toggle_off_24*/
                        // Handler().postDelayed({
                        light_characteristics.get(position).setstateonoff(false)
                        //  listofsender.get(position).close()
                        //  },700)
                    }
                }
            }


            viewHolder.imageView2.setOnClickListener()
            {
                nn.n = position

                if (light_characteristics.get(position).Type_!="AP")
                {
                    val context: Context = it.getContext()
                    //val myIntent = Intent(context, light_colors_and_features::class.java)
                    val activity = context as Activity

                    val myFragment =
                        (activity as MainActivity).supportFragmentManager.findFragmentByTag("f1")

                    (myFragment as SecondFragment).spin_.setSelection(light_characteristics.get(position).ActionType)
                    (myFragment as SecondFragment).deactivatedraw = 1
                    (myFragment as SecondFragment).changeseekbarwhite(light_characteristics.get(position).Sint)
                    (myFragment as SecondFragment).changeseekbarbrigthness(
                        light_characteristics.get(
                            position
                        ).Vint
                    )

                    (myFragment as SecondFragment).myCanvasView.drawstuff(
                        light_characteristics.get(
                            position
                        ).modifiedcolor
                    )


                    activity.vpPager.setCurrentItem(1)
                    (myFragment as SecondFragment).deactivatedraw = 0
                    //context.startActivity(myIntent)
                }
                else
                {
                    val context: Context = it.getContext()
                    //val myIntent = Intent(context, light_colors_and_features::class.java)
                    val activity = context as Activity

                    val myFragment =
                        (activity as MainActivity).supportFragmentManager.findFragmentByTag("f0")
                    (myFragment as FirstFragment).APtext.setText("AP Name: " + light_characteristics.get(position).NameofAP)

                    (myFragment as FirstFragment).APtext.setVisibility(View.VISIBLE);
                    (myFragment as FirstFragment).Ptext.setVisibility(View.VISIBLE);
                    (myFragment as FirstFragment).PAssText.setVisibility(View.VISIBLE);
                    (myFragment as FirstFragment).Buttonconnect.setVisibility(View.VISIBLE);
                    (myFragment as FirstFragment).Buttoncancel.setVisibility(View.VISIBLE);


                }
            }

    }




    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return light_characteristics.size
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
        /*return when (position) {
            0 -> FirstFragment()
            1 -> {SecondFragment()}
            else -> SecondFragment()
        }*/
        if (position==0){
          return FirstFragment()
        }
        if (position==1){
            //val context: Context = fragmentActivity.getContext()
            //val myIntent = Intent(context, light_colors_and_features::class.java)
            //val activity = context as Activity
            //(activity as MainActivity).drawfragment= SecondFragment()
            return SecondFragment()
        }
        return SecondFragment()
    }


    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 2
    }
}


class MainActivity : AppCompatActivity() {

    var  hsv_music: FloatArray = FloatArray(4)
    var mMediaPlayer: MediaPlayer?=null
    lateinit var  vpPager: ViewPager2

    lateinit var Scanwifi:scanwifi

    lateinit var thegroups:MutableList<Groups_>

    var localadapter:ViewPagerAdapter? = null
    lateinit var image_music:ImageView
    lateinit var image_mic:ImageView
    lateinit var image_group:ImageView

    lateinit var menuArray: Array<String>



    lateinit var viewModel: EventViewModel

    var light_characteristics: MutableList<lightinformation>    = mutableListOf()
    var light_characteristicsglobal: MutableList<lightinformation>    = mutableListOf()
    /*var listofsender: MutableList<UDPBroadcaster> = mutableListOf()*/

    var nn: myint = myint()


    var activity_: Activity? = null

    lateinit var broadcaster_receiver: UDPBroadcaster
    lateinit var updsender: UDPBroadcaster

    var listofdevices: MutableList<MutableList<String>>? = null

    var tempSongList: MutableList<Song>? = null

    var fftfunction:DoFFT=DoFFT(this)


    public fun setdata(
            red: Int = 255, green: Int = 255, blue: Int = 255,
            white: Int = 0, Sint: Int = 100, Vint: Int = 100, modifiedcolor: Int = 0
    ) {
        light_characteristics[nn.n].setmodifiedcolor(modifiedcolor)
                light_characteristics[nn.n].setcolostring(red, green, blue, white, Sint, Vint, modifiedcolor)


    }

    public fun setdata(specialcommand: Int
    ) {

            light_characteristics[nn.n].setcolostring(specialcommand, menuArray[specialcommand])

     }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            vpPager.setCurrentItem(0)
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }*/

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            var listofdevices_string: Array<String> = data?.getStringArrayExtra("MESSAGE") as Array<String>;
            thegroups=outputgroups(listofdevices_string)
            light_characteristics= mutableListOf()
            for (group_ in thegroups)
            {
                if (group_.ISGROUP) {
                    light_characteristicsglobal.add(lightinformation(NameofAP = group_.nameofgroup))
                    var sss= light_characteristicsglobal.size-1
                    light_characteristicsglobal.get(sss).IP = "0.0.0.0"
                    light_characteristicsglobal.get(sss).port = 0
                    /*light_characteristicsglobal.get(sss).udpbroadcaster*/
                    light_characteristicsglobal.get(sss).Type_ = "Light"
                    for (r in 1..group_.listoflightsingroup.size-1)
                    {
                        var x: lightinformation? = searchforlight(group_.listoflightsingroup[r], light_characteristicsglobal)
                        x?.let { light_characteristicsglobal.get(sss).sublights.add(it) }
                    }
                    println()
                    light_characteristics.add(light_characteristicsglobal.get(sss))
                }
                else
                {
                    var x: lightinformation? = searchforlight(group_.nameofgroup, light_characteristicsglobal)
                    x?.let { light_characteristics.add(it) }
                }

                for (z in light_characteristicsglobal){
                    if (z.Type_=="AP")
                        light_characteristics.add(z)
                }

            }



            val myFragment = supportFragmentManager.findFragmentByTag("f0")


             (myFragment as FirstFragment).rv = findViewById<View>(R.id.RecView_devices) as RecyclerView



            val adapter = ContactsAdapter(
                    /*Type_.toTypedArray(),
                    Title_.toTypedArray(),
                    imgid_dots.toTypedArray(),
                    imgid_edit.toTypedArray(),
                    imgid_on.toTypedArray(),*/
                    light_characteristics,
                    /*(activity as MainActivity).listofsender,*/
                    nn
            )



            (myFragment as FirstFragment).rv.adapter = adapter
            // Set layout manager to position the items
            (myFragment as FirstFragment).rv.layoutManager = LinearLayoutManager(this)
        }
    }


    fun searchforlight(n: String, lightlist: MutableList<lightinformation>):lightinformation?
    {
        for (k in lightlist)
        {
            if (k.NameofAP==n)
            return k
        }
        return null
    }

    fun openSomeActivityForResult(intent: Intent){
        resultLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         activity_ = this


        var REQUEST_CODE: Int =0
        if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as MainActivity,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this as MainActivity,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
                        android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as MainActivity,
                            android.Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this as MainActivity,
                        arrayOf(android.Manifest.permission.RECORD_AUDIO),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
                        android.Manifest.permission.MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as MainActivity,
                            android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this as MainActivity,
                        arrayOf(android.Manifest.permission.MODIFY_AUDIO_SETTINGS),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        //thiscontext = activity_!!.applicationContext
        Scanwifi=scanwifi(activity_ as Activity)

        setContentView(com.example.lightappfragmentspagerlayout.R.layout.activity_main)
        menuArray = getResources().getStringArray(R.array.programs_array);




        image_music = findViewById<View>(R.id.image_music) as ImageView
        image_mic = findViewById<View>(R.id.image_mic) as ImageView
        image_group = findViewById<View>(R.id.image_group) as ImageView
        image_music.setImageResource(R.drawable.ic_audiotrack_24px)
        image_mic.setImageResource(R.drawable.ic_mic_24px)
        image_group.setImageResource(R.drawable.ic_baseline_subject_24)


        image_music.setOnClickListener (){
            tempSongList= mutableListOf()
            getSongList(this, tempSongList)
            var song: Uri = tempSongList!!.get(3).uriImage
            //val song = resources.getIdentifier("lolas1", "raw", packageName)
            if (mMediaPlayer==null) {

                /*val audioSessionId = this.getSystemService(AUDIO_SERVICE).getAudioSessionId()
                if (audioSessionId != AudioManager.ERROR) {
                    mMediaPlayer?.setAudioSessionId(audioSessionId);
                }*/

                mMediaPlayer = MediaPlayer.create(this, song)

                mMediaPlayer?.setLooping(true);
                mMediaPlayer?.start();
                var si=mMediaPlayer?.getAudioSessionId()



                fftfunction.link(mMediaPlayer)
                hsv_music[3]=1f
            }
            else
            {

                mMediaPlayer?.stop();
                mMediaPlayer?.release();
                mMediaPlayer=null
                hsv_music[3]=0.0f

                }

            var stop=0
        }


        image_group.setOnClickListener (){
            val context: Context = it.getContext()
            val myIntent = Intent(context, Group_activity::class.java)

            val listofdevices: MutableList<String> = ArrayList()
            for (t in light_characteristics) {
                listofdevices.add((t as lightinformation).NameofAP)
            }
            myIntent.putExtra("EXTRA_SESSION_ID", listofdevices.toTypedArray());
            openSomeActivityForResult(myIntent)

        }


        vpPager = findViewById<View>(com.example.lightappfragmentspagerlayout.R.id.vpPager) as ViewPager2
        vpPager.setUserInputEnabled(false);


        localadapter = createAdapter()
        vpPager.setAdapter(localadapter)

        vpPager.setCurrentItem(1)
        vpPager.setCurrentItem(0)

        viewModel = EventViewModel()
        //Aview = AndroidViewModel()



    }



    private fun createAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(this)
    }

  /*  inner class AndroidViewModel() : ViewModel() {

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

*/
  override fun onBackPressed() {

    val currentFragment = vpPager.currentItem
        //(this as MainActivity).supportFragmentManager.findFragmentById(R.id.vpPager);
    if (currentFragment == 1) {
        val myFragment = (this as MainActivity).supportFragmentManager.findFragmentByTag("f1")
        (myFragment as SecondFragment).backsecondfragment()
    }

      else {vpPager.setCurrentItem(0)}

  }

}

