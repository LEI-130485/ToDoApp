package com.example.pdf;

import com.example.pdf.PdfGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Route("pdf")
public class PdfView extends VerticalLayout {

    public PdfView() {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);

        Label title = new Label("Gerar relatório PDF");

        Button generateButton = new Button("Gerar PDF", event -> {
            try {
                byte[] pdfData = PdfGenerator.generateSimplePdf(
                        "Relatório de Exemplo",
                        "Gerado em " + LocalDateTime.now() + "\n\nEste é um exemplo de PDF gerado em Java com Vaadin."
                );

                StreamResource resource = new StreamResource("relatorio.pdf",
                        () -> new ByteArrayInputStream(pdfData));

                Anchor downloadLink = new Anchor(resource, "Baixar PDF");
                downloadLink.getElement().setAttribute("download", true);

                add(downloadLink);

            } catch (IOException e) {
                add(new Label("Erro ao gerar PDF: " + e.getMessage()));
            }
        });

        add(title, generateButton);
    }
}
