package main.com.itextpdf.course.samples.extra;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfPatternCanvas;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfShading;
import com.itextpdf.layout.Document;

import java.io.IOException;

public class PatternExamples {

    public static void main(String[] args) throws IOException {
        new PatternExamples().patternedCanvasExample("src/main/resources/test.pdf");

    }

    void gradientShadingExample(String dest) throws Exception {
        PageSize pageSize = new PageSize(150, 300);
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        pdfDoc.setDefaultPageSize(pageSize);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), 0, pageSize.getHeight(),
                ColorConstants.WHITE.getColorValue(), 0, 0, ColorConstants.GREEN.getColorValue());
        PdfPattern.Shading pattern = new PdfPattern.Shading(axial);
        canvas.setFillColorShading(pattern);
        canvas.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
        canvas.fill();

        pdfDoc.close();
    }

    void patternedCanvasExample(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        String fillText = "this is the fill text! ";
        float fillTextWidth = font.getWidth(fillText, 6);

        PdfPattern.Tiling tilingPattern = new PdfPattern.Tiling(fillTextWidth, 60, fillTextWidth, 60);
        PdfPatternCanvas patternCanvas = new PdfPatternCanvas(tilingPattern, pdfDoc);
        patternCanvas.beginText().setFontAndSize(font, 6.f);
        float x = 0;
        for (float y = 0; y < 60; y += 10) {
            patternCanvas.setTextMatrix(x - fillTextWidth, y);
            patternCanvas.showText(fillText);
            patternCanvas.setTextMatrix(x, y);
            patternCanvas.showText(fillText);
            x += (fillTextWidth / 6);
        }
        patternCanvas.endText();
        //provide area to cover
        canvas.rectangle(10, 10, 575, 822);
        //set custom pattern
        canvas.setFillColor(new PatternColor(tilingPattern));
        canvas.fill();

        doc.close();
    }
}

