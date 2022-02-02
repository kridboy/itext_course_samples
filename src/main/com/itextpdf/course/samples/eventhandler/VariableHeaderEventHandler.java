package main.com.itextpdf.course.samples.eventhandler;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;

public class VariableHeaderEventHandler implements IEventHandler {
    protected String header;

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public void handleEvent(Event currentEvent) {
        PdfDocumentEvent documentEvent = (PdfDocumentEvent) currentEvent;
        PdfPage page = documentEvent.getPage();
        Canvas canvas = new Canvas(page, page.getPageSize());

        canvas.showTextAligned(header, 490, 806, TextAlignment.CENTER);

        if (documentEvent.getDocument().getPage(1).equals(page))
            canvas.showTextAligned("First Header", page.getPageSize().getWidth() - 5f, page.getPageSize().getHeight() - 20f, TextAlignment.RIGHT);
        else
            canvas.showTextAligned("Next Header", page.getPageSize().getWidth() - 5f, page.getPageSize().getHeight() - 20f, TextAlignment.RIGHT);
    }
}
