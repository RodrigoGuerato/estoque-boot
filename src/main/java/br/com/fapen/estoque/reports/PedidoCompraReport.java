package br.com.fapen.estoque.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import br.com.fapen.estoque.models.ItemPedidoCompra;
import br.com.fapen.estoque.models.PedidoCompra;

@Component
public class PedidoCompraReport {

	@Autowired
	private ResourceLoader resourceLoader;

	private PdfFont fontCorpo;
	private Style negrito;
	private Style normal;
	private Style titulo;

	public ByteArrayInputStream gerarPDF(PedidoCompra pedidoSendoImpresso) {

		ByteArrayOutputStream outputStreamPDF = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(outputStreamPDF);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document documento = new Document(pdfDoc, PageSize.A4);

		try {
			this.defineFontes();
			float[] tamanhoColunas = { 2, 5 };

			Table tabelaLayout = new Table(UnitValue.createPercentArray(tamanhoColunas)).useAllAvailableWidth();

			// Logo + Numero do Pedido
			String logo = resourceLoader.getResource("/WEB-INF/logo-java.jpg").getFile().getAbsolutePath();
			Image imgLogo = new Image(ImageDataFactory.create(logo));
			tabelaLayout.addCell(imgLogo.setHeight(50).setHorizontalAlignment(HorizontalAlignment.CENTER));
			tabelaLayout.addCell(new Paragraph("Pedido de Compra Nº " + pedidoSendoImpresso.getId()).addStyle(titulo));

			// Dados do Pedido
			Map<String, String> cabecalho = new LinkedHashMap<String, String>();
			cabecalho.put("Fornecedor", pedidoSendoImpresso.getFornecedor().getRazaoSocial());
			cabecalho.put("Contato", pedidoSendoImpresso.getFornecedor().getNomeResponsavel());
			cabecalho.put("Data Entrega",
					pedidoSendoImpresso.getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			cabecalho.put("Condição de Pagamento", pedidoSendoImpresso.getCondicaoPagamento().getDisplayValue());
			cabecalho.put("Status", pedidoSendoImpresso.getStatus().getDisplayValue());

			for (Map.Entry<String, String> registro : cabecalho.entrySet()) {
				tabelaLayout.addCell(
						new Paragraph(registro.getKey()).addStyle(negrito).setTextAlignment(TextAlignment.RIGHT));
				tabelaLayout.addCell(
						new Paragraph(registro.getValue()).addStyle(normal).setTextAlignment(TextAlignment.LEFT));
			}

			// Itens do pedido
			tabelaLayout.addCell(new Cell(1, 2)
					.add(new Paragraph("Itens").addStyle(negrito).setTextAlignment(TextAlignment.CENTER)));
			String[] cabecalhoItens = { "Cód.", "Descrição", "Quantidade", "Vlr. Unitário", "Total" };
			float[] tamColunaItens = { 1, 4, 1, 1, 1 };

			Table tabelaItens = new Table(UnitValue.createPercentArray(tamColunaItens)).useAllAvailableWidth();
			for (String linha : cabecalhoItens) {
				tabelaItens
						.addHeaderCell(new Paragraph(linha).addStyle(negrito).setTextAlignment(TextAlignment.CENTER));
			}
			for (int cont = 0; cont < pedidoSendoImpresso.getItens().size(); cont++) {
				ItemPedidoCompra itemPed = pedidoSendoImpresso.getItens().get(cont);
				tabelaItens.addCell(new Paragraph(itemPed.getProduto().getId().toString()).addStyle(normal)
						.setTextAlignment(TextAlignment.RIGHT));
				tabelaItens.addCell(new Paragraph(itemPed.getProduto().getDescricao()).addStyle(normal));
				tabelaItens.addCell(new Paragraph(itemPed.getQuantidade().toString()).addStyle(normal)
						.setTextAlignment(TextAlignment.RIGHT));
				tabelaItens.addCell(new Paragraph(itemPed.getPrecoUnitario().toString()).addStyle(normal)
						.setTextAlignment(TextAlignment.RIGHT));
				tabelaItens.addCell(new Paragraph(itemPed.getPrecoTotal().toString()).addStyle(normal)
						.setTextAlignment(TextAlignment.RIGHT));
			}
			tabelaItens.addCell(
					new Cell(1, 4).add(new Paragraph("Total").addStyle(normal).setTextAlignment(TextAlignment.RIGHT)));
			tabelaItens.addCell(new Cell(1, 1).add(new Paragraph(pedidoSendoImpresso.getValorTotal().toString())
					.addStyle(negrito).setTextAlignment(TextAlignment.RIGHT)));

			tabelaLayout.addCell(new Cell(1, 2).add(tabelaItens));

			// Observacoes
			tabelaLayout.addCell(new Cell(1, 2).add(new Paragraph("Observações").addStyle(negrito).setTextAlignment(TextAlignment.CENTER)));
			tabelaLayout.addCell(new Cell(1, 2).add(new Paragraph(pedidoSendoImpresso.getObservacao()).addStyle(normal)));

			documento.add(tabelaLayout);
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro na geração do PDF: --> " + e.getMessage());
		}

		documento.close();
		return new ByteArrayInputStream(outputStreamPDF.toByteArray());
	}

	private void defineFontes() throws IOException {
		
		this.fontCorpo = PdfFontFactory.createFont(StandardFonts.HELVETICA);

		this.negrito = new Style().setFont(this.fontCorpo)
				.setFontSize(10).setFontColor(ColorConstants.BLACK).setBold();
		
		this.normal = new Style().setFont(fontCorpo)
				.setFontSize(10).setFontColor(ColorConstants.BLACK);
		
		this.titulo = new Style().setFont(fontCorpo)
				.setFontSize(14).setFontColor(ColorConstants.RED)
				.setTextAlignment(TextAlignment.CENTER);
	}

}
