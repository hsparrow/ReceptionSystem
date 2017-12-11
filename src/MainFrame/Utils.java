package MainFrame;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Provides general purpose methods for handling OpenCV-JavaFX data conversion.
 */
class Utils {

    /**
     * Converts a Mat object in the corresponding Image for JavaFX.
     *
     * @param frame the {@link Mat} representing the current frame
     * @return the {@link Image} to show
     */
    public static Image mat2Image(Mat frame) {
        try {
//            System.out.println("转好了！ok");
            return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
        } catch (Exception e) {
            System.err.println("Cannot convert the Mat object: " + e);
            return null;
        }
    }

    public static Image getProfileImage(int id) {
        String fileName = "src/Resources/profileImage/" + id + ".jpg";
        File file = new File(fileName);
        Image image = new Image(file.toURI().toString());
        return image;
    }

    /**
     * Saves image to pathToImage.
     *
     * @param image Image to save.
     * @param label Name of the image.
     */
    static void saveImage(String pathToImage, String imageName, Image image) {
        File outputFile = new File(pathToImage + imageName + ".jpg");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void export(String path, Mat photo, String photoName) {
        String targetPath = path;

        File file = new File(targetPath);

        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = targetPath + File.separator + photoName + ".jpg";
//        System.out.println(targetPath);
        Imgcodecs.imwrite(imagePath, photo);

    }

    /**
     * Updates the image in the ImageView.
     *
     * @param view
     * @param image
     */
    static void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * Generic method for putting element running on a non-JavaFX thread on the
     * JavaFX thread, to properly update the UI.
     *
     * @param property a {@link ObjectProperty}
     * @param value the value to set for the given {@link ObjectProperty}
     * @param <T>
     */
    public static <T> void onFXThread(final ObjectProperty<T> property, final T value) {
        Platform.runLater(() -> {
            property.set(value);
        });
    }

    /**
     * Supports for the mat2Image() method.
     *
     * @param original the {@link Mat} object in BGR or grayscale
     * @return the corresponding {@link BufferedImage}
     */
    private static BufferedImage matToBufferedImage(Mat original) {
        BufferedImage image = null;
        int width = original.width(), height = original.height(), channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0, 0, sourcePixels);

        if (original.channels() > 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return image;
    }
}
