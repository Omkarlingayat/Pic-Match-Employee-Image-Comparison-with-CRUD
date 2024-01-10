package crud.demo;

import java.util.Arrays;

public class ImageComparisonUtils {
    public static boolean areImagesEqual(byte[] image1, byte[] image2){
        return Arrays.equals(image1, image2);
    }
}
