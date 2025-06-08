package com.allan.str_producer;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;

public class GerarPDFComTabela {
    public static void main(String[] args) {
        try {
            // Defina o caminho do arquivo PDF que será gerado
            String caminhoPDF = "tabela_no_pdf.pdf";

            // Crie o PdfWriter
            PdfWriter writer = new PdfWriter(caminhoPDF);

            // Crie o PdfDocument
            PdfDocument pdf = new PdfDocument(writer);

            // Crie o Documento
            Document document = new Document(pdf);

            // Defina o número de colunas da tabela
            float[] larguraColunas = {1, 4, 2}; // Exemplo de 3 colunas com larguras relativas

            // Crie a tabela
            Table table = new Table(larguraColunas);

            table.setBorder(Border.NO_BORDER);

            // Adicione os cabeçalhos da tabela
            table.addCell(new Cell().add(new Paragraph("ID")).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph("Nome")).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph("Idade")).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER);

            // Adicione os dados da tabela
            table.addCell(new Cell().add(new Paragraph("1"))).setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph("João Silva")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("30"))).setBorder(Border.NO_BORDER);

            table.addCell(new Cell().add(new Paragraph("2"))).setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph("Maria Oliveira"))).setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph("25")).setBorder(Border.NO_BORDER)).setBorder(Border.NO_BORDER);

            table.addCell(new Cell().add(new Paragraph("3")));
            table.addCell(new Cell().add(new Paragraph("Carlos Pereira")));
            table.addCell(new Cell().add(new Paragraph("28")));


//            table.

            // Adicione a tabela ao documento
            document.add(table);

            // Feche o documento
            document.close();

            System.out.println("PDF gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

