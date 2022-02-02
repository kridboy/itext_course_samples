package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.DivRenderer;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CustomMultiColumnRenderer extends DivRenderer {
    private static final float ADDITIONAL_VERTICAL_SPACE = 30;
    private static final float COLUMN_HORIZONTAL_SPACING = 30;

    private int columnCount = 1;

    private static boolean testMode = false;

    public CustomMultiColumnRenderer(Div modelElement, int columnCount) {
        super(modelElement);
        this.columnCount = columnCount;

        this.setProperty(Property.COLLAPSING_MARGINS, false);
    }

    @Override
    public IRenderer getNextRenderer() {
        return new CustomMultiColumnRenderer((Div) this.modelElement, this.columnCount);
    }

    //Gets all rectangles that this IRenderer can draw upon in the given area.
    @Override
    public List<Rectangle> initElementAreas(LayoutArea area) {
        if (!testMode) {
            float columnWidth = (area.getBBox().getWidth() - 2 * COLUMN_HORIZONTAL_SPACING) / columnCount;
            // Calculate the area needed to place the content
            testMode = true;
            LayoutResult tempResult = this.layout(new LayoutContext(new LayoutArea(0, new Rectangle(0, 0, columnWidth, 1000000)))); //This turns into a recursive call
            testMode = false;
            // That's important to add some additional vertical space to each column, because of splits
            float columnHeight = Math.min(occupiedArea.getBBox().getHeight() / columnCount + ADDITIONAL_VERTICAL_SPACE, area.getBBox().getHeight());
            List<Rectangle> renderAreas = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                renderAreas.add(new Rectangle(area.getBBox().getLeft() + i * (columnWidth + COLUMN_HORIZONTAL_SPACING), area.getBBox().getTop() - columnHeight, columnWidth, columnHeight));
            }
            return renderAreas;
        } else {
            return Collections.singletonList(area.getBBox());
        }
    }
}