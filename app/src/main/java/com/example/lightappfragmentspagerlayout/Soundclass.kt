package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.ContentResolver
import android.database.Cursor
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.OnDataCaptureListener
import android.net.Uri
import android.provider.MediaStore


class DoFFT (var activity_: Activity){

    public lateinit var mVisualizer:Visualizer

    /*PARAMETERS*/
    public var filter: Double = 0.1
    public var maxfreq=5000
    public val fullintensity_db=7
    public val zerointensity_db=4
    public val intensityspan=0.7
    public val intensity_thr=5
    public val timetoexplorecolorspan=0.1
    public val colorjump=0.3

    /*VARIABLES*/
    public var averagelevel_s: Double= 0.0
    public var dbValue_old:Double=0.0
    public var bright_=0.0
    public var H=0.0



/**
 * Links the visualizer to a player
 * @param player - MediaPlayer instance to link to
 */
fun link(player: MediaPlayer?) {
    if (player == null) {
        throw NullPointerException("Cannot link to null MediaPlayer")
    }


    // Create the Visualizer object and attach it to our media player.
    var si=player.getAudioSessionId()
    mVisualizer = Visualizer(si)
    mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0])

    // Pass through Visualizer data to VisualizerView
    val captureListener: OnDataCaptureListener = object : OnDataCaptureListener {
        override fun onWaveFormDataCapture(visualizer: Visualizer, bytes: ByteArray,
                                           samplingRate: Int) {
            /*updateVisualizer(bytes)*/
        }

        override fun onFftDataCapture(visualizer: Visualizer, bytes: ByteArray,
                                      samplingRate: Int) {
            updateVisualizerFFT(bytes, samplingRate)
            println()
        }
    }
    mVisualizer.setDataCaptureListener(captureListener,
            Visualizer.getMaxCaptureRate() / 2, true, true)

    // Enabled Visualizer and disable when we're done with the stream
    mVisualizer.setEnabled(true)
    player.setOnCompletionListener(OnCompletionListener { mVisualizer.setEnabled(false) })
}

    fun updateVisualizerFFT(data: ByteArray, samplinrate:Int) {

        var rfk:Int
        var ifk:Int
        var dbValue:Double=0.0

        val freqresolution=samplinrate/1000/data.size
        var maxindex=(maxfreq/freqresolution).toInt()
        if (maxindex%2==0) {maxindex+1}
        for (i in 0..maxindex) {

            rfk = data.get(i).toInt()
            ifk = data.get(i + 1).toInt()
            var magnitude: Double = (rfk * rfk + ifk * ifk).toDouble()
            dbValue = (Math.log(magnitude+1))/2  * (1024/data.size)

        }

        averagelevel_s=filter*(dbValue- averagelevel_s) +averagelevel_s

        bright_=intensityspan/(fullintensity_db - zerointensity_db)  *
        (averagelevel_s-zerointensity_db) + (1-intensityspan)
        bright_=kotlin.math.max(bright_,(1-intensityspan) )
        bright_=kotlin.math.min(bright_,1.0 )


        var diff=dbValue - dbValue_old
        if (diff >intensity_thr)
        {H=H+colorjump}
        else
        {H=H+1/(timetoexplorecolorspan*freqresolution)}
        if (H>1){H=H-1}

        (activity_ as MainActivity).hsv_music[0]=H.toFloat()
        (activity_ as MainActivity).hsv_music[1]=1.0f
        (activity_ as MainActivity).hsv_music[2]=bright_.toFloat()

        dbValue_old=dbValue
    }

}




class Song(
        public var iD: Long = 0,

        public var title: String? = null,

        public var artist: String? = null,

        public var albumId: Long = 0,

        public var data_: String? = null,
        public var albumkey: String? = null,
        public var uriImage: Uri)
    {

    fun init(songID: Long, songTitle: String?, songArtist: String?, songalbumId: Long, songdata: String?, songalbumkey: String?, songUri: Uri)
        {
        this.iD = songID
        title = songTitle
        artist = songArtist
        albumId = songalbumId
        data_ = songdata
        albumkey = songalbumkey
        uriImage = songUri
        }
}

fun getSongList(thisActivity: Activity, tempSongList: MutableList<Song>?) {
    //retrieve song info
    val musicResolver: ContentResolver = thisActivity.getContentResolver()
    val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val musicCursor: Cursor? = musicResolver.query(musicUri, null, null, null, null)
    //iterate over results if valid
    if (musicCursor != null && musicCursor.moveToFirst()) {
        //get columns
        val titleColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val idColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val artistColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val albumId: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val data: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val albumkey: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY)
        //add songs to list
        do {
            val thisId: Long = musicCursor.getLong(idColumn)
            val thisTitle: String = musicCursor.getString(titleColumn)
            val thisArtist: String = musicCursor.getString(artistColumn)
            val thisalbumId: Long = musicCursor.getLong(albumId)
            val thisdata: String = musicCursor.getString(data)
            val AlbumKey: String = musicCursor.getString(albumkey)
            val uriImage = Uri.withAppendedPath(musicUri, "" + thisId)
            tempSongList?.add(Song(thisId, thisTitle, thisArtist, thisalbumId, thisdata, AlbumKey, uriImage))
        } while (musicCursor.moveToNext())
    }
}