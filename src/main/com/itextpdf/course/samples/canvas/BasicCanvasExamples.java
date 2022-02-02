package main.com.itextpdf.course.samples.canvas;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import main.com.itextpdf.course.tools.DocumentCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static main.com.itextpdf.course.tools.Constants.BASE_URI;

public class BasicCanvasExamples {
    public static void main(String[] args) throws IOException {
        new BasicCanvasExamples().addContentUnderExample(BASE_URI + "canvas/contentUnder.pdf");
    }

    void canvasFromPageExample(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(36, 650, 100, 100);

        //Draw rectangle at specific coordinates
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Paragraph p = createParagraph();

        canvas.add(p);
        canvas.close();
        pdf.close();
    }


    void canvasFromXObjectExample(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Rectangle rect = new Rectangle(100, 100);
        PdfFormXObject xObject = new PdfFormXObject(rect);
        PdfCanvas pdfCanvas = new PdfCanvas(xObject, pdf);

        pdfCanvas.moveTo(0, 0)
                .lineTo(0, 100)
                .lineTo(100, 100)
                .lineTo(100, 0)
                .lineTo(0, 0)
                .stroke();

//        Canvas canvas = new Canvas(xObject,pdf);
        Canvas canvas = new Canvas(pdfCanvas, rect);
        Paragraph p = createParagraph();
        canvas.add(p);

        for (int i = 0; i < 5; i++) {
            PdfCanvas c = new PdfCanvas(pdf.addNewPage());
            c.addXObjectAt(xObject, 50, 200 + (i * 100));
        }

        pdf.close();
    }

    void addContentUnderExample(String dest) throws IOException {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(DocumentCreator.createDocWithPages(5)));
        PdfDocument pdf = new PdfDocument(reader, new PdfWriter(dest));
        PdfPage page = pdf.getPage(2);

        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdf);
//        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(),page.getResources(),pdf);
        pdfCanvas.saveState()
                .setFillColor(ColorConstants.LIGHT_GRAY)
                .rectangle(page.getPageSize())
                .fill()
                .restoreState();

        pdf.close();
    }

    private Paragraph createParagraph() throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
        Text author = new Text("Robert Louis Stevenson").setFont(font);
        return new Paragraph().add(title).add(" by ").add(author);
    }
}
