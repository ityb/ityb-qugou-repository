package com.ityb.qugou.base.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtils {

	private static Font pdf20Font = null;
	private static String fontPath=null;
	/**
	 * 获取中文字符集且是8号字体，常用作文字水印信息
	 * 
	 * @param fullFilePath
	 */
	public static Font getChinese20Font() throws DocumentException, IOException {
		if (pdf20Font == null) {
			// 设置中文字体和字体样式
			//BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			pdf20Font = new Font(bfChinese, 20, Font.BOLD, BaseColor.CYAN);
		}
		return pdf20Font;
	}
	//fontPath
	/**
	 * 设置成只读权限
	 * 
	 * @param pdfWriter
	 */
	public static PdfWriter setReadOnlyPDFFile(PdfWriter pdfWriter) throws DocumentException {
		pdfWriter.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
		return pdfWriter;
	}

	/**
	 * 变更一个图片对象的展示位置和角度信息
	 * 
	 * @param waterMarkImage
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	public static Image getWaterMarkImage(Image waterMarkImage, float xPosition, float yPosition) {
		waterMarkImage.setAbsolutePosition(xPosition, yPosition);// 坐标
		waterMarkImage.setRotation(-20);// 旋转 弧度
		waterMarkImage.setRotationDegrees(-45);// 旋转 角度
		waterMarkImage.scalePercent(100);// 依照比例缩放
		return waterMarkImage;
	}

	/**
	 * 为PDF分页时创建添加文本水印的事件信息
	 */
	class TextWaterMarkPdfPageEvent extends PdfPageEventHelper {

		private String waterMarkText;

		public TextWaterMarkPdfPageEvent(String waterMarkText) {
			this.waterMarkText = waterMarkText;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				float pageWidth = document.right() + document.left();// 获取pdf内容正文页面宽度
				float pageHeight = document.top() + document.bottom();// 获取pdf内容正文页面高度
				// 设置水印字体格式
				Font waterMarkFont = PdfUtils.getChinese20Font();
				PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
				Phrase phrase = new Phrase(waterMarkText, waterMarkFont);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.25f, pageHeight * 0.2f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.25f, pageHeight * 0.5f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.25f, pageHeight * 0.8f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.65f, pageHeight * 0.2f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.65f, pageHeight * 0.5f, 45);
				ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase, pageWidth * 0.65f, pageHeight * 0.8f, 45);
			} catch (DocumentException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			} catch (IOException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			}
		}
	}

	/**
	 * 为PDF分页时创建添加图片水印的事件信息
	 */
	class PictureWaterMarkPdfPageEvent extends PdfPageEventHelper {

		private String waterMarkFullFilePath;
		private Image waterMarkImage;

		public PictureWaterMarkPdfPageEvent(String waterMarkFullFilePath) {
			this.waterMarkFullFilePath = waterMarkFullFilePath;
		}
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				float pageWidth = document.right() + document.left();// 获取pdf内容正文页面宽度
				float pageHeight = document.top() + document.bottom();// 获取pdf内容正文页面高度
				PdfContentByte waterMarkPdfContent = writer.getDirectContentUnder();
				// 仅设置一个图片实例对象，整个PDF文档只应用一个图片对象，极大减少因为增加图片水印导致PDF文档大小增加
				if (waterMarkImage == null) {
					waterMarkImage = Image.getInstance(waterMarkFullFilePath);
				}
				// 添加水印图片，文档正文内容采用横向三列，竖向两列模式增加图片水印
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.2f, pageHeight * 0.1f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.2f, pageHeight * 0.4f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.2f, pageHeight * 0.7f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.6f, pageHeight * 0.1f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.6f, pageHeight * 0.4f));
				waterMarkPdfContent.addImage(getWaterMarkImage(waterMarkImage, pageWidth * 0.6f, pageHeight * 0.7f));
				PdfGState gs = new PdfGState();
				gs.setFillOpacity(0.2f);// 设置透明度为0.2
				waterMarkPdfContent.setGState(gs);
			} catch (DocumentException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			} catch (IOException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			}
		}
	}

	/**
	 * 为PDF分页时创建添加header和footer信息的事件信息
	 */
	class HeadFootInfoPdfPageEvent extends PdfPageEventHelper {

		public HeadFootInfoPdfPageEvent() {
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				PdfContentByte headAndFootPdfContent = writer.getDirectContent();
				headAndFootPdfContent.saveState();
				headAndFootPdfContent.beginText();
				BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				headAndFootPdfContent.setFontAndSize(bfChinese, 10);
				// 文档页头信息设置
				/*float x = document.top(-10);
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, "趣购公司表", (document.right() + document.left()) / 2 , x, 0);*/
				// 文档页脚信息设置
				float y = document.bottom(-20);
				// 页头信息左面
			//	headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, "基本信息导出", document.left(), y, 0);
				// 页头信息中间
				headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, "第" + writer.getPageNumber() + "页", (document.right() + document.left()) / 2,  y, 0);
				// 页头信息右面
			//	headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, "国云科技股份有限公司", document.right(),  y, 0);
				headAndFootPdfContent.endText();
				headAndFootPdfContent.restoreState();
			} catch (DocumentException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			} catch (IOException de) {
				de.printStackTrace();
				System.err.println("pdf watermark font: " + de.getMessage());
			}
		}
	}
	/**
	 * PDF输出表格
	 * @param fullFilePath:要写入的文件物理路径
	 * @param headers：表头
	 * @param contents：内容
	 * @return
	 */
	public static boolean exportTableContent(HttpServletRequest request,HttpServletResponse response, String textWaterMark,String title,String fontPath,String[] header, List<Object[]> contents,String pdfName) {
		//左 右 上 下
		PdfUtils.fontPath=fontPath;
		pdfName=pdfName+".pdf";
		Document pdfDocument = new Document(PageSize.A4, 30, 30, 30, 30);
		OutputStream pdfFileOutputStream=null;
		PdfWriter pdfWriter =null;
		try {
			DownloadUtils.setDownLoadHeader(request, response, pdfName);
			pdfFileOutputStream =response.getOutputStream();
			pdfWriter= PdfWriter.getInstance(pdfDocument, pdfFileOutputStream);
			PdfUtils pdfFileExportUtil = new PdfUtils();
			// 通过PDF页面事件模式添加文字水印功能
			pdfWriter.setPageEvent(pdfFileExportUtil.new TextWaterMarkPdfPageEvent(textWaterMark));
			// 通过PDF页面事件模式添加图片水印功能
			//String waterMarkFullFilePath = "F:/temp/pdftest/loading.gif";// 水印图片
			//pdfWriter.setPageEvent(pdfFileExportUtil.new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
			// 通过PDF页面事件模式添加页头和页脚信息功能
			pdfWriter.setPageEvent(pdfFileExportUtil.new HeadFootInfoPdfPageEvent());
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			//BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font f10 = new Font(bfChinese, 10, Font.NORMAL);
			Font f12 = new Font(bfChinese, 12, Font.NORMAL);
			// 打开PDF文件流
			pdfDocument.open();
			// 创建一个N列的表格控件
			PdfPTable pdfTable = new PdfPTable(header.length);
			// 设置表格占PDF文档100%宽度
			pdfTable.setWidthPercentage(100);
			// 水平方向表格控件左对齐
			pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			
			// 创建一个表格的表头单元格
			PdfPCell pdfTableHeaderCellTitle = new PdfPCell();
			pdfTableHeaderCellTitle.setColspan(header.length);
			pdfTableHeaderCellTitle.setBackgroundColor(new BaseColor(213, 141, 69));
			pdfTableHeaderCellTitle.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableHeaderCellTitle.setMinimumHeight(30);// 高度
			pdfTableHeaderCellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pdfTableHeaderCellTitle.setPhrase(new Paragraph(title, f12));
			pdfTable.addCell(pdfTableHeaderCellTitle);
			
			// 创建一个表格的表头单元格
			PdfPCell pdfTableHeaderCell = new PdfPCell();
			// 设置表格的表头单元格颜色
			pdfTableHeaderCell.setBackgroundColor(new BaseColor(213, 141, 69));
			pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableHeaderCell.setMinimumHeight(30);// 高度
			pdfTableHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			for (String tableHeaderInfo : header) {
				pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f12));
				pdfTable.addCell(pdfTableHeaderCell);
			}
			// 创建一个表格的正文内容单元格
			PdfPCell pdfTableContentCell = new PdfPCell();
			pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			// 表格内容行数的填充
			for (int i = 0; i < contents.size(); i++) {
				Object[] content = contents.get(i);
				for (int j = 0; j < content.length; j++) {
					String tableContentInfo = content[j].toString();
					pdfTableContentCell.setMinimumHeight(25);// 高度
					pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f10));
					pdfTable.addCell(pdfTableContentCell);
