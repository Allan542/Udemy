package com.allan.str_producer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class GerarPDFComTabelaPDFBox {
    public static void main(String[] args) {
        try {
            // Crie o documento PDF
            PDDocument document = new PDDocument();

            // Crie uma nova página
            PDPage page = new PDPage();
            document.addPage(page);

            // Crie o ContentStream para escrever na página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Defina a fonte e o tamanho
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Defina as coordenadas iniciais para a tabela
            float margin = 50;
            float yStart = 750;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 5f;
            float tableWidth = 500;
            float[] columnWidth = {50f, 300f, 150f}; // Largura das colunas

            // Adicione o cabeçalho da tabela
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("ID");
            contentStream.newLineAtOffset(columnWidth[0], 0);
            contentStream.showText("Nome");
            contentStream.newLineAtOffset(columnWidth[1], 0);
            contentStream.showText("Idade");
            contentStream.endText();

            // Desça para a próxima linha
            yPosition -= rowHeight;

            // Adicione os dados da tabela
            String[][] data = {
                {"1", "João Silva", "30"},
                {"2", "Maria Oliveira", "25"},
                {"3", "Carlos Pereira", "28"}
            };

            for (String[] row : data) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(row[0]);
                contentStream.newLineAtOffset(columnWidth[0], 0);
                contentStream.showText(row[1]);
                contentStream.newLineAtOffset(columnWidth[1], 0);
                contentStream.showText(row[2]);
                contentStream.endText();

                // Desça para a próxima linha
                yPosition -= rowHeight;
            }

            // Feche o ContentStream e o documento
            contentStream.close();
            document.save("tabela_no_pdfbox.pdf");
            document.close();

            System.out.println("PDF gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
