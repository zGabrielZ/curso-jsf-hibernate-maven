package br.com.gabrielfereira.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielfereira.entidade.to.LancamentoTo;
import br.com.gabrielfereira.entidade.to.PessoaTo;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorioLancamento(List<LancamentoTo> lancamentoTos, List<PessoaTo> pessoaTos) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		try {
			
			String caminho = context.getExternalContext().getRealPath(lancamentoTos == null ? "/resources/relatorios/Pessoa.jrxml" : "/resources/relatorios/Lancamento.jrxml");
			JasperReport compilarRelatorio = JasperCompileManager.compileReport(caminho);
			Map<String, Object> parametros = new LinkedHashMap<>();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lancamentoTos == null ? pessoaTos : lancamentoTos);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorio,parametros,dataSource);
			
			byte [] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			String nomeRelatorio = lancamentoTos == null ? "Relatório Pessoas.pdf" : "Relatório Lançamentos.pdf";
			response.setHeader("Content-disposition","attachment; filename="+nomeRelatorio);
			response.setContentType("application/pdf"); 
			response.setContentLength(bytes.length);
			
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
