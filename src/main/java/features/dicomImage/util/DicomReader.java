package features.dicomImage.util;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.display.ConsumerFormatImageMaker;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds different methods for reading Dicom images.
 */
public class DicomReader {

    // TODO(sabrinamusatian): migrate from pixelmed.dicom.AttributeList to MedImageAttribute

    /**
     * Reads Dicom image attributes from a given local path.
     *
     * @param dicomInputFile a path to the dicom image on disk.
     * @return list with dicom attributes;
     */
    public static AttributeList readDicomImageAttributesFromLocalFile(String dicomInputFile) {
        AttributeList dicomAttributes = new AttributeList();
        try {
            dicomAttributes.read(dicomInputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dicomAttributes;
    }

    /**
     * Reads only pixel data of a Dicom image from a given {@link AttributeList}.
     *
     * @param attributeList a list of already read dicom attributes.
     * @return list with pixel data, e.g. list of slices in dicom image.
     */
    public static List<BufferedImage> readDicomImagePixelDataFromAttributeList(AttributeList attributeList) {
        ArrayList pixelData = new ArrayList();
        try {
            BufferedImage[] imgs = ConsumerFormatImageMaker.makeEightBitImages(attributeList);
            pixelData = new ArrayList<>(Arrays.asList(imgs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pixelData;
    }

    /**
     * Reads only pixel data of a Dicom image from a from a given local path.
     *
     * @param dicomInputFile a path to the dicom image on disk.
     * @return list with pixel data, e.g. list of slices in dicom image.
     */
    public static List<BufferedImage> readDicomImagePixelDataFromLocalFile(String dicomInputFile) {
        AttributeList attributeList = readDicomImageAttributesFromLocalFile(dicomInputFile);
        return readDicomImagePixelDataFromAttributeList(attributeList);
    }

}
