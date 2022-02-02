package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.CellRenderer;

public class CustomCellColorRenderer extends CellRenderer {
    public CustomCellColorRenderer(Cell modelElement, String value) {
        super(modelElement);
        Paragraph p = (Paragraph) modelElement.getChildren().get(0);
        if (p != null) {
            Text content = (Text) p.getChildren().get(0);
            if (content != null && content.getText().contains(value))
                modelElement.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        }
    }

}
