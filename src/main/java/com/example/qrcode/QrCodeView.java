package com.example.qrcode;

import com.example.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Route("qrcode")
public class QrCodeView extends VerticalLayout {

    public QrCodeView() {
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setSpacing(true);

        Label title = new Label("QR Code para o site do projeto:");
        String link = "https://www.exemplo.com"; // substitua pelo seu link

        try {
            byte[] qrImage = QRCodeGenerator.generateQRCodeImage(link, 250, 250);
            StreamResource resource = new StreamResource("qrcode.png",
                    () -> new ByteArrayInputStream(qrImage));

            Image qrCodeImage = new Image(resource, "QR Code");
            qrCodeImage.setWidth("250px");
            qrCodeImage.setHeight("250px");

            Anchor linkAnchor = new Anchor(link, "Abrir link");
            linkAnchor.setTarget("_blank");

            add(title, qrCodeImage, linkAnchor);

        } catch (WriterException | IOException e) {
            add(new Label("Erro ao gerar QR Code: " + e.getMessage()));
        }
    }
}
