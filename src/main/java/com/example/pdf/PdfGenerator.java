package com.example.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    /**
     * Gera um PDF simples com o texto indicado
     * @param title título do documento
     * @param content corpo do documento
     * @return array de bytes representando o PDF
     */
    public static byte[] generateSimplePdf(String title, String content) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph(title, titleFont));
            document.add(Chunk.NEWLINE);

            // Corpo
            Font bodyFont = new Font(Font.HELVETICA, 12);
            document.add(new Paragraph(content, bodyFont));

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Erro ao gerar PDF", e);
        }

        return outputStream.toByteArray();
    }
}