//					if (j == 0) {
//						pdfTableContentCell.setPhrase(new Paragraph((i + 1) + "", f10));
//						pdfTable.addCell(pdfTableContentCell);
//						pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f10));
//						pdfTable.addCell(pdfTableContentCell);
//					} else {
//						pdfTableContentCell.setPhrase(new Paragraph(tableContentInfo, f10));
//						pdfTable.addCell(pdfTableContentCell);
//					}
				}

				// 表格内容每写满某个数字的行数时，其内容一方面写入物理文件，另一方面释放内存中存留的内容。
				if ((i % 100) == 0) {
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				} else {
					// 如果全部类容完毕且又没达到某个行数限制，则也要写入物理文件中。
					pdfDocument.add(pdfTable);
					pdfTable.deleteBodyRows();
				}
			}
			pdfFileOutputStream= pdfWriter.getOs();
			pdfFileOutputStream.flush();
			return true;
		} catch (Exception de) {
			de.printStackTrace();
			System.err.println("pdf font: " + de.getMessage());
			return false;
		} finally {
			// 关闭PDF文档流，OutputStream文件输出流也将在PDF文档流关闭方法内部关闭
			if (pdfDocument != null) {
				pdfDocument.close();
			}
			if(pdfFileOutputStream!=null) {
				try {
					pdfFileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pdfWriter!=null) {
				pdfWriter.close();
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String header[]= {"序号","姓名","年龄","职业","籍贯","aa","bb"};
		List<Object[]> contents = new ArrayList<Object[]>();
		for (int i = 0; i < 200; i++) {
			Object[] obj=new Object[header.length];
			obj[0]=(i+1);
			obj[1]="张三" + (i+1);
			obj[2]="25";
			obj[3]="java工程师";
			obj[4]="北京";
			obj[5]="aaaa";
			obj[6]="bbbb";
			contents.add(obj);
		}
		System.out.println("----------> " + contents.size());
		//PdfUtils.exportTableContent(response, "阿里巴巴集团", "阿里巴巴", "C:/Windows/Fonts/SIMYOU.TTF", header, contents);
	}
}