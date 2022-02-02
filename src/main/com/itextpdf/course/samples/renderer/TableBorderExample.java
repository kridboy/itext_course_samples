package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;

import static main.com.itextpdf.course.tools.Constants.LOREM_IPSUM;

public class TableBorderExample {
    public static final SolidBorder BLUE_BORDER = new SolidBorder(new DeviceRgb(20, 20, 240), .5f);
    public static final float SMALL_CELL_WIDTH = 15f;
    public static final float TABLE_WIDTH = 506.25f;
    public static final float LARGE_CELL_WIDTH = 491.25f;

    public static void main(String[] args) throws IOException {
        new TableBorderExample().createDocument();
    }

    public void createDocument() throws IOException {
        String dest = "src/main/resources/table_borders.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        Paragraph loremParagraph = new Paragraph(LOREM_IPSUM+" "+LOREM_IPSUM).setMarginTop(20).setMarginBottom(20);

        for (int i = 0; i < 10; i++) {
            Table table = new Table(2);
            table.setWidth(TABLE_WIDTH);
            addDecoratedCellsToTable(table);
            TableBorderRenderer tbr = new TableBorderRenderer(table);
            table.setNextRenderer(tbr);

            doc.add(table);
            doc.add(loremParagraph);
        }
        doc.close();
    }

    private void addDecoratedCellsToTable(Table table) throws IOException {
        Cell cell, cell2;
        cell = createCell(SMALL_CELL_WIDTH, "0", createBorderArr(Border.NO_BORDER, BLUE_BORDER, BLUE_BORDER, Border.NO_BORDER));
        cell2 = createCell(LARGE_CELL_WIDTH, "First row", createBorderArr(Border.NO_BORDER, Border.NO_BORDER, BLUE_BORDER, BLUE_BORDER));
        table.addCell(cell);
        table.addCell(cell2);

        for (int j = 1; j <= 10; j++) {
            cell = createCell(SMALL_CELL_WIDTH, String.valueOf(j), createBorderArr(BLUE_BORDER, BLUE_BORDER, Border.NO_BORDER, Border.NO_BORDER));
            cell2 = createCell(LARGE_CELL_WIDTH, "Row Number - " + j, createBorderArr(BLUE_BORDER, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER));
            table.addCell(cell);
            table.addCell(cell2);
        }

        cell = createCell(SMALL_CELL_WIDTH, "11", createBorderArr(BLUE_BORDER, BLUE_BORDER, Border.NO_BORDER, Border.NO_BORDER));
        cell2 = createCell(LARGE_CELL_WIDTH, "Last Row", createBorderArr(BLUE_BORDER, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER));
        table.addCell(cell);
        table.addCell(cell2);
    }


    //A convenience method so we can tell what border we are filling in code
    private Border[] createBorderArr(Border top, Border right, Border bottom, Border left) {
        return new Border[]{top,right,bottom,left};
    }

    private Cell createCell(float width, String text, Border[] borders){
        Text t = new Text(text)
                .setFontSize(11f);
        Paragraph p = new Paragraph()
                .setFixedLeading(0f)
                .setMultipliedLeading(.86f);
        Cell c = new Cell(1, 1);

        c.add(p.add(t))
                .setWidth(width)
                .setPadding(3f)
                .setMinHeight(10f)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setTextAlignment(TextAlignment.LEFT)
                .setKeepTogether(true)
                .setNextRenderer(new CustomCellColorRenderer(c,"A"));

        c.setBorderTop(borders[0] == null ? Border.NO_BORDER : borders[0]);
        c.setBorderRight(borders[1] == null ? Border.NO_BORDER : borders[1]);
        c.setBorderBottom(borders[2] == null ? Border.NO_BORDER : borders[2]);
        c.setBorderLeft(borders[3] == null ? Border.NO_BORDER : borders[3]);

        return c;
    }
}
