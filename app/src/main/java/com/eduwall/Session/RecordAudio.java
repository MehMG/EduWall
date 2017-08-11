package com.eduwall.Session;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PC10 on 09-Aug-17.
 */

public class RecordAudio {

    Context context;
    String time;
    String dateInString;
    File SDCardpath;
    File myDataPath;
    File audiofile;
    MediaRecorder recorder;
    String fileName;
    String filePath;

    public RecordAudio(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startRecording() {
        try {
            Toast.makeText(context, "Recording Starts", Toast.LENGTH_SHORT).show();
            time = "-" + String.valueOf(Calendar.getInstance().get(Calendar.HOUR)) + "-" + String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)) + "-" + String.valueOf(Calendar.getInstance().get(Calendar.SECOND))+"-";

            dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            fileName = "EduWall_" + dateInString + time + " record.mp3";

            SDCardpath = Environment.getExternalStorageDirectory();
            myDataPath = new File(SDCardpath.getAbsolutePath()
                    + "/.My Recordings");

            if (!myDataPath.exists())
                myDataPath.mkdir();

            audiofile = new File(myDataPath + "/" + fileName);
            filePath = myDataPath + "/" + fileName;
            Log.e("Recorder", "Path ::: " + myDataPath.getAbsolutePath());

            recorder = new MediaRecorder();
            recorder.reset();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setAudioEncodingBitRate(16);
            recorder.setAudioSamplingRate(44100);
            recorder.setOutputFile(audiofile.getAbsolutePath());

            try {
                recorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            recorder.start();
        } catch (Exception e) {

        }
    }

    public String stopRecording() {
        try {
            Toast.makeText(context, "Recording Stops", Toast.LENGTH_SHORT).show();
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (RuntimeException stopException) {
                }
                recorder.release();
                recorder = null;
            } else {
                Toast.makeText(context, "Record First",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
        return filePath;
    }
}
