package Modeles;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.vandeseer.pdfbox.easytable.Cell;
import org.vandeseer.pdfbox.easytable.Row.RowBuilder;
import org.vandeseer.pdfbox.easytable.Table.TableBuilder;
import org.vandeseer.pdfbox.easytable.TableDrawer;

/**
 * Cette classe permet de générer un document PDF document correspondant à un
 * ticket pour une epreuve sportive en utilisant la librairie apache PDFBox
 * (https://pdfbox.apache.org/).
 *
 * @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
 */
public class TicketPDF {

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
     * @param listeTickets
     * @param spec
     * @param titulaire le nom du titulaire du billet
     * @param epreuve l'épreuve concernée
     * @param nbPlaces le nombre de places achetées
     * @param logoFileName le nom (chemin absolu) du fichier contenant l'image
     * logo à afficher en haut du ticket.
     * @return l'objet byte array contenant les données du fichier pdf
     * @throws IOException
     * @throws com.google.zxing.WriterException
     */
    public static byte[] createPDF_AsByteArray(List<TicketRecap> listeTickets, SpectacleDate spec, String date, int numDossier, int prixTotal, float remise) throws IOException, WriterException {

        //int noTicket; // le numéro du ticket
        // on utilise une variable locale et le numéro de ticket est calculé dans
        // un bloc synchronisé afin d'éviter que deux tickets puissent avoir le même numéro
//        synchronized (TicketPDF.class) {
//            nbTickets++;
//            noTicket = nbTickets;
//        }
        // Le tableau d'octets qui contiendra en mémoire le document pdf 
        int nbTickes = listeTickets.size();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date dateCurrent = new Date();
        String dateStr = dateFormat.format(dateCurrent);

        // Define the table structure first
        TableBuilder tableBuilder = new TableBuilder()
                .addColumnOfWidth(80)
                .addColumnOfWidth(80)
                .addColumnOfWidth(120)
                .addColumnOfWidth(80)
                .setFontSize(8)
                .setFont(PDType1Font.HELVETICA);

// Header ...
        tableBuilder.addRow(new RowBuilder()
                .add(Cell.withText("Place").withAllBorders())
                .add(Cell.withText("Rang").withAllBorders())
                .add(Cell.withText("Zone").withAllBorders())
                .add(Cell.withText("Prix").withAllBorders())
                .build());

// ... and some cells
        for (int i = 0; i < nbTickes; i++) {
            tableBuilder.addRow(new RowBuilder()
                    .add(Cell.withText(listeTickets.get(i).getNoPlace()).withAllBorders())
                    .add(Cell.withText(listeTickets.get(i).getNoRang()).withAllBorders())
                    .add(Cell.withText(listeTickets.get(i).getNomC()).withAllBorders())
                    .add(Cell.withText(listeTickets.get(i).getPrix()).withAllBorders())
                    .setBackgroundColor(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE)
                    .build());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            // on utilise un try avec ressource pour être sûr que le document pdf est bien fermé

            final PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try ( // Définition d'un objet contentStream destiné à "contenir" le document qui va être créée.
                    // On utilise un try avec ressources  pour s'assurer que l'objet contentStream sera bien fermé
                    final PDPageContentStream contentStream = new PDPageContentStream(document, page);) {
// Define the starting point
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(240, 750);
                contentStream.showText("Récapitulatif de votre achat");
                contentStream.endText();

                // affichage du numéro de dossier 
                contentStream.beginText();
                contentStream.newLineAtOffset(70, 720);
                contentStream.showText("Numéro de dossier: " + numDossier);
                contentStream.endText();

                // date d'achat
                contentStream.beginText();
                contentStream.newLineAtOffset(70, 700);
                contentStream.showText("Date d'achat: " + dateStr);
                contentStream.endText();

                // nom spectacle et date de spectacle
                contentStream.beginText();
                contentStream.newLineAtOffset(70, 680);
                contentStream.showText("Spectacle: " + spec.getNomSpectacle() + ": " + date);
                contentStream.endText();

                final float startY = page.getMediaBox().getHeight() - 180;
                final int startX = 70;
                final float y = startY - ((nbTickes + 1) * 16);

                if (listeTickets.size() < 10) {
                    // affichage du prix total
                    contentStream.beginText();
                    contentStream.newLineAtOffset(70, y - 30);
                    contentStream.showText("Prix Total: " + prixTotal + "€");
                    contentStream.endText();
                } else {
                    // affichage du la remise
                    contentStream.beginText();
                    contentStream.newLineAtOffset(70, y - 30);
                    contentStream.showText("Remise: " + remise + "€");
                    contentStream.endText();

                    float prixtotalApresRemise = prixTotal - remise;
                    // affichage Total avec remise 
                    contentStream.beginText();
                    contentStream.newLineAtOffset(70, y - 50);
                    contentStream.showText("Prix total avec remise : " + prixtotalApresRemise + "€");
                    contentStream.endText();
                }

// Draw!
                (new TableDrawer(contentStream, tableBuilder.build(), startX, startY)).draw();
                contentStream.close();
                document.save(out);
                return out.toByteArray();

            }

        }
    }
}
