package main.com.itextpdf.course.samples.eventhandler;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.com.itextpdf.course.tools.Constants.LOREM_IPSUM;

public class Examples {
    public static void main(String[] args) throws Exception {
//        new Examples().variableHeaderExample("src/main/resources/test.pdf");
        new Examples().pageXofYExample("src/main/resources/test.pdf");
    }


    void pageXofYExample(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PageXofYEventHandler pageXofY = new PageXofYEventHandler(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pageXofY);
        Document doc = new Document(pdfDoc);
        Paragraph p = new Paragraph(LOREM_IPSUM);

        for (int i = 0; i < 5; i++)
            doc.add(p).add(new AreaBreak());

        pageXofY.writeTotal(pdfDoc);
        pdfDoc.close();
    }

    void variableHeaderExample(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        VariableHeaderEventHandler handler = new VariableHeaderEventHandler();
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

        //Creating some content
        for (int i = 2; i < 301; i++) {
            List<Integer> factors = getFactors(i);

            if (factors.size() == 1)
                doc.add(new Paragraph("This is a prime number!"));

            for (int factor : factors)
                doc.add(new Paragraph("Factor: " + factor));

            handler.setHeader(String.format("THE FACTORS OF %s", i));

            if (300 != i)
                doc.add(new AreaBreak());
        }

        doc.close();
    }

    //Helper method
    private static List<Integer> getFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }
}
