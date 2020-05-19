package com.aqratsa.aeqarat.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int UPDATE_PERCENT_THRESHOLD = 1;

    private File file;
    private ProgressListener listener;
    private MediaType mediaType;

    public interface ProgressListener {
        void onUploadProgress(int progressInPercent, long totalBytes);
    }

    public ProgressRequestBody(Context context, File file, ProgressListener listener) {
        //this.file = FileUtils.getFile(context, fileUri);
        this.file = file;
        this.listener = listener;

        /*String type = context.getContentResolver().getType(fileUri);
        mediaType = MediaType.parse(type);*/
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long totalBytes = file.length();
        FileInputStream in = new FileInputStream(file);

        try {
            // init variables
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            long uploadedBytes = 0;
            int readBytes;
            int fileUploadedInPercent = 0;

            // go through the file and notify the UI
            while ((readBytes = in.read(buffer)) != -1) {
                // notify UI at max after every 1%
                int newfileUploadedInPercent = (int) ((uploadedBytes * 100) / totalBytes);
                if (fileUploadedInPercent + UPDATE_PERCENT_THRESHOLD <= newfileUploadedInPercent) {
                    fileUploadedInPercent = newfileUploadedInPercent;
                    listener.onUploadProgress(newfileUploadedInPercent, totalBytes);
                }

                uploadedBytes += readBytes;
                sink.write(buffer, 0, readBytes);
            }
        } finally {
            in.close();
        }

        listener.onUploadProgress(100, totalBytes);
    }
}
