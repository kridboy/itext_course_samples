package main.com.itextpdf.course.samples.extra;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import main.com.itextpdf.course.tools.DocumentCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static main.com.itextpdf.course.tools.Constants.BASE_URI;
import static main.com.itextpdf.course.tools.Constants.LOREM_IPSUM;

public class Examples {
    public static void main(String[] args) throws IOException{
        new Examples().onlyAddContentWhenItFitsExample("src/main/resources/test.pdf");
    }
    void addPageNumbers(Document doc) {
        int numberOfPages = doc.getPdfDocument().getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            int x = 500, y = 20;
            Paragraph p = new Paragraph(String.format("page %s of %s", i, numberOfPages));
            p.setFontColor(ColorConstants.GRAY);
            doc.showTextAligned(p, x, y, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }

    void copyPagesAsXObjectsExample() throws IOException {
        PdfReader reader = new PdfReader(new ByteArrayInputStream(DocumentCreator.createDocWithPages(5)));
        PdfDocument pdfIn= new PdfDocument(reader);
        PdfDocument pdfOut = new PdfDocument(new PdfWriter(BASE_URI+"test.pdf"));
        Document doc = new Document(pdfOut);
        PdfFormXObject pageXObject = pdfIn.getPage(2).copyAsFormXObject(pdfOut);

        pdfIn.close();
        doc.close();
    }

    void onlyAddContentWhenItFitsExample(String dest)throws IOException{
        PdfDocument pdf= new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdf);
        Paragraph p = new Paragraph(LOREM_IPSUM+LOREM_IPSUM+LOREM_IPSUM);

        for (int i = 0; i < 20; i++) {
            if(elementFitsCurrentPage(doc,p)){
                doc.add(p);
            }
            else{
                doc.add(new AreaBreak());
                doc.add(p);
            }
        }
        doc.close();
    }

    private boolean elementFitsCurrentPage(Document doc, IBlockElement element){
        Div d = new Div();
        d.add(element);
        IRenderer rst = d.createRendererSubTree().setParent(doc.getRenderer());
        LayoutResult lr = rst.layout(new LayoutContext(new LayoutArea(0, doc.getPageEffectiveArea(PageSize.A4))));
        float pageHeightAvailable = doc.getRenderer().getCurrentArea().getBBox().getHeight();
        float optimalHeightNeeded = lr.getOccupiedArea().getBBox().getHeight();

        return pageHeightAvailable>optimalHeightNeeded;
    }
}
