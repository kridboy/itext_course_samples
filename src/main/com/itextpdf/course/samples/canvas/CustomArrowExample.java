package main.com.itextpdf.course.samples.canvas;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import main.com.itextpdf.course.tools.DocumentCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustomArrowExample {

    public static void main(String[] args) throws IOException {
        new CustomArrowExample().run();
    }

    void run() throws IOException {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(DocumentCreator.createDocWithPages(5)));
        PdfDocument pdf = new PdfDocument(reader, new PdfWriter("src/main/resources/test.pdf"));
        PdfCanvas c = new PdfCanvas(pdf.getPage(1));
        c.setStrokeColor(ColorConstants.RED);
        drawArrow(c, 20, 500, 50, 600, 2);
        pdf.close();
    }

    public void drawArrow(PdfCanvas pdfCanvas, float xStart, float yStart, float xEnd, float yEnd, float lineWidth) {
        double radAngle = Math.toRadians(30);

        double smallLineLength = 0.1;

        double x3, y3, x4, y4;
        double xCos = (xStart - xEnd) * Math.cos(radAngle);
        double xSin = (xStart - xEnd) * Math.sin(radAngle);

        double yCos = (yStart - yEnd) * Math.cos(radAngle);
        double ySin = (yStart - yEnd) * Math.sin(radAngle);

        //Determine coordinates of line-end points of "wings"
        x3 = xEnd + (smallLineLength) * (xCos + ySin);
        y3 = yEnd + (smallLineLength) * (yCos - xSin);
        x4 = xEnd + (smallLineLength) * (xCos - ySin);
        y4 = yEnd + (smallLineLength) * (yCos + xSin);

        pdfCanvas.setLineCapStyle(PdfCanvasConstants.LineCapStyle.ROUND)
                .setLineWidth(lineWidth)
                .moveTo(xStart, yStart)
                .lineTo(xEnd, yEnd)

                .moveTo(x3, y3)
                .lineTo(xEnd, yEnd)

                .moveTo(x4, y4)
                .lineTo(xEnd, yEnd)
                .stroke()
                .release();

        //System.out.printf("x3=[%.2f],y3=[%.2f]||x4=[%.2f],y4=[%.2f]%n", x3, y3, x4, y4);
    }
}
