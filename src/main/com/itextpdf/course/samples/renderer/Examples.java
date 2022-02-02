package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.IOException;

import static main.com.itextpdf.course.tools.Constants.LOREM_IPSUM;

public class Examples {
    public static void main(String[] args) throws IOException {
        //new Examples().multipleColumnDocumentExample("src/main/resources/test.pdf");
        new Examples().textRendererExample("src/main/resources/test.pdf");
    }

    void multipleColumnDocumentExample(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PageSize ps = pdf.getDefaultPageSize();
        Document document = new Document(pdf);
        //Set column parameters
        float offSet = 36;

        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3; //devided by columns
        float columnHeight = ps.getHeight() - offSet * 2;

        //Define column areas
        Rectangle[] columns = new Rectangle[]{
                new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)
        };
        document.setRenderer(new ColumnDocumentRenderer(document, columns));

        Paragraph p = new Paragraph(LOREM_IPSUM+" " + LOREM_IPSUM);
        for (int i = 0; i < 10; i++) {
            document.add(p);
        }
        document.close();
    }

    void multipleColumnDivExample(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdf);
        doc.add(new Paragraph(LOREM_IPSUM).setMarginBottom(20f));
        StringBuilder bigText = new StringBuilder();

        for (int i = 0; i < 30; i++)
            bigText.append(LOREM_IPSUM + " ");

        Div d = new Div();
        d.add(new Paragraph(bigText.toString()));
        d.setNextRenderer(new CustomMultiColumnRenderer(d, 2));

        doc.add(d);
        doc.close();
    }

    void coloredCellExample(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        Table t = new Table(5);
        String[] words = (LOREM_IPSUM + LOREM_IPSUM).split(" ");
        for (String word : words) {
            Cell c = new Cell();
            c.add(new Paragraph(word))
                    .setNextRenderer(new CustomCellColorRenderer(c, "lor"));
            t.addCell(c);
        }
        doc.add(t);
        doc.close();
    }

    void textRendererExample(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        String[] words = (LOREM_IPSUM + LOREM_IPSUM).split(" ");
        Paragraph paragraph = new Paragraph();
        Text text = null;
        int i = 0;
        for (String word : words) {
            if (text != null) {
                paragraph.add(" ");
            }

            text = new Text(word);
            text.setNextRenderer(new Word25TextRenderer(text, ++i));
            paragraph.add(text);
        }
        doc.add(paragraph);

        doc.close();
    }
}
