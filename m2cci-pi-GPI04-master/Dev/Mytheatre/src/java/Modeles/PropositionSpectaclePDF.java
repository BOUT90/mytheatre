package Modeles;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Cette classe permet de générer un document PDF document correspondant à un
 * ticket pour une epreuve sportive en utilisant la librairie apache PDFBox
 * (https://pdfbox.apache.org/).
 *
 * @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
 */
public class PropositionSpectaclePDF {

    // variable de classe pour calculer le numéro du ticket. 
    // cette variable compte le nombre de tickets qui ont été imprimés
    private static int nbTickets = 0;

    public static BufferedImage createQRC(String codeText, int size) throws WriterException {
        String fileType = "png";
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(codeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        int CrunchifyWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < CrunchifyWidth; i++) {
            for (int j = 0; j < CrunchifyWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;

    }

    /**
     * Crée en mémoire un document pdf correspondant à un ticket électronique
     *
     * @param proposition
     * @param spec
     * @param utilisateur
     * @param titulaire le nom du titulaire du billet
     * @param epreuve l'épreuve concernée
     * @param nbPlaces le nombre de places achetées
     * @param logoFileName le nom (chemin absolu) du fichier contenant l'image
     * logo à afficher en haut du ticket.
     * @return l'objet byte array contenant les données du fichier pdf
     * @throws IOException
     * @throws com.google.zxing.WriterException
     */
    public static byte[] createPDF_AsByteArray(InfoPropositionSpec proposition, UtilisateurInfos utilisateur) throws IOException, WriterException {

        //int noTicket; // le numéro du ticket
        // on utilise une variable locale et le numéro de ticket est calculé dans
        // un bloc synchronisé afin d'éviter que deux tickets puissent avoir le même numéro
//        synchronized (TicketPDF.class) {
//            nbTickets++;
//            noTicket = nbTickets;
//        }
        // Le tableau d'octets qui contiendra en mémoire le document pdf 
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            // on utilise un try avec ressource pour être sûr que le document pdf est bien fermé

            PDPage page = new PDPage();
            document.addPage(page);

            try ( // Définition d'un objet contentStream destiné à "contenir" le document qui va être créée.
                    // On utilise un try avec ressources  pour s'assurer que l'objet contentStream sera bien fermé
                    PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                //--------------------------------------------------------------
                // lecture du fichier image du logo et écriture de celui-ci
                // dans le contentStream
                //--------------------------------------------------------------
//                BufferedImage logo = ImageIO.read(new File(logoFileName));
//                PDImageXObject logoImage = LosslessFactory.createFromImage(document, logo);
//                contentStream.drawImage(logoImage, 10, 700);
                //--------------------------------------------------------------
                // Définition d'un texte donnant le numéro du ticket, le nom du
                // titulaire, le nom et la date de l'épreuve, le nombre de places.
                //--------------------------------------------------------------
                // création d'un nouvel objet font selectionnant l'une des polices de
                // base du PDF et association de celui-ci au contentStream
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 12);

                // affichage du numéro de ticket
//                contentStream.beginText();
//                contentStream.newLineAtOffset(140, 670); // positionnement du curseur là où le texte doit être affiché
//                contentStream.showText("Ticket N°" + String.format("%06d", noTicket)); // affichage du texte
//                contentStream.endText();
                // affichage du nom du titulaire du billet
//                contentStream.beginText();
//                contentStream.newLineAtOffset(140, 650);
//                contentStream.showText(titulaire);
//                contentStream.endText();
                // nom spectacle et date de spectacle
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 14);
                int debutline = 700;
                contentStream.newLineAtOffset(100, debutline);
                String text = "Proposition d'un spectacle par l'association: " + proposition.getNomAssociation();
                // contentStream.showText("Proposition d'un spectacle par l'association: " + utilisateur.getNomAssociation());
               // PDRectangle mediabox = page.getMediaBox();

                float startX = 0;
                float startY = 0;
                List<String> lines = getLines(text, 20, page);

                contentStream.moveTextPositionByAmount(startX, startY);
                float leading = 1.5f * 12;
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -leading);
                }
                contentStream.endText();
                int nb = lines.size();
                int y = debutline - nb * 30;
                // nom spectacle et date de spectacle
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 13);
                contentStream.newLineAtOffset(70, y);
                contentStream.showText("Information personnelles");
                contentStream.endText();

                // nom spectacle et date de spectacle
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 20);
                contentStream.showText("" + utilisateur.getNom() + " " + utilisateur.getPrenom());
                contentStream.endText();

                // affichage du nom de l'épreuve
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 40);
                contentStream.showText("Mail: " + utilisateur.getMail());
                contentStream.endText();

                // affichage du nom de l'épreuve
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 60);
                contentStream.showText("Tel: " + utilisateur.getTel());
                contentStream.endText();

                // affichage du nom de l'épreuve
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 80);
                contentStream.showText("Adresse: " + utilisateur.getRue());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
                contentStream.newLineAtOffset(70, y - 110);
                contentStream.showText("Information Spectacle:");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 130);
                contentStream.showText("Nom spectacle:" + proposition.getTitreSpec());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 150);
                contentStream.showText("Durée:" + proposition.getDuree());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 170);
                contentStream.showText("Prix unitaire:" + proposition.getPrixUnitaireRepresentation());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 200);
                contentStream.showText("Autres informations:" + proposition.getInfoSupp());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(90, y - 220);
                contentStream.showText("URL:" + proposition.getUrlSpectacle());
                contentStream.endText();

                if (proposition.getResume().equals("")) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.newLineAtOffset(90, y - 240);
                     contentStream.showText("Pas de résumé");
                    contentStream.endText();
                    y = y - 260 ;
                    // contentStream.showText("Proposition d'un spectacle par l'association: " + utilisateur.getNomAssociation());
                } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.newLineAtOffset(90, y - 240);

                    float startX1 = 0;
                    float startY2 = 0;
                    String text1 = "Résumé: "+proposition.getResume();
                    List<String> lines1 = getLines(text1, 10, page);

                    contentStream.moveTextPositionByAmount(startX1, startY2);
                    float leading1 = 1.5f * 12;
                    for (String line : lines1) {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -leading1);
                    }
                    contentStream.endText();

                    int nb1 = lines1.size();
                    y = y - 240 - nb1 * 20;
                    //contentStream.setLeading();
                    //PDImageXObject pdImage = PDImageXObject. createFromFile ("C:/logo.png", doc);
                    // contentstream.drawImage(pdImage, 70, 250) ;
                 
                }
                for (int i = 0; i < proposition.getListeDates().size(); i++) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.newLineAtOffset(90, y - (i * 20));
                    contentStream.showText("Représentation" + (i + 1) + ": " + proposition.getListeDates().get(i) + " " + proposition.getListeheures().get(i));
                    contentStream.endText();
                }

                //---------------------------------------------------------------------------
                // appel du web service qrickit.com pour récupérer en une image d'un code QRC
                // et affichage de celle-ci dans le document PDF.
                // Pour plus d'informations sur l'API de ce webservice et les différents 
                // paramètres possibles: http://qrickit.com/qrickit_apps/qrickit_api.php
                // sur les machine de l'ufr cela ne marche pas à cause du proxy....
                // une solution technique est en cours de recherche
                //---------------------------------------------------------------------------
