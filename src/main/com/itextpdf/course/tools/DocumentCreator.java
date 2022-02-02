package main.com.itextpdf.course.tools;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static main.com.itextpdf.course.tools.Constants.BASE_URI;
import static main.com.itextpdf.course.tools.Constants.LOREM_IPSUM;

public class DocumentCreator {
    private DocumentCreator() {
    }

    public static void main(String[] args) throws IOException {
        createFolders();
        writePdfBytes(createSimpleDoc(), "file.pdf");
    }

    static void createFolders() {
        String[] folderNames = new String[]{"canvas", "eventhandler", "extra", "renderer"};
        for (String foldername : folderNames) {
            File f = new File(BASE_URI + "files/" + foldername);
            f.mkdirs();
        }
    }

    static void writePdfBytes(byte[] pdfBytes, String fileName) throws IOException {
        Files.write(Path.of(BASE_URI + "extra/" + fileName), pdfBytes);
    }

    public static byte[] createSimpleDoc() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);
        doc.add(new Paragraph("Hello, World!"))
                .close();
        return baos.toByteArray();
    }

    public static byte[] createDocWithRandomPageSizes(int pages) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Random rand = new Random();
        for (int i = 1; i <= pages; i++) {
            float base = 50f,
                    width = base + rand.nextInt(5000),
                    height = base + rand.nextInt(5000);

            PdfCanvas pdfC = new PdfCanvas(pdf.addNewPage(new PageSize(width, height)));
            pdfC.moveTo(10, height - 40)
                    .beginText()
                    .setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 20)
                    .showText("Page " + i)
                    .endText()
                    .release();
        }
        pdf.close();
        return baos.toByteArray();
    }

    public static byte[] createDocWithPages(int pages) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);
        LineSeparator line = new LineSeparator(new DottedLine(1, 2)).setMarginTop(-4);
        Paragraph latinP = new Paragraph(LOREM_IPSUM);
        AreaBreak br = new AreaBreak();

        for (int i = 1; i <= pages; i++) {
            doc.add(new Paragraph("Page " + i).setFontSize(20));
            doc.add(line);
            doc.add(latinP);
            doc.add(latinP);
            if (!(i == pages))
                doc.add(br);
        }
        doc.close();

        return baos.toByteArray();
    }

    public static byte[] createDocWithTable(int rows) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);
        Table t = new Table(3);

        for (int i = 1; i <= rows; i++) {
            String s = "Row ";
            t.addCell(new Cell().add(new Paragraph(s + i)));
            t.addCell(new Cell().add(new Paragraph(s + i)));
            t.addCell(new Cell().add(new Paragraph(s + i)));
        }

        doc.close();
        return baos.toByteArray();
    }

}
