package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter class
 */
class ImageWriterTest {

    int nX = 800;
    int nY = 500;

    // Create a yellow color and a red color
    Color yellowColor = new Color(255d, 255d, 0d); // Yellow is a combination of red end green
    Color redColor = new Color(255d, 0d, 0d);


    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("Image writer test", nX, nY);
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, redColor);
                } else {
                    imageWriter.writePixel(j, i, yellowColor);
                }
            }
        }
        // Write the image to the file
        imageWriter.writeToImage();
    }
}