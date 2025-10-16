package com.example.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class QRCodeGenerator {

    /**
     * Gera um QR Code a partir de um texto (geralmente uma URL)
     * @param text Conteúdo do QR Code (ex: URL)
     * @param width Largura da imagem
     * @param height Altura da imagem
     * @return Array de bytes representando a imagem PNG do QR Code
     */
    public static byte[] generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        }
    }

    /**
     * (Opcional) Salva o QR Code gerado num ficheiro PNG
     */
    public static void saveToFile(String text, int width, int height, Path filePath)
            throws WriterException, IOException {
        byte[] imageData = generateQRCodeImage(text, width, height);
        Files.write(filePath, imageData);
    }
}