//                // le texte à encoder avec le QR Code
//                String data = "Ticket N°" + String.format("%06d", noTicket);
//                // attention ce texte devant être passé en paramètre d'une requête HTTP GET
//                // il doit être "url encodé" (pour gérer correctement les espaces et caractères non ASCII).
//                data = URLEncoder.encode(data, "UTF-8");
//                // appel du service web pour obtenir l'image du QR code
//                BufferedImage awtImage = ImageIO.read(new URL(
//                        "http://qrickit.com/api/qr?d=" + data
//                        + "&qrsize=100&t=p&e=m"));
////                // écriture de l'image dans le fichier pdf
//                PDImageXObject ximage = LosslessFactory.createFromImage(document, createQRC(data, 100));
//                contentStream.drawImage(ximage, 20, 580);
                //--------------------------------------------------------------
                // Sauvegarde du document pdf dans l'objet ByteArrayOutputStream
                //--------------------------------------------------------------
                // avant toute chose l'objet ContentStream doit être fermé
                contentStream.close();

                document.save(out);
                return out.toByteArray();
            }
        }
    }

    public static List<String> getLines(String text, float fontS, PDPage page) throws IOException {
        PDFont pdfFont = PDType1Font.HELVETICA;
        float fontSize = fontS;
        float leading = 1.5f * fontSize;

        PDRectangle mediabox = page.getMediaBox();
        float margin = 72;
        float width = mediabox.getWidth() - 2 * margin;
//    float startX = mediabox.getLowerLeftX() + margin;
//    float startY = mediabox.getUpperRightY() - margin;

        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;
        if (text != null){
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                spaceIndex = text.length();
            }
            String subString = text.substring(0, spaceIndex);
            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
            System.out.printf("'%s' - %f of %f\n", subString, size, width);
            if (size > width) {
                if (lastSpace < 0) {
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                System.out.printf("'%s' is line\n", subString);
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                System.out.printf("'%s' is line\n", text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        }
        return lines;
    }

}
