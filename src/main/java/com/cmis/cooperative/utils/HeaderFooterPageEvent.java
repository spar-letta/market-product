package com.cmis.cooperative.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    private PdfTemplate t;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer, document);
        //addWaterMark(writer, document);
        addFooter(writer, document);
    }

    private void addHeader(PdfWriter writer, Document document) {
        PdfPTable header = new PdfPTable(3);
        try {
            header.setTotalWidth(document.getPageSize().getWidth() - 40);

            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            ClassPathResource minetLogoClassPathResource = new ClassPathResource("cmis_logo.png");
            ClassPathResource minetBannerLogoClassPathResource = new ClassPathResource("cmis_banner_logo.png");
            Image logoImage = Image.getInstance(IOUtils.toByteArray(minetLogoClassPathResource.getInputStream()));
            float scale = 0.68f;
            logoImage.scaleAbsolute((200f * scale), (40f * scale));
            logoImage.setAlignment(Element.ALIGN_LEFT);

            Image bannerLogoImage = Image.getInstance(IOUtils.toByteArray(minetBannerLogoClassPathResource.getInputStream()));
            bannerLogoImage.scaleAbsolute((186f * scale), (17f * scale));
            bannerLogoImage.setAlignment(Element.ALIGN_RIGHT);

            PdfPCell imageCell1 = new PdfPCell(logoImage, false);
            imageCell1.setBorder(Rectangle.BOTTOM);
            imageCell1.setBorderColor(BaseColor.LIGHT_GRAY);
            header.addCell(imageCell1);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(10);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("", new Font(Font.FontFamily.HELVETICA, 12)));
            header.addCell(text);

            PdfPCell imageCell2 = new PdfPCell(bannerLogoImage, false);
            imageCell2.setBorder(Rectangle.BOTTOM);
            imageCell2.setBorderColor(BaseColor.LIGHT_GRAY);
            header.addCell(imageCell2);
            header.setPaddingTop(40);
            header.setPaddingTop(40);

            System.out.println("*************** top " + document.top());
            System.out.println("*************** left " + document.left());

            // write content
            header.writeSelectedRows(0, -1, (document.left() + 5), (document.top() + 40), writer.getDirectContent());
        } catch (DocumentException | IOException de) {
            throw new ExceptionConverter(de);
        }
    }

    private void addFooter(PdfWriter writer, Document document) {
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{24, 2, 1});
            footer.setTotalWidth(document.getPageSize().getWidth() - 40);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(30);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);


            // add copyright
            footer.addCell(new Phrase(" \u00A9 Cooperatives.     http://www.cmis.com", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL)));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("p. %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, (document.left() + 10), 30, canvas);
            canvas.endMarkedContentSequence();
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    private void addWaterMark(PdfWriter writer, Document document) {
        try {
            BarcodeQRCode my_code = new BarcodeQRCode("https://cmis.go.ke", 1, 1, null);
            Image qr_image = my_code.getImage();
            qr_image.scaleAbsolute(100f, 100f);  // Set image size.

            //Get width and height of whole page
            float pdfPageWidth = document.getPageSize().getWidth() - 740f;
            float pdfPageHeight = document.getPageSize().getHeight() - 530f;

            //Set waterMarkImage on whole page
            writer.getDirectContentUnder().addImage(qr_image,
                    pdfPageWidth, 0, 0, pdfPageHeight, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }

}