package com.example.gdg.marvel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.gdg.marvel.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class ImageUtils {

    /**
     * Gets random thumbnails
     * @param context
     * @return
     */
    public static Bitmap getRandomThumbnail(Context context){
        int min = 1, max = 9;
        int index = new Random().nextInt(max - min + 1) + min;
        int resId;

        switch (index) {
            case 1:
                resId = R.drawable.ic_photoshot_ant_man;
                break;
            case 2:
                resId = R.drawable.ic_photoshot_captain_america;
                break;
            case 3:
                resId = R.drawable.ic_photoshot_deadpool;
                break;
            case 4:
                resId = R.drawable.ic_photoshot_hulk;
                break;
            case 5:
                resId = R.drawable.ic_photoshot_iron_man;
                break;
            case 6:
                resId = R.drawable.ic_photoshot_marvel_woman;
                break;
            case 7:
                resId = R.drawable.ic_photoshot_spider_man;
                break;
            case 8:
                resId = R.drawable.ic_photoshot_thor;
                break;
            case 9:
            default:
                resId = R.drawable.ic_photoshot_wolverine;
                break;
        }

        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * Gets the images path
     *
     * @return
     */
    private static String getImagesPath() {
        String dir = Environment.getExternalStorageDirectory() + "/MarvelComicsWiki/";

        new File(dir).mkdir();

        return dir;
    }

    /**
     * Gets the imagem from the url
     *
     * @param fileName
     * @param imageUrl
     * @return
     */
    public static Bitmap getFromUrl(String fileName, String imageUrl) {
        File file = new File(getImagesPath() + fileName);

        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            saveFromUrl(fileName, imageUrl);
            return getFromUrl(fileName, imageUrl);
        }
    }

    /**
     * Saves file from given url
     *
     * @param fileName the file name
     * @param imageUrl the image url
     * @return if the image was successfull saved
     */
    public static boolean saveFromUrl(String fileName, String imageUrl) {

        try {

            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(getImagesPath() + fileName);

            byte data[] = new byte[1024];


            int count = -1;

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
