package Modeles;

import Controleurs.ClasseHelp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.awt.SystemColor.text;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
public class InfoSpectaclePDF {

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
    public static byte[] createPDF_AsByteArray(SpectacleDate spectacle, List<Integer> listePlacesDispo) throws IOException, WriterException {

        //int noTicket; // le numéro du ticket
        // on utilise une variable locale et le numéro de ticket est calculé dans
        // un bloc synchronisé afin d'éviter que deux tickets puissent avoir le même numéro
//        synchronized (TicketPDF.class) {
//            nbTickets++;
//            noTicket = nbTickets;
//        }
        // Le tableau d'octets qui contiendra en mémoire le document pdf 
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date dateCurrent = new Date();
        String dateStr = dateFormat.format(dateCurrent);

        String titre = spectacle.getNomSpectacle();
        String resume = spectacle.getResumeSpectacle();
        int duree = spectacle.getDuree();
        List<LocalDateTime> dateSpectacle = spectacle.getListeDate();
        int nbTickes = dateSpectacle.size();

        // Define the table structure first
        TableBuilder tableBuilder = new TableBuilder()
                .addColumnOfWidth(200)
                .addColumnOfWidth(200)
                .setFontSize(12)
                .setFont(PDType1Font.HELVETICA);

// Header ...
        tableBuilder.addRow(new RowBuilder()
                .add(Cell.withText("Représentation").withAllBorders())
                .add(Cell.withText("Place(s) disponible(s) en ligne").withAllBorders())
                .build());

// ... and some cells
        for (int i = 0; i < nbTickes; i++) {
            tableBuilder.addRow(new RowBuilder()
                    .add(Cell.withText(ClasseHelp.dateToString(dateSpectacle.get(i))).withAllBorders())
                    .add(Cell.withText(330 - listePlacesDispo.get(i)).withAllBorders())
                    .setBackgroundColor(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE)
                    .build());
        }

        // Define the table structure first
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
                contentStream.newLineAtOffset(160, 750);
                contentStream.showText("Information concernant le spectacle: " + titre);
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(70, 700);
                contentStream.showText("Durée: " + duree+ " minutes");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(70, 680);
                contentStream.showText( "Résumé: ");
                contentStream.endText();
                 
                String text = resume;
                float startX1 = 0;
                float startY1 = 0;
                List<String> lines = PropositionSpectaclePDF.getLines(text, 12, page);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(120, 660);
                contentStream.moveTextPositionByAmount(startX1, startY1);
                float leading = 1.5f * 12;
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -leading);
                }
                contentStream.endText();
                int nb = lines.size();
                int y = nb * 20;

                final float startY = page.getMediaBox().getHeight() - 160 - y;
                final int startX = 70;

                (new TableDrawer(contentStream, tableBuilder.build(), startX, startY)).draw();
                contentStream.close();

                document.save(out);
                return out.toByteArray();

            }

        }
    }
}
