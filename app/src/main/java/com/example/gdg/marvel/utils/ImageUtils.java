package com.example.gdg.marvel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.gdg.marvel.R;
import com.karumi.marvelapiclient.model.CharacterDto;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.EventResourceDto;
import com.karumi.marvelapiclient.model.MarvelImage;

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
     *
     * @param context
     * @return
     */
    public static Bitmap getRandomThumbnail(Context context) {
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
    private static String getImagesPath(String relativePath) {
        String dir = Environment.getExternalStorageDirectory() + "/MarvelComicsWiki/";

        if (relativePath != null && !relativePath.equals(""))
            dir += relativePath + "/";

        new File(dir).mkdir();

        return dir;
    }

    /**
     * Gets from char
     *
     * @param character
     * @return
     */
    public static Bitmap getFromCharacter(CharacterDto character) {

        // Monta o nome do arquivo
        String thumbnailFileName = character.getId() + "." + character.getThumbnail().getExtension();

        // Captura a imagem
        return getFromUrl(
                thumbnailFileName,
                character.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_AMAZING),
                "characters");
    }

    /**
     * Gets from char
     *
     * @param comic
     * @return
     */
    public static Bitmap getFromComic(ComicDto comic) {

        // Monta o nome do arquivo
        String thumbnailFileName = comic.getId() + "." + comic.getThumbnail().getExtension();

        // Captura a imagem
        return getFromUrl(
                thumbnailFileName,
                comic.getThumbnail().getImageUrl(MarvelImage.Size.PORTRAIT_INCREDIBLE),
                "comics");
    }

    /**
     * Gets from char
     *
     * @param event
     * @return
     */
    public static Bitmap getFromEvent(EventResourceDto event) {

//        // Monta o nome do arquivo
//        String thumbnailFileName = event.getResourceUri() + "." + character.getThumbnail().getExtension();
//
//        // Captura a imagem
//        return getFromUrl(
//                thumbnailFileName,
//                character.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_AMAZING),
//                "characters");
        return null;
    }


    /**
     * Gets the imagem from the url
     *
     * @param fileName
     * @param imageUrl
     * @return
     */
    public static Bitmap getFromUrl(String fileName, String imageUrl) {
        return getFromUrl(fileName, imageUrl, null);
    }

    public static Bitmap getFromUrl(String fileName, String imageUrl, String relativePath) {
        File file = new File(getImagesPath(relativePath) + fileName);

        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            saveFromUrl(fileName, imageUrl, relativePath);
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
    public static boolean saveFromUrl(String fileName, String imageUrl, String relativePath) {

        try {

            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(getImagesPath(relativePath) + fileName);

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
