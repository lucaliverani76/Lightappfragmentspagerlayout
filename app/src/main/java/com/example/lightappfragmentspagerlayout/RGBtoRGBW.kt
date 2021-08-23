package com.example.lightappfragmentspagerlayout

class RGBtoRGBW {

    class colorRgbw{
        var red:Int=0
        public var green:Int= 0
        public var blue:Int=0
        public var white:Int=0
    }


public fun saturation(rgbw:colorRgbw ) : Int {
    var low_:Float= (kotlin.math.min(rgbw.red, kotlin.math.min(rgbw.green, rgbw.blue))).toFloat()
    var high_:Float= (kotlin.math.max(rgbw.red, kotlin.math.max(rgbw.green, rgbw.blue))).toFloat()
    return (kotlin.math.round(100 * ((high_ - low_) / high_)).toInt())
}


    public fun getWhite(rgbw:colorRgbw ) : Int {
        return  (255 - saturation(rgbw)).toInt() / 255 * (rgbw.red + rgbw.green + rgbw.blue) / 3;
    }


    public fun getWhite(rgbw:colorRgbw, redMax:Int, greenMax:Int, blueMax:Int ) : Int {

        rgbw.red = (rgbw.red.toFloat() / 255.0 * redMax.toFloat()).toInt();
        rgbw.green = (rgbw.green.toFloat() / 255.0 * greenMax.toFloat()).toInt();
        rgbw.blue = (rgbw.blue.toFloat() / 255.0 * blueMax.toFloat()).toInt();
        return  (255 - saturation(rgbw)).toInt() / 255 * (rgbw.red + rgbw.green + rgbw.blue) / 3;
    }


   // Example function.
     public  fun rgbToRgbw(red:Int, green:Int, blue:Int):colorRgbw  {

           var white_:Int  = 0
           var rgbw: colorRgbw = colorRgbw()

            rgbw.red=red
            rgbw.green=green
            rgbw.blue=blue
            rgbw.white=white_

            rgbw.white = getWhite(rgbw);
           return rgbw;
       }

    public  fun rgbToRgbw(red:Int, green:Int, blue:Int, redmax:Int, greenmax:Int, bluemax:Int):colorRgbw  {

        var white_:Int  = 0
        var rgbw: colorRgbw = colorRgbw()

        rgbw.red=red
        rgbw.green=green
        rgbw.blue=blue
        rgbw.white=white_

        rgbw.white = getWhite(rgbw, redmax, greenmax, bluemax);
        return rgbw;
    }



}