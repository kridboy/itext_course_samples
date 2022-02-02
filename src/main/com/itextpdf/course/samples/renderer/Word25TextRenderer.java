package main.com.itextpdf.course.samples.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;

public class Word25TextRenderer extends TextRenderer {
    private int count = 0;

    public Word25TextRenderer(Text textElement, int count) {
        super(textElement);
        this.count = count;
    }

    // If a renderer overflows on the next area, iText uses #getNextRenderer() method to create a new renderer for the overflow part.
    // If #getNextRenderer() isn't overridden, the default method will be used and thus the default rather than the custom
    // renderer will be created
    @Override
    public IRenderer getNextRenderer() {
        return new Word25TextRenderer((Text) modelElement, count);
    }

    //Gets called when content is drawn to page
    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);

        // Draws a line to delimit the text every 25 words
        if (0 == count % 25) {
            Rectangle textRect = getOccupiedAreaBBox();
            int pageNumber = getOccupiedArea().getPageNumber();
            PdfCanvas canvas = drawContext.getCanvas();
            Rectangle pageRect = drawContext.getDocument().getPage(pageNumber).getPageSize();
            canvas.saveState()
                    .setLineDash(5, 5)
                    .moveTo(pageRect.getLeft(), textRect.getBottom())
                    .lineTo(textRect.getRight(), textRect.getBottom())
                    .lineTo(textRect.getRight(), textRect.getTop())
                    .lineTo(pageRect.getRight(), textRect.getTop())
                    .stroke()
                    .restoreState();
        }
    }
}