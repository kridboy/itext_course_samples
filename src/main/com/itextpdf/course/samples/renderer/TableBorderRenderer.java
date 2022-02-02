package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

class TableBorderRenderer extends TableRenderer {
    public TableBorderRenderer(Table modelElement) {
        super(modelElement);
    }

    @Override
    public IRenderer getNextRenderer() {
        return new TableBorderRenderer((Table) modelElement);
    }

    @Override
    //Decorate Table borders
    public void drawBorders(DrawContext drawContext) {
        Rectangle rect = getOccupiedAreaBBox();
        //Gray border color
        Color color = new DeviceRgb(128, 128, 128);

        drawContext.getCanvas()
                .saveState()
                .setLineWidth(0.5f)
                .setStrokeColor(color)
                .roundRectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight(), 5)
                .stroke()
                .restoreState();
        super.drawBorders(drawContext);
    }

    //When table is split we don't want to render certain borders on the first row of cells of the next page
    @Override
    protected TableRenderer createSplitRenderer(Table.RowRange rowRange) {
        Table t = ((Table) this.getModelElement());
        int columns = t.getNumberOfColumns();

        //Using row range info of split renderer we can select required cells.
        for (int j = 0; j < columns; j++) {
            Cell c = t.getCell(rowRange.getFinishRow(), j);
            if (c != null) {
                c.setBorderTop(Border.NO_BORDER);
                c.setBorderBottom(Border.NO_BORDER);
            }
        }
        return super.createSplitRenderer(rowRange);
    }
}