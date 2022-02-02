package main.com.itextpdf.course.samples.canvas;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;
import main.com.itextpdf.course.tools.DocumentCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static main.com.itextpdf.course.tools.Constants.BASE_URI;

public class WatermarkExample {

    public static void main(String[] args) throws IOException {
        new WatermarkExample().run();
    }

    void run() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(DocumentCreator.createDocWithRandomPageSizes(20));
        PdfDocument pdf = new PdfDocument(new PdfReader(bais), new PdfWriter(BASE_URI + "watermarked_doc.pdf"));

        for (int i = 1; i <= pdf.getNumberOfPages(); i++)
            addWaterMarkOnPage(pdf.getPage(i), "WATERMARK 2022 - " + i);

        pdf.close();
    }

    void addWaterMarkOnPage(PdfPage page, String wmString) {
        PdfCanvas pdfCanvas = new PdfCanvas(page); //select page to write onto
        Canvas canvas = new Canvas(pdfCanvas, page.getPageSize());

        PdfExtGState gs1 = new PdfExtGState(); //this object holds opacity parameter
        gs1.setFillOpacity(0.5f);
        pdfCanvas.setExtGState(gs1); //add to canvas to control opacity

        //vw and vh give us a way to handle different page sizes dynamically
        Rectangle pgSize = page.getPageSize();
        float vw = page.getPageSize().getWidth() / 100;
        float vh = page.getPageSize().getHeight() / 100;

        canvas.setFontColor(ColorConstants.RED)
                .setFontSize(Math.min(vw, vh) * 8)
                .showTextAligned(wmString, pgSize.getWidth() / 2, pgSize.getHeight() / 2, TextAlignment.CENTER, (float) Math.toRadians(45))
                .close();
    }
}
