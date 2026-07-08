package com.eventticketing.ticket;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrCodeService {

    public String generateQrCode(String ticketCode) {
        try {
            Path qrFolder = Paths.get("qrcodes");

            if (!Files.exists(qrFolder)) {
                Files.createDirectories(qrFolder);
            }

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            var bitMatrix = qrCodeWriter.encode(
                    ticketCode,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            String fileName = ticketCode + ".png";
            Path path = qrFolder.resolve(fileName);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return "qrcodes/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("QR code generation failed");
        }
    }
}