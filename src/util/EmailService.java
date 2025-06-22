package util;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    public static void kirimStruk(String emailTujuan, String subjek, String isiEmail) {
        // --- KONFIGURASI PENGIRIM ---
        final String emailPengirim = "geralsnduk@gmail.com"; // Ganti dengan email Gmail Anda
        final String passwordPengirim = "npip kxdg pvyp obtj"; // Ganti dengan 16 karakter App Password dari Google

        // --- PROPERTI KONEKSI GMAIL ---
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // --- SESI AUTENTIKASI ---
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailPengirim, passwordPengirim);
            }
        };
        Session session = Session.getInstance(props, auth);

        // --- PROSES PENGIRIMAN ---
        try {
            Message pesan = new MimeMessage(session);
            pesan.setFrom(new InternetAddress(emailPengirim));
            pesan.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTujuan));
            pesan.setSubject(subjek);
            pesan.setText(isiEmail);

            new Thread(() -> {
                try {
                    Transport.send(pesan);
                    System.out.println("Email berhasil dikirim ke " + emailTujuan);
                } catch (MessagingException e) {
                     System.err.println("Gagal mengirim email: " + e.getMessage());
                }
            }).start();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}